/*
 * membrane.c
 */

#include <ctype.h>
#include "membrane.h"
#include "rule.h"

void lmn_mem_add_ruleset(LmnMembrane *mem, LmnRuleSet *ruleset)
{
  vec_push(&mem->rulesets, (LmnWord)ruleset);
}

/*----------------------------------------------------------------------
 * Atom Set
 */

/* static BOOL atom_list_is_empty(AtomSetEntry *entry) */
/* { */
/*   return entry->head == (LmnAtomPtr)entry; */
/* } */

static AtomSetEntry *make_atomlist()
{
  AtomSetEntry *as = LMN_MALLOC(struct AtomSetEntry);
  LMN_ATOM_SET_PREV(as, as);
  LMN_ATOM_SET_NEXT(as, as);
  return as;
}

void lmn_mem_push_atom(LmnMembrane *mem, LmnAtomPtr ap)
{
  AtomSetEntry *as;
  LmnFunctor f = LMN_ATOM_GET_FUNCTOR(ap); 
  
  as = (AtomSetEntry *)hashtbl_get_default(&mem->atomset, f, 0);
  if (!as) {
    as = make_atomlist();
    hashtbl_put(&mem->atomset, LMN_ATOM_GET_FUNCTOR(ap), (HashKeyType)as);
  }

  LMN_ATOM_SET_NEXT(ap, as);
  LMN_ATOM_SET_PREV(ap, as->tail);
  LMN_ATOM_SET_NEXT(as->tail, ap);
  as->tail = (LmnWord)ap;

  if (!LMN_IS_PROXY_FUNCTOR(f)) mem->atom_num++;
} 

/* append e2 to e1 */
static inline void append_atomlist(AtomSetEntry *e1, AtomSetEntry *e2)
{
  LMN_ATOM_SET_NEXT(e1->tail, e2->head);
  LMN_ATOM_SET_PREV(e2->head, e1->tail);
  LMN_ATOM_SET_NEXT(e2->tail, e1);
  e1->tail = e2->tail;
}

void lmn_mem_remove_atom(LmnMembrane *mem, LmnAtomPtr atom)
{
  LmnAtomPtr prev, next;

  prev = LMN_ATOM_GET_PREV(atom);
  next = LMN_ATOM_GET_NEXT(atom);
  LMN_ATOM_SET_PREV(next, prev);
  LMN_ATOM_SET_NEXT(prev, next);
  if (!LMN_IS_PROXY_FUNCTOR(LMN_ATOM_GET_FUNCTOR(atom)))
    mem->atom_num--;
}

/*----------------------------------------------------------------------
 * Membrane
 */

LmnMembrane *lmn_mem_make(void)
{
  LmnMembrane *mem = LMN_MALLOC(LmnMembrane);
  memset(mem, 0, sizeof(LmnMembrane)); /* set all data to 0 */
  vec_init(&mem->rulesets, 1);
  hashtbl_init(&mem->atomset, 4); /* 初期サイズはいくつが適当？ */
  return mem;
}

void lmn_mem_free(LmnMembrane *mem)
{
  HashIterator iter;

  /* free hashtable and it's element */
  for (iter = hashtbl_iterator(&mem->atomset);
       !hashiter_isend(&iter);
       hashiter_next(&iter)) {
    LMN_FREE(hashiter_entry(&iter)->data);
  }
  hashtbl_destroy(&mem->atomset);

  vec_destroy(&mem->rulesets);
  LMN_FREE(mem);
}

/* add newmem to parent chid membranes */
void lmn_mem_add_child_mem(LmnMembrane *parentmem, LmnMembrane *newmem)
{
  /* TODO: membrane activation */
  newmem->next = parentmem->child_head;
  newmem->parent = parentmem;
  if(parentmem->child_head) parentmem->child_head->prev = newmem;
  parentmem->child_head = newmem;
}

/* return NULL when atomlist don't exists. */
AtomSetEntry* lmn_mem_get_atomlist(LmnMembrane *mem, LmnFunctor f)
{
  return (AtomSetEntry*)hashtbl_get_default(&mem->atomset, f, 0);
}

/* make atom which functor is f, and push atom into mem */
LmnAtomPtr lmn_mem_newatom(LmnMembrane *mem, LmnFunctor f)
{
  LmnAtomPtr atom = lmn_new_atom(f);
  lmn_mem_push_atom(mem, atom);
  return atom;
}

/* TODO: 全てのシンボルアトムをなめているので O(n) になっている */
/* TODO: データアトムの個数を数えていない. 正確に行うには
   引数の先を確かめる必要がある */
BOOL lmn_mem_natoms(LmnMembrane *mem, unsigned int count)
{
  return mem->atom_num == count;
}

BOOL lmn_mem_nmems(LmnMembrane *mem, unsigned int count)
{
	unsigned int i;
	LmnMembrane* mp = mem->child_head;
	for(i = 0; mp && i < count; mp=mp->next, i++);
  return i == count;
}

/* return TRUE if # of freelinks in mem is equal to count */
/* リストをたどって数を数えているのでO(n)。
   countがそれほど大きくならなければ問題はないが */
BOOL lmn_mem_nfreelinks(LmnMembrane *mem, unsigned int count)
{
  AtomSetEntry *ent = (AtomSetEntry *)hashtbl_get_default(&mem->atomset,
                                                          LMN_IN_PROXY_FUNCTOR,
                                                          0);
  unsigned int n;
  LmnAtomPtr atom;
  
  if (!ent) return count == 0;
  for (atom = atomlist_head(ent), n = 0;
       atom != lmn_atomset_end(ent) && count<n;
       atom = LMN_ATOM_GET_NEXT(atom), n++) {}
  return count == n;
}

void lmn_mem_link_data_atoms(LmnMembrane *mem,
                             LmnWord d0,
                             LmnLinkAttr attr0,
                             LmnWord d1,
                             LmnLinkAttr attr1)
{
  LmnAtomPtr ap = lmn_new_atom(LMN_UNIFY_FUNCTOR);

  LMN_ATOM_SET_LINK(ap, 0, d0);
  LMN_ATOM_SET_LINK(ap, 1, d1);
  LMN_ATOM_SET_LINK_ATTR(ap, 0, attr0);
  LMN_ATOM_SET_LINK_ATTR(ap, 1, attr1);
  lmn_mem_push_atom(mem, ap);
}

void lmn_mem_unify_atom_args(LmnMembrane *mem,
                   LmnWord atom1,
                   int pos1,
                   LmnWord  atom2,
                   int pos2)
{
  LmnWord ap1 = LMN_ATOM_GET_LINK(atom1, pos1);
  LmnLinkAttr attr1 = LMN_ATOM_GET_LINK_ATTR(atom1, pos1);
  LmnWord ap2 = LMN_ATOM_GET_LINK(atom2, pos2);
  LmnLinkAttr attr2 = LMN_ATOM_GET_LINK_ATTR(atom2, pos2);

  if(LMN_ATTR_IS_DATA(attr1) && LMN_ATTR_IS_DATA(attr2)) {
    lmn_mem_link_data_atoms(mem, ap1, attr1, ap2, attr2);
  }
  else if (LMN_ATTR_IS_DATA(attr1)) {
    LMN_ATOM_SET_LINK(ap2, attr2, (LmnWord)ap1);
    LMN_ATOM_SET_LINK_ATTR(ap2, attr2, attr1);
  }
  else if (LMN_ATTR_IS_DATA(attr2)) {
    LMN_ATOM_SET_LINK(ap1, attr1, (LmnWord)ap2);
    LMN_ATOM_SET_LINK_ATTR(ap1, attr1, attr2);
  }
  else {
    LMN_ATOM_SET_LINK(ap2, attr2, (LmnWord)ap1);
    LMN_ATOM_SET_LINK_ATTR(ap2, attr2, attr1);
    LMN_ATOM_SET_LINK(ap1, attr1, (LmnWord)ap2);
    LMN_ATOM_SET_LINK_ATTR(ap1, attr1, attr2);
  }
}

/* シンボルアトムに限定したnewlink */
static void lmn_mem_newlink_symbols(LmnMembrane *mem,
                                    LmnWord atom0,
                                    int pos0,
                                    LmnWord atom1,
                                    int pos1)
{
  LMN_ATOM_SET_LINK(LMN_ATOM(atom0), pos0, atom1);
  LMN_ATOM_SET_LINK(LMN_ATOM(atom1), pos1, atom0); 
  LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(atom0), pos0, pos1);
  LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(atom1), pos1, pos0);
}

void lmn_mem_newlink(LmnMembrane *mem,
                     LmnWord atom0,
                     LmnLinkAttr attr0,
                     int pos0,
                     LmnWord atom1,
                     LmnLinkAttr attr1,
                     int pos1)
{
  if (LMN_ATTR_IS_DATA(attr0)) {
    if (LMN_ATTR_IS_DATA(attr1)) { /* both data */
      LMN_ASSERT(pos0 == 0 && pos1 == 0);
      lmn_mem_link_data_atoms(mem, atom0, pos0, atom1, pos1);
    }
    else { /* atom0 data, atom1 symbol */
      LMN_ATOM_SET_LINK(LMN_ATOM(atom1), pos1, atom0);
      LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(atom1), pos1, attr0);
    }
  }
  else if (LMN_ATTR_IS_DATA(attr1)) { /* atom0 symbol, atom1 data */
    LMN_ATOM_SET_LINK(LMN_ATOM(atom0), pos0, atom1);
    LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(atom0), pos0, attr1);
  }
  else { /* both symbol */
    lmn_mem_newlink_symbols(mem, atom0, pos0, atom1, pos1);
  }
}

/* TODO: atom0, atom1がデータでないことが保証されれば,attrは不要 */
void lmn_mem_relink_atom_args(LmnMembrane *mem,
                              LmnWord atom0,
                              LmnLinkAttr attr0,
                              int pos0,
                              LmnWord atom1,
                              LmnLinkAttr attr1,
                              int pos1)
{
  LmnWord a;
  LmnLinkAttr attr;

  /* TODO: relinkではatom0,atom1がデータになることは,セマンティクスで禁止されるはず
           このことを確認する */
  LMN_ASSERT(!LMN_ATTR_IS_DATA(attr0) &&
             !LMN_ATTR_IS_DATA(attr1));
  
  a = LMN_ATOM_GET_LINK(LMN_ATOM(atom1), pos1);
  attr = LMN_ATOM_GET_LINK_ATTR(LMN_ATOM(atom1), pos1);

  /* この処理はaがsymbol, dataのどちらでも共通 */
  LMN_ATOM_SET_LINK(LMN_ATOM(atom0), pos0, a);
  LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(atom0), pos0, attr);

  if (!LMN_ATTR_IS_DATA(attr)) {
    LMN_ATOM_SET_LINK(LMN_ATOM(a), LMN_ATTR_GET_VALUE(attr), atom0);
    LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(a), LMN_ATTR_GET_VALUE(attr), LMN_ATTR_MAKE_LINK(pos0));
  }
}
                              
void lmn_mem_move_cells(LmnMembrane *destmem, LmnMembrane *srcmem)
{
  /* move atoms */
  {
    HashIterator iter;

    for (iter = hashtbl_iterator(&srcmem->atomset);
         !hashiter_isend(&iter);
         hashiter_next(&iter)) {
      LmnFunctor f = (LmnFunctor)hashiter_entry(&iter)->key;
      AtomSetEntry *srcent = (AtomSetEntry *)hashiter_entry(&iter)->data;
      AtomSetEntry *destent = lmn_mem_get_atomlist(destmem, f);
      hashtbl_put(&srcmem->atomset, f, 0);
      if (destent) append_atomlist(destent, srcent);
      else hashtbl_put(&destmem->atomset, (HashKeyType)f, (HashValueType)srcent);
    }
  }

  /* move membranes */
  {
    LmnMembrane *m, *next;
    for (m = srcmem->child_head; m; m = next) {
      next = m->next;
      lmn_mem_add_child_mem(destmem, m);
    }
  }
}

#define REMOVE 1U
#define STAR   2U
#define STATE(ATOM)        (LMN_ATOM_GET_LINK_ATTR((ATOM), 2))
#define SET_STATE(ATOM,S)  (LMN_ATOM_SET_LINK_ATTR((ATOM), 2, (S)))

void lmn_mem_remove_proxies(LmnMembrane *mem)
{
  AtomSetEntry *ent;
  LmnAtomPtr opxy;
  Vector remove_list;
  unsigned int i;

  ent = (AtomSetEntry *)hashtbl_get_default(&mem->atomset,
                                           LMN_OUT_PROXY_FUNCTOR,
                                           0);
  if (!ent) return;

  vec_init(&remove_list, 16);

  /* clear mem attribute */
  for (opxy = atomlist_head(ent);
       opxy != lmn_atomset_end(ent);
       opxy = LMN_ATOM_GET_NEXT(opxy)) {
    LMN_ATOM_SET_LINK_ATTR(opxy, 2, 0);
  }

  for (opxy = atomlist_head(ent);
       opxy != lmn_atomset_end(ent);
       opxy = LMN_ATOM_GET_NEXT(opxy)) {
    LmnAtomPtr a0 = LMN_ATOM(LMN_ATOM_GET_LINK(opxy, 0));
    if (LMN_PROXY_GET_MEM(a0)->parent != mem &&
        !LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(opxy, 1))) {
      LmnAtomPtr a1 = LMN_ATOM(LMN_ATOM_GET_LINK(opxy, 1));
      LmnFunctor f1 = LMN_ATOM_GET_FUNCTOR(a1);
      if (f1 == LMN_IN_PROXY_FUNCTOR) {
        lmn_mem_unify_atom_args(mem, (LmnWord)opxy, 0, (LmnWord)a1, 0);
        SET_STATE(opxy, REMOVE);
        vec_push(&remove_list, (LmnWord)opxy);
        lmn_mem_remove_atom(mem, a1);
        lmn_delete_atom(a1);
      }
      else {
        if (f1 == LMN_OUT_PROXY_FUNCTOR &&
            LMN_PROXY_GET_MEM(LMN_ATOM_GET_LINK(a1, 0))->parent != mem &&
            STATE(opxy) != REMOVE) {
          lmn_mem_unify_atom_args(mem, (LmnWord)opxy, 0, (LmnWord)a1, 0);
          SET_STATE(opxy, REMOVE);
          SET_STATE(a1, REMOVE);
          vec_push(&remove_list, (LmnWord)opxy);
          vec_push(&remove_list, (LmnWord)a1);
        } else {
          SET_STATE(opxy, STAR);
        }
      }
    }
  }

  for (i = 0; i < remove_list.num; i++) {
    lmn_mem_remove_atom(mem, LMN_ATOM(vec_get(&remove_list, i)));
    lmn_delete_atom(LMN_ATOM(vec_get(&remove_list, i)));
  }
  vec_destroy(&remove_list);
}

/* proxies in child_mem are stared */
void lmn_mem_insert_proxies(LmnMembrane *mem, LmnMembrane *child_mem)
{
  Vector remove_list;
  AtomSetEntry *ent;
  LmnAtomPtr star, oldstar;
  unsigned int i;
  
  ent = (AtomSetEntry *)hashtbl_get_default(&child_mem->atomset,
                                            LMN_OUT_PROXY_FUNCTOR,
                                            0);
  if (!ent) return;
  
  vec_init(&remove_list, 16);

  for (star = atomlist_head(ent);
       star != lmn_atomset_end(ent);
       star = LMN_ATOM_GET_NEXT(star)) {
    if (STATE(star) != STAR) continue;
    oldstar = LMN_ATOM(LMN_ATOM_GET_LINK(star, 0));
    if (LMN_PROXY_GET_MEM(oldstar) == child_mem) {
      if (STATE(star) != REMOVE) {
        lmn_mem_unify_atom_args(child_mem, (LmnWord)star, 1, (LmnWord)oldstar, 1);
        SET_STATE(star, REMOVE);
        SET_STATE(oldstar, REMOVE);
      }
    }
    else {
      LMN_ATOM_SET_FUNCTOR(star, LMN_IN_PROXY_FUNCTOR);
      if (LMN_PROXY_GET_MEM(oldstar) == mem) {
        LMN_ATOM_SET_FUNCTOR(oldstar, LMN_OUT_PROXY_FUNCTOR);
        lmn_mem_newlink_symbols(mem, (LmnWord)star, 0, (LmnWord)oldstar, 0);
      } else {
        LmnAtomPtr outside = lmn_mem_newatom(mem, LMN_OUT_PROXY_FUNCTOR);
        LmnAtomPtr newstar = lmn_mem_newatom(mem, LMN_OUT_PROXY_FUNCTOR);
        SET_STATE(newstar, STAR);
        lmn_mem_newlink_symbols(mem, (LmnWord)outside, 1, (LmnWord)newstar, 1);
        lmn_mem_relink_atom_args(mem,
                                 (LmnWord)newstar,
                                 LMN_ATTR_MAKE_LINK(0),
                                 0,
                                 (LmnWord)star,
                                 LMN_ATTR_MAKE_LINK(0),
                                 0);
        lmn_mem_newlink_symbols(mem, (LmnWord)star, 0, (LmnWord)outside, 0);
      }
    }
  }

  for (i = 0; i < remove_list.num; i++) {
    lmn_mem_remove_atom(mem, LMN_ATOM(vec_get(&remove_list, i)));
    lmn_delete_atom(LMN_ATOM(vec_get(&remove_list, i)));
  }

  vec_destroy(&remove_list);
}

void lmn_mem_remove_temporary_proxies(LmnMembrane *mem)
{
  LmnAtomPtr star, outside;
  AtomSetEntry *ent;
  Vector remove_list;
  unsigned int i;

  ent = (AtomSetEntry *)hashtbl_get_default(&mem->atomset,
                                            LMN_OUT_PROXY_FUNCTOR,
                                            0);
  
  if (!ent) return;

  vec_init(&remove_list, 16);
  
  for (star = atomlist_head(ent);
       star != lmn_atomset_end(ent);
       star = LMN_ATOM_GET_NEXT(star)) {
    if (STATE(star) != STAR) continue;
    outside = LMN_ATOM(LMN_ATOM_GET_LINK(star, 0));
    lmn_mem_unify_atom_args(mem, (LmnWord)star, 1, (LmnWord)outside, 1);
    SET_STATE(star, REMOVE);
    SET_STATE(outside, REMOVE);
    vec_push(&remove_list, (LmnWord)star);
    vec_push(&remove_list, (LmnWord)outside);
  }
  for (i = 0; i < remove_list.num; i++) {
    lmn_mem_remove_atom(mem, LMN_ATOM(vec_get(&remove_list, i)));
    lmn_delete_atom(LMN_ATOM(vec_get(&remove_list, i)));
  }

  vec_destroy(&remove_list);
}

void lmn_mem_remove_toplevel_proxies(LmnMembrane *mem)
{
  Vector remove_list;
  AtomSetEntry *ent;
  LmnAtomPtr outside;
  unsigned int i;
  
  ent = (AtomSetEntry *)hashtbl_get_default(&mem->atomset,
                                            LMN_OUT_PROXY_FUNCTOR,
                                            0);
  if (!ent) return;
  
  vec_init(&remove_list, 16);

  for (outside = atomlist_head(ent);
       outside != lmn_atomset_end(ent);
       outside = LMN_ATOM_GET_NEXT(outside)) {
    LmnAtomPtr a0;
    if (STATE(outside) == REMOVE) continue;
    a0 = LMN_ATOM(LMN_ATOM_GET_LINK(outside, 0));
    if (LMN_PROXY_GET_MEM(a0) &&
        LMN_PROXY_GET_MEM(a0)->parent != mem) {
      LmnAtomPtr a1 = LMN_ATOM(LMN_ATOM_GET_LINK(outside, 1));
      if (LMN_ATOM_GET_FUNCTOR(a1) == LMN_OUT_PROXY_FUNCTOR) {
        LmnAtomPtr a10 = LMN_ATOM(LMN_ATOM_GET_LINK(a1, 0));
        if (LMN_PROXY_GET_MEM(a10) != 0 &&
            LMN_PROXY_GET_MEM(a10) != mem) {
          lmn_mem_unify_atom_args(mem, (LmnWord)outside, 0, (LmnWord)a1, 0);
          SET_STATE(outside, REMOVE);
          SET_STATE(a1, REMOVE);
          vec_push(&remove_list, (LmnWord)outside);
          vec_push(&remove_list, (LmnWord)a1);
        }
      }
    }
  }

  for (i = 0; i < remove_list.num; i++) {
    lmn_mem_remove_atom(mem, LMN_ATOM(vec_get(&remove_list, i)));
    lmn_delete_atom(LMN_ATOM(vec_get(&remove_list, i)));
  }
}

