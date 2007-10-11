/*
 * membrane.c
 */

#include <ctype.h>
#include "membrane.h"
#include "rule.h"
#include "dumper.h" /* for debug */

/* REFACTOR: このファイルにあるのはふさわしくないので移動する */
static inline void free_symbol_atom_with_buddy_data(LmnAtomPtr atom);


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

/* アトムリストを空にする. */
#define EMPTY_ATOMLIST(X)                       \
  do {                                          \
    LMN_ATOM_SET_PREV((X), (X));                \
    LMN_ATOM_SET_NEXT((X), (X));                \
  } while (0)

static AtomSetEntry *make_atomlist()
{
  AtomSetEntry *as = LMN_MALLOC(struct AtomSetEntry);
  EMPTY_ATOMLIST(as);
  return as;
}

void mem_push_symbol_atom(LmnMembrane *mem, LmnAtomPtr atom)
{
  AtomSetEntry *as;
  LmnFunctor f = LMN_ATOM_GET_FUNCTOR(atom); 

  as = (AtomSetEntry *)hashtbl_get_default(&mem->atomset, f, 0);
  if (!as) {
    as = make_atomlist();
    hashtbl_put(&mem->atomset, (LmnWord)f, (HashKeyType)as);
  }

  if (LMN_IS_PROXY_FUNCTOR(f)) {
    LMN_PROXY_SET_MEM(atom, (LmnWord)mem);
  }
  else if (f != LMN_UNIFY_FUNCTOR) {
    /* symbol atom except proxy and unify */
    mem->atom_num++;
  }
  
  LMN_ATOM_SET_NEXT(atom, as);
  LMN_ATOM_SET_PREV(atom, as->tail);
  LMN_ATOM_SET_NEXT(as->tail, atom);
  as->tail = (LmnWord)atom;
}

void lmn_mem_push_atom(LmnMembrane *mem, LmnWord atom, LmnLinkAttr attr)
{
  if (LMN_ATTR_IS_DATA(attr)) {
    mem->atom_num++;
  }
  else { /* symbol atom */
    mem_push_symbol_atom(mem, LMN_ATOM(atom));
  }
} 

/* append e2 to e1 */
static inline void append_atomlist(AtomSetEntry *e1, AtomSetEntry *e2)
{
  if (atomlist_head(e2) != lmn_atomset_end(e2)) {/* true if e2 is not empty */
    LMN_ATOM_SET_NEXT(e1->tail, e2->head);
    LMN_ATOM_SET_PREV(e2->head, e1->tail);
    LMN_ATOM_SET_NEXT(e2->tail, e1);
    e1->tail = e2->tail;
  }
  EMPTY_ATOMLIST(e2);
}

static inline void mem_remove_symbol_atom(LmnMembrane *mem, LmnAtomPtr atom)
{
  LmnFunctor f = LMN_ATOM_GET_FUNCTOR(atom);
  
  LMN_ATOM_SET_PREV(LMN_ATOM_GET_NEXT(atom), LMN_ATOM_GET_PREV(atom));
  LMN_ATOM_SET_NEXT(LMN_ATOM_GET_PREV(atom), LMN_ATOM_GET_NEXT(atom));
  if (!LMN_IS_PROXY_FUNCTOR(f) && f != LMN_UNIFY_FUNCTOR) {
    mem->atom_num--;
  }
}

void lmn_mem_remove_atom(LmnMembrane *mem, LmnWord atom, LmnLinkAttr attr)
{
  if (LMN_ATTR_IS_DATA(attr)) {
    mem->atom_num--;
  }
  else {
    mem_remove_symbol_atom(mem, LMN_ATOM(atom));
  }
}

/*----------------------------------------------------------------------
 * Membrane
 */

LmnMembrane *lmn_mem_make(void)
{
  LmnMembrane *mem = LMN_MALLOC(LmnMembrane);
  
  memset(mem, 0, sizeof(LmnMembrane)); /* set all data to 0 */
  vec_init(&mem->rulesets, 1);
  hashtbl_init(&mem->atomset, 4); /* TODO: 初期サイズはいくつが適当？ */
  return mem;
}

/* 膜内のプロセスと子膜を破棄する */
void lmn_mem_drop(LmnMembrane *mem)
{
  HashIterator iter;
  LmnMembrane *m, *n;

  /* drop and free child mems */
  m = mem->child_head;
  while (m) {
    n = m;
    m = m->next;
    lmn_mem_drop(n);
    lmn_mem_free(n);
  }
  mem->child_head = NULL;

  /* free all atoms */
  for (iter = hashtbl_iterator(&mem->atomset);
       !hashiter_isend(&iter);
       hashiter_next(&iter)) {
    AtomSetEntry *ent = (AtomSetEntry *)hashiter_entry(&iter)->data;
    LmnAtomPtr a = LMN_ATOM(ent->head), b;
    while (a != lmn_atomset_end(ent)) {
      b = a;
      a = LMN_ATOM_GET_NEXT(a);
      free_symbol_atom_with_buddy_data(b);
    }
    EMPTY_ATOMLIST(ent);
  }
  mem->atom_num = 0;
}

/* memとmem内のプロセス全てを破棄 */
void lmn_mem_free(LmnMembrane *mem)
{
  HashIterator iter;

  LMN_ASSERT(mem->atom_num == 0);
  /* free all atomlists  */
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
  LMN_ASSERT(parentmem);
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
  mem_push_symbol_atom(mem, atom);
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
	LmnMembrane *mp = mem->child_head;
	for(i = 0; mp && i < count; mp = mp->next, i++);
  return i == count;
}

/* return TRUE if # of freelinks in mem is equal to count */
/* TODO: O(N) になっている */
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
       atom != lmn_atomset_end(ent) && n<=count;
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
  mem_push_symbol_atom(mem, ap);
}

/* atom1, atom2をシンボルアトムに限定した unify link */
void lmn_mem_unify_symbol_atom_args(LmnAtomPtr atom1,
                                    int pos1,
                                    LmnAtomPtr atom2,
                                    int pos2)
{
  LmnWord ap1 = LMN_ATOM_GET_LINK(atom1, pos1);
  LmnLinkAttr attr1 = LMN_ATOM_GET_LINK_ATTR(atom1, pos1);
  LmnWord ap2 = LMN_ATOM_GET_LINK(atom2, pos2);
  LmnLinkAttr attr2 = LMN_ATOM_GET_LINK_ATTR(atom2, pos2);

  LMN_ATOM_SET_LINK(ap2, attr2, (LmnWord)ap1);
  LMN_ATOM_SET_LINK_ATTR(ap2, attr2, attr1);
  LMN_ATOM_SET_LINK(ap1, attr1, (LmnWord)ap2);
  LMN_ATOM_SET_LINK_ATTR(ap1, attr1, attr2);
}

/* atom1, atom2はシンボルアトムのはず */
void lmn_mem_unify_atom_args(LmnMembrane *mem,
                   LmnAtomPtr atom1,
                   int pos1,
                   LmnAtomPtr atom2,
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
/* TODO: memの関数でないので名前からmemを取り除く */
void lmn_newlink_in_symbols(LmnAtomPtr atom0,
                            int pos0,
                            LmnAtomPtr atom1,
                            int pos1)
{
  LMN_ATOM_SET_LINK(atom0, pos0, (LmnWord)atom1);
  LMN_ATOM_SET_LINK(atom1, pos1, (LmnWord)atom0); 
  LMN_ATOM_SET_LINK_ATTR(atom0, pos0, pos1);
  LMN_ATOM_SET_LINK_ATTR(atom1, pos1, pos0);
}

/* シンボルアトムatom0と, シンボルorデータアトム atom1 の間にリンクを張る
   このコードが重複して現れたので,関数に分割した */
static inline void newlink_symbol_and_something(LmnAtomPtr atom0,
                                                int pos,
                                                LmnWord atom1,
                                                LmnLinkAttr attr)
{
  LMN_ATOM_SET_LINK(atom0, pos, atom1);
  LMN_ATOM_SET_LINK_ATTR(atom0, pos, attr);
  if (!LMN_ATTR_IS_DATA(attr)) {
    LMN_ATOM_SET_LINK(LMN_ATOM(atom1), LMN_ATTR_GET_VALUE(attr), (LmnWord)atom0);
    LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(atom1), LMN_ATTR_GET_VALUE(attr), LMN_ATTR_MAKE_LINK(pos));
  }
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
    lmn_newlink_in_symbols(LMN_ATOM(atom0), pos0, LMN_ATOM(atom1), pos1);
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
  /* TODO: relinkではatom0,atom1がデータになることは,セマンティクスで禁止されるはず
           このことを確認する */
  LMN_ASSERT(!LMN_ATTR_IS_DATA(attr0) &&
             !LMN_ATTR_IS_DATA(attr1));

  newlink_symbol_and_something(LMN_ATOM(atom0),
                               pos0,
                               LMN_ATOM_GET_LINK(LMN_ATOM(atom1), pos1),
                               LMN_ATOM_GET_LINK_ATTR(LMN_ATOM(atom1), pos1));
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

      if (LMN_IS_PROXY_FUNCTOR(f)) {
        LmnAtomPtr a;
        for (a = atomlist_head(srcent);
             a != lmn_atomset_end(srcent);
             a = LMN_ATOM_GET_NEXT(a)) {
          LMN_PROXY_SET_MEM(a, (LmnWord)destmem);
        }
      }

      if (destent) {
        append_atomlist(destent, srcent);
      }
      else {
        /* リストをdestに移す */
        hashiter_entry(&iter)->data = 0; /* freeされないように NULL にする */
        hashtbl_put(&destmem->atomset, (HashKeyType)f, (HashValueType)srcent);
      }
    }
    destmem->atom_num += srcmem->atom_num;
    srcmem->atom_num = 0;
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

#define REMOVE            1
#define STAR              2
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
        lmn_mem_unify_atom_args(mem, opxy, 0, a1, 0);
        SET_STATE(opxy, REMOVE);
        vec_push(&remove_list, (LmnWord)opxy);
        mem_remove_symbol_atom(mem, a1);
        lmn_delete_atom(a1);
      }
      else {
        if (f1 == LMN_OUT_PROXY_FUNCTOR &&
            LMN_PROXY_GET_MEM(LMN_ATOM_GET_LINK(a1, 0))->parent != mem &&
            STATE(opxy) != REMOVE) {
          lmn_mem_unify_atom_args(mem, opxy, 0, a1, 0);
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
    mem_remove_symbol_atom(mem, LMN_ATOM(vec_get(&remove_list, i)));
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
        lmn_mem_unify_atom_args(child_mem, star, 1, oldstar, 1);
        SET_STATE(star, REMOVE);
        SET_STATE(oldstar, REMOVE);
      }
    }
    else {
      LMN_ATOM_SET_FUNCTOR(star, LMN_IN_PROXY_FUNCTOR);
      if (LMN_PROXY_GET_MEM(oldstar) == mem) {
        LMN_ATOM_SET_FUNCTOR(oldstar, LMN_OUT_PROXY_FUNCTOR);
        lmn_newlink_in_symbols(star, 0, oldstar, 0);
      } else {
        LmnAtomPtr outside = lmn_mem_newatom(mem, LMN_OUT_PROXY_FUNCTOR);
        LmnAtomPtr newstar = lmn_mem_newatom(mem, LMN_OUT_PROXY_FUNCTOR);
        SET_STATE(newstar, STAR);
        lmn_newlink_in_symbols(outside, 1, newstar, 1);
        lmn_mem_relink_atom_args(mem,
                                 (LmnWord)newstar,
                                 LMN_ATTR_MAKE_LINK(0),
                                 0,
                                 (LmnWord)star,
                                 LMN_ATTR_MAKE_LINK(0),
                                 0);
        lmn_newlink_in_symbols(star, 0, outside, 0);
      }
    }
  }

  for (i = 0; i < remove_list.num; i++) {
    mem_remove_symbol_atom(mem, LMN_ATOM(vec_get(&remove_list, i)));
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
    lmn_mem_unify_atom_args(mem, star, 1, outside, 1);
    SET_STATE(star, REMOVE);
    SET_STATE(outside, REMOVE);
    vec_push(&remove_list, (LmnWord)star);
    vec_push(&remove_list, (LmnWord)outside);
  }
  for (i = 0; i < remove_list.num; i++) {
    mem_remove_symbol_atom(mem, LMN_ATOM(vec_get(&remove_list, i)));
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
          lmn_mem_unify_atom_args(mem, outside, 0, a1, 0);
          SET_STATE(outside, REMOVE);
          SET_STATE(a1, REMOVE);
          vec_push(&remove_list, (LmnWord)outside);
          vec_push(&remove_list, (LmnWord)a1);
        }
      }
    }
  }

  for (i = 0; i < remove_list.num; i++) {
    mem_remove_symbol_atom(mem, LMN_ATOM(vec_get(&remove_list, i)));
    lmn_delete_atom(LMN_ATOM(vec_get(&remove_list, i)));
  }
}

/* REFACTOR: このファイルにあるのはふさわしくない */
LmnWord lmn_copy_data_atom(LmnWord atom, LmnLinkAttr attr)
{
  switch (attr) {
  case LMN_ATOM_INT_ATTR:
    return atom;
  case LMN_ATOM_DBL_ATTR:
    {
      double *d = LMN_MALLOC(double);
      *d = *(double*)atom;
      return (LmnWord)d;
    }
  default:
    LMN_ASSERT(FALSE);
    return -1;
  }
}

/* REFACTOR: このファイルにあるのはふさわしくない */
LmnWord lmn_copy_atom(LmnWord atom, LmnLinkAttr attr)
{
  if (LMN_ATTR_IS_DATA(attr)) {
    return lmn_copy_data_atom(atom, attr);
  } else { /* symbol atom */
    LmnFunctor f = LMN_ATOM_GET_FUNCTOR(LMN_ATOM(atom));
    LmnAtomPtr newatom = lmn_new_atom(f);
    memcpy((void *)newatom, (void *)atom, LMN_WORD_BYTES*LMN_ATOM_WORDS(LMN_FUNCTOR_ARITY(f)));
    return (LmnWord)newatom;
  }
}

/* mem -> atoms のhashtblはold atom -> newatomのhashtbl一つに統合できる.
   子膜を必ず先にコピーする。しかし,これだと、一つのhashtblが大きくなってしまう
   問題がある
*/
SimpleHashtbl *lmn_mem_copy_cells(LmnMembrane *destmem, LmnMembrane *srcmem)
{
  SimpleHashtbl *atoms, mem_to_atoms;
  LmnMembrane *m;
  HashIterator iter;
  unsigned int i;
  
  atoms = hashtbl_make(srcmem->atom_num * 2);
  hashtbl_init(&mem_to_atoms, 16);
  hashtbl_put(&mem_to_atoms, (HashKeyType)destmem, (HashValueType)atoms);

  /* copy child mems */
  for (m = srcmem->child_head; m; m = m->next) {
    LmnMembrane *new_mem = lmn_mem_make();
    lmn_mem_add_child_mem(destmem, new_mem);
    hashtbl_put(&mem_to_atoms,
                (HashKeyType)new_mem,
                (HashValueType)lmn_mem_copy_cells(new_mem, m));
  }

  /* copy atoms */
  for (iter = hashtbl_iterator(&srcmem->atomset);
       !hashiter_isend(&iter);
       hashiter_next(&iter)) {
    AtomSetEntry *ent = (AtomSetEntry *)hashiter_entry(&iter)->data;
    LmnAtomPtr srcatom;
    
    LMN_ASSERT(ent);
    
    for (srcatom = atomlist_head(ent);
         srcatom != lmn_atomset_end(ent);
         srcatom = LMN_ATOM_GET_NEXT(srcatom)) {
      LmnFunctor f;
      LmnAtomPtr newatom;
      unsigned int start, end;
      
      if (hashtbl_contains(atoms, (HashKeyType)srcatom)) continue;
      f = LMN_ATOM_GET_FUNCTOR(srcatom);
      newatom = lmn_mem_newatom(destmem, f);
      hashtbl_put(atoms, (HashKeyType)srcatom, (HashValueType)newatom);
      start = 0;
      end = LMN_ATOM_GET_ARITY(srcatom);

      if (LMN_IS_PROXY_FUNCTOR(f)) {
        start = 1, end = 2;
        LMN_PROXY_SET_MEM(newatom, (LmnWord)destmem);
        if (f == LMN_OUT_PROXY_FUNCTOR) {
          LmnAtomPtr srcinside = LMN_ATOM(LMN_ATOM_GET_LINK(srcatom, 0));
          SimpleHashtbl *childmem_atoms =
            (SimpleHashtbl *)hashtbl_get(&mem_to_atoms,
                                         (HashKeyType)LMN_PROXY_GET_MEM(srcinside));
          LmnAtomPtr newinside = LMN_ATOM(hashtbl_get(childmem_atoms, (HashKeyType)srcinside));
          /* 必ず子膜につながっているはず */
          LMN_ASSERT(LMN_ATOM_GET_FUNCTOR(srcinside) == LMN_IN_PROXY_FUNCTOR &&
                     LMN_PROXY_GET_MEM(srcinside)->parent == LMN_PROXY_GET_MEM(srcatom));
          lmn_newlink_in_symbols(newatom, 0, newinside, 0);
        }
      }

      /* リンク先と接続 */
      for (i = start; i < end; i++) {
        LmnLinkAttr attr = LMN_ATOM_GET_LINK_ATTR(srcatom, i);
        LmnWord newargatom = lmn_copy_atom(LMN_ATOM_GET_LINK(srcatom, i), attr);
        newlink_symbol_and_something(newatom, i, newargatom, attr);
      }
    }
  }
  destmem->atom_num += srcmem->atom_num;
  
  hashtbl_destroy(&mem_to_atoms);

  return atoms;
}

static inline void free_data_atom(LmnWord atom, LmnLinkAttr attr)
{
  switch (attr) {
  case LMN_ATOM_INT_ATTR:
    break;
  case LMN_ATOM_DBL_ATTR:
    LMN_FREE((double*)atom);
    break;
  default:
    LMN_ASSERT(FALSE);
    break;
  }
  return;
}


/* REFACTOR: このファイルにあるのはふさわしくないので移動する */
/* シンボルアトムとリンクで接続しているデータアトムを解放する */
static inline void free_symbol_atom_with_buddy_data(LmnAtomPtr atom)
{
  unsigned int i;
  unsigned int end = LMN_ATOM_GET_LINK_NUM(atom);
  /* free linked data atoms */
  for (i = 0; i < end; i++) {
    if (LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(atom, i))) {
      free_data_atom(LMN_ATOM_GET_LINK(atom, i), LMN_ATOM_GET_LINK_ATTR(atom, i));
    }
  }
  lmn_delete_atom((LmnAtomPtr)atom);
}

/* REFACTOR: このファイルにあるのはふさわしくないので移動する */
/* O(ARITY) */
void lmn_free_atom(LmnWord atom, LmnLinkAttr attr)
{
  if (LMN_ATTR_IS_DATA(attr)) {
    free_data_atom(atom, attr);
  }
  else { /* symbol atom */
    lmn_delete_atom((LmnAtomPtr)atom);
  }
}

BOOL lmn_eq_func(LmnWord atom0, LmnLinkAttr attr0, LmnWord atom1, LmnLinkAttr attr1)
{
  /* TODO: シンボルアトムのattrがすべて等しい値であることを確認する */
  if (attr0 != attr1) return FALSE;
  switch (attr0) {
  case LMN_ATOM_INT_ATTR:
    return atom0 == atom1;
  case LMN_ATOM_DBL_ATTR:
    /* TODO: 浮動小数点の比較は要確認 */
    return *(double *)atom0 == *(double *)atom1;
  default: /* symbol atom */
    return LMN_ATOM_GET_FUNCTOR(atom0) == LMN_ATOM_GET_FUNCTOR(atom1);
  }
}
