/*
 * membrane.c
 */

#include "membrane.h"

/*----------------------------------------------------------------------
 * Rule Set List
 */

/* TODO stub implementation */

struct RuleSetList {
  LmnRuleSet *ruleset;
  RuleSetList *next;
};
typedef struct RuleSetList RuleSetNode;

static RuleSetNode *ruleset_node_make(LmnRuleSet *ruleset)
{
  RuleSetNode *node = LMN_MALLOC(RuleSetNode);
  node->ruleset = ruleset;
  node->next = 0;
  return node;
}

static void ruleset_free(RuleSetList *r)
{
  RuleSetList *p;
  
  while (r) {
    p = r->next;
    LMN_FREE(r);
    r = p;
  }
}

void lmn_mem_add_ruleset(LmnMembrane *mem, LmnRuleSet *ruleset)
{
  RuleSetNode *new_node = ruleset_node_make(ruleset);
  if (mem->rulesets) new_node->next = mem->rulesets;
  mem->rulesets = new_node;
}

/*----------------------------------------------------------------------
 * Atom Set
 */

static BOOL atom_list_is_empty(AtomSetEntry *entry)
{
  return !entry->head;
}

void lmn_mem_push_atom(LmnMembrane *mem, LmnAtomPtr ap)
{
  AtomSetEntry *as;

  as = (AtomSetEntry *)hashtbl_get_default(&mem->atomset, LMN_ATOM_GET_FUNCTOR(ap), 0);
  if (!as) {
    as = LMN_MALLOC(struct AtomSetEntry);
    as->head = as->tail = 0;
    hashtbl_put(&mem->atomset, LMN_ATOM_GET_FUNCTOR(ap), (HashKeyType)as);
  }

  LMN_ATOM_SET_NEXT(ap, 0);
  if (atom_list_is_empty(as)) {
    as->head = as->tail = ap;
    LMN_ATOM_SET_PREV(ap, 0);
  } else {
    LMN_ATOM_SET_PREV(ap, as->tail);
    LMN_ATOM_SET_NEXT(as->tail, ap);
    as->tail = ap;
  }
} 

LmnAtomPtr lmn_mem_pop_atom(LmnMembrane *mem, LmnFunctor f)
{
  LmnAtomPtr ap;
  AtomSetEntry *ent;

  LMN_ASSERT(hashtbl_contains(&mem->atomset, f));
  ent = (AtomSetEntry *)hashtbl_get(&mem->atomset, f);
  LMN_ASSERT(!atom_list_is_empty(ent));
  ap = ent->head;
  ent->head = LMN_ATOM_GET_NEXT(ap);
  return ap;
}

void lmn_mem_remove_atom(LmnMembrane *mem, LmnAtomPtr atom)
{
  LmnAtomPtr prev, next;

  prev = LMN_ATOM_GET_PREV(atom);
  next = LMN_ATOM_GET_NEXT(atom);
  if (next) LMN_ATOM_SET_PREV(next, prev);
  if (prev) LMN_ATOM_SET_NEXT(prev, next);

  /* リストがダミーheadを持たないのでこの処理が必要になる */
  if (next == NULL && prev == NULL) {
    AtomSetEntry *p = (AtomSetEntry *)hashtbl_get(&mem->atomset, LMN_ATOM_GET_FUNCTOR(atom));
    p->head = p->tail = NULL;
  }
	LMN_ATOM_SET_NEXT(atom, NULL); LMN_ATOM_SET_PREV(atom, NULL);
}

/*----------------------------------------------------------------------
 * Membrane
 */

LmnMembrane *lmn_mem_make(void)
{
  LmnMembrane *mem = LMN_MALLOC(LmnMembrane);
  memset(mem, 0, sizeof(LmnMembrane));
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
    LMN_FREE(hashiter_entry(&iter).data);
  }
  hashtbl_destroy(&mem->atomset);

  ruleset_free(mem->rulesets);
  LMN_FREE(mem);
}

void lmn_mem_push_mem(LmnMembrane *parentmem, LmnMembrane *newmem)
{
  /* TODO: membrane activation */
  newmem->next = parentmem->child_head;
  newmem->parent = parentmem;
  if(parentmem->child_head) parentmem->child_head->prev = newmem;
  parentmem->child_head = newmem;
}

AtomSetEntry *lmn_mem_get_atomlist(LmnMembrane *mem, LmnFunctor f)
{
  return (AtomSetEntry *)hashtbl_get(&mem->atomset, f);
}

unsigned int lmn_mem_natoms(LmnMembrane *mem)
{
  unsigned int n = 0;
  HashIterator iter;

  for (iter = hashtbl_iterator(&mem->atomset);
       !hashiter_isend(&iter);
       hashiter_next(&iter)) {
    AtomSetEntry *ent = (AtomSetEntry *)hashiter_entry(&iter).data;
    if (!atom_list_is_empty(ent)) {
      LmnAtomPtr atom = ent->head;
      while (atom) {
        atom = LMN_ATOM_GET_NEXT(atom);
        n++;
      }
    }
  }
  return n;
}

unsigned int lmn_mem_nmems(LmnMembrane *mem)
{
	unsigned int i = 0;
	LmnMembrane* mp = mem->child_head;
	for(; mp; mp=mp->next, i++);
  return i;
}

/*----------------------------------------------------------------------
 * Dump
 */

struct AtomRec {
  BOOL done;
  SimpleHashtbl args;
};

struct DumpState {
  int link_num;
};

#define LINK_FORMAT "L%d"

static struct AtomRec *atomrec_make()
{
  struct AtomRec *a = LMN_MALLOC(struct AtomRec);
  a->done = FALSE;
  hashtbl_init(&a->args, 16);
  return a;
}

static void atomrec_free(struct AtomRec *a)
{
  hashtbl_destroy(&a->args);
  LMN_FREE(a);
}

/* TODO: ちゃんとした実装はプロキシを実装してから */
static void dump_atom(LmnAtomPtr atom,
                      SimpleHashtbl *ht,
                      int link_pos,
                      struct DumpState *s,
                      int indent)
{
  LmnFunctor f;
  LmnArity arity;
  int i;
  int limit;
  struct AtomRec *t;
  
  LMN_ASSERT(atom);
  
  if (hashtbl_contains(ht, (HashKeyType)atom)) {
    t = (struct AtomRec *)hashtbl_get(ht, (HashKeyType)atom);
  } else {
    t = atomrec_make();
    hashtbl_put(ht, (HashKeyType)atom, (HashValueType)t);
  }

  if (link_pos >= 0 && t->done) { /* 閉路 */
    int link = s->link_num++;
    hashtbl_put(&t->args, link_pos, link);
    fprintf(stdout, LINK_FORMAT, link);
    return;
  }
  if (t->done) return;

  t->done = TRUE;
  f = LMN_ATOM_GET_FUNCTOR(atom);
  arity = LMN_FUNCTOR_ARITY(f);

  fprintf(stdout, "%s", LMN_SYMBOL_STR(LMN_FUNCTOR_NAME_ID(f)));
  limit = link_pos < 0 ? arity : arity - 1;
  if (limit > 0) {
    fprintf(stdout, "(");
    for (i = 0; i < limit; i++) {
      LmnLinkAttr attr;

      if (i > 0) fprintf(stdout, ", ");
      attr = LMN_ATOM_GET_LINK_ATTR(atom,i);
      if (LMN_ATTR_IS_DATA(attr)) {
        switch (LMN_ATTR_GET_VALUE(attr)) {
        case  LMN_ATOM_IN_PROXY_ATTR:
          /* TODO プロキシをたどってリンク先のアトムのに出力するリンク番号を知らせる*/
          fprintf(stdout, "$in");
          break;
        case  LMN_ATOM_OUT_PROXY_ATTR:
          fprintf(stdout, "$out");
          break;
        case  LMN_ATOM_INT_ATTR:
          fprintf(stdout, "%d", (int)LMN_ATOM_GET_LINK(atom,i));
          break;
        case  LMN_ATOM_DBL_ATTR:
          fprintf(stdout, "%f", *(double*)LMN_ATOM_GET_LINK(atom,i));
          break;
        default:
          fprintf(stdout, "unknown data type[%d], ", LMN_ATTR_GET_VALUE(attr));
          break;
        }
      } else { /* symbol atom */
        if (hashtbl_contains(&t->args, i)) {
          int link = hashtbl_get(&t->args, i);
          fprintf(stdout, LINK_FORMAT, link);
        }
        else {
          dump_atom(LMN_ATOM(LMN_ATOM_GET_LINK(atom, i)),
                    ht,
                    LMN_ATTR_GET_VALUE(LMN_ATOM_GET_LINK_ATTR(atom, i)),
                    s,
                    indent);
        }
      }
    }
    fprintf(stdout, ")");
  }

  if (link_pos<0) fprintf(stdout, ". ");
}

static void dump_ruleset(RuleSetList *p, int indent)
{
  int i;

  for (i = 0; p; i++) {
    if (i > 0 && p->next) fprintf(stdout, ", ");
    fprintf(stdout, "@%d", p->ruleset->id);
    p = p->next;
  }
  if (i > 0) fprintf(stdout, ". ");
}
                  
#define INDENT_INCR 2

static void lmn_mem_dump_internal(LmnMembrane *mem,
                                  SimpleHashtbl *ht,
                                  struct DumpState *s,
                                  int indent)
{
  unsigned int i, j;
  enum {P0, P1, P2, P3, PRI_NUM};
  SimpleHashtbl pred_atoms[PRI_NUM];
  HashIterator iter;

  if (!mem) return;

  if (hashtbl_contains(ht, (HashKeyType)mem)) return;
  hashtbl_put(ht, (HashKeyType)mem, 0);

  for (i = 0; i < PRI_NUM; i++) {
    hashtbl_init(&pred_atoms[i], 16);
  }

  /* 優先順位に応じて起点となるアトムを振り分ける */
  /* P0 : 0引数アトム */

  for (iter = hashtbl_iterator(&mem->atomset);
       !hashiter_isend(&iter);
       hashiter_next(&iter)) {
    AtomSetEntry *ent = (AtomSetEntry *)hashiter_entry(&iter).data;

    if (!atom_list_is_empty(ent)) {
      LmnAtomPtr atom;
      for (atom = ent->head; atom; atom = LMN_ATOM_GET_NEXT(atom)) {
        int arity = LMN_ATOM_GET_ARITY(atom);
        if (arity == 0) {
          hashtbl_put(&pred_atoms[P0], hashtbl_num(&pred_atoms[P0]), (HashValueType)atom);
        }
        else if (arity == 1 &&
                 (LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(atom, 0)) ||
                  LMN_ATTR_GET_VALUE(LMN_ATOM_GET_LINK_ATTR(atom, 0)) ==
                  LMN_ATOM_GET_ARITY(LMN_ATOM_GET_LINK(atom, 0)) - 1)) {
          hashtbl_put(&pred_atoms[P1], hashtbl_num(&pred_atoms[P1]), (HashValueType)atom);
        }
        else if (arity > 1 &&
                 (LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(atom, arity-1)) ||
                  LMN_ATTR_GET_VALUE(LMN_ATOM_GET_LINK_ATTR(atom, arity-1)) ==
                  LMN_ATOM_GET_ARITY(LMN_ATOM_GET_LINK(atom, arity-1)) - 1)) {
          hashtbl_put(&pred_atoms[P2], hashtbl_num(&pred_atoms[P2]), (HashValueType)atom);
        }
        else {
          hashtbl_put(&pred_atoms[P3], hashtbl_num(&pred_atoms[P3]), (HashValueType)atom);
        }
      }
    }
  }


  fprintf(stdout, "{");
  for (i = 0; i < PRI_NUM; i++) {
    for (j = 0; j < hashtbl_num(&pred_atoms[i]); j++) {
      dump_atom(LMN_ATOM(hashtbl_get(&pred_atoms[i], j)),
                ht,
                -1,
                s,
                indent + INDENT_INCR);
    }
  }
  dump_ruleset(mem->rulesets, indent);
  lmn_mem_dump_internal(mem->child_head, ht, s, indent + INDENT_INCR);
  lmn_mem_dump_internal(mem->next, ht, s, indent);


  fprintf(stdout, "}");
  
  for (i = 0; i < PRI_NUM; i++) {
    hashtbl_destroy(&pred_atoms[i]);
  }


}

static void lmn_mem_dump_dev(LmnMembrane *mem);

/* print membrane structure */
/* 出力順序
   1. 0引数アトム
*/
void lmn_mem_dump(LmnMembrane *mem)
{
  SimpleHashtbl ht;
  struct DumpState s;
  s.link_num = 0;
    

  if (lmn_env.dev_dump) {
    lmn_mem_dump_dev(mem);
    return;
  }

  if (!mem) return;

  hashtbl_init(&ht, 128);
  lmn_mem_dump_internal(mem, &ht, &s, 0);
  fprintf(stdout, "\n");

  {
    HashIterator iter;

    /* 開放処理. 今のところdataに0以外が入っていた場合
       struct AtomRecのポインタが格納されている */
    for (iter = hashtbl_iterator(&ht); !hashiter_isend(&iter); hashiter_next(&iter)) {
      if (hashiter_entry(&iter).data) {
        atomrec_free((struct AtomRec *)hashiter_entry(&iter).data);
      }
    }
    hashtbl_destroy(&ht);
  }
}

static void dump_atom_dev(LmnAtomPtr atom)
{
  LmnFunctor f;
  LmnArity arity;
  unsigned int i;
  
  f = LMN_ATOM_GET_FUNCTOR(atom);
  arity = LMN_FUNCTOR_ARITY(f);
  fprintf(stdout, "Func[%u], Name[%s], A[%u], Addr[%p], ", f, LMN_SYMBOL_STR(LMN_FUNCTOR_NAME_ID(f)), arity, (void*)atom);

  for (i = 0; i < arity; i++) {
    LmnLinkAttr attr;

    fprintf(stdout, "%u: ", i);
    attr = LMN_ATOM_GET_LINK_ATTR(atom,i);
    if (LMN_ATTR_IS_DATA(attr)) {
      switch (LMN_ATTR_GET_VALUE(attr)) {
      case  LMN_ATOM_IN_PROXY_ATTR:
        fprintf(stdout, "in-proxy[%lu], ", LMN_ATOM_GET_LINK(atom,i));
        break;
      case  LMN_ATOM_OUT_PROXY_ATTR:
        fprintf(stdout, "out-proxy[%lu], ", LMN_ATOM_GET_LINK(atom,i));
        break;
      case  LMN_ATOM_INT_ATTR:
        fprintf(stdout, "int[%lu], ", LMN_ATOM_GET_LINK(atom,i));
        break;
      case  LMN_ATOM_DBL_ATTR:
        fprintf(stdout, "double[%f], ", *(double*)LMN_ATOM_GET_LINK(atom,i));
        break;
      default:
        fprintf(stdout, "unknown data type[%d], ", LMN_ATTR_GET_VALUE(attr));
        break;
      }
    } else { /* symbol atom */
      fprintf(stdout, "link[%d, %p], ", LMN_ATTR_GET_VALUE(attr), (void*)LMN_ATOM_GET_LINK(atom, i));
    }
  }

  fprintf(stdout, "\n");
}

static void dump_ruleset_dev(RuleSetList *p)
{
  fprintf(stdout, "ruleset[");
  while (p) {
    fprintf(stdout, "%d ", p->ruleset->id);
    p = p->next;
  }
  fprintf(stdout, "]\n");
}
                  

static void lmn_mem_dump_dev(LmnMembrane *mem)
{
  HashIterator iter;


  if (!mem) return;
  
  fprintf(stdout, "{\n");
  for (iter = hashtbl_iterator(&mem->atomset);
       !hashiter_isend(&iter);
       hashiter_next(&iter)) {
    AtomSetEntry *ent = (AtomSetEntry *)hashiter_entry(&iter).data;
    if (!atom_list_is_empty(ent)) {
      LmnAtomPtr atom = ent->head;
      while (atom) {
        dump_atom_dev(atom);
        atom = LMN_ATOM_GET_NEXT(atom);
      }
    }
  }

  dump_ruleset_dev(mem->rulesets);
  lmn_mem_dump_dev(mem->child_head);
  lmn_mem_dump_dev(mem->next);
  
  fprintf(stdout, "}\n");
}


#ifdef DEBUG
void test_mem(void)
{
  LmnMembrane *mem;
  LmnAtomPtr atom;

  printf("--- test mem ---\n");
  mem = lmn_mem_make();
  atom = lmn_new_atom((LmnFunctor)1);
  lmn_mem_push_atom(mem, atom);
  lmn_mem_push_atom(mem, lmn_new_atom((LmnFunctor)1));
  lmn_mem_push_atom(mem, lmn_new_atom((LmnFunctor)1));

  lmn_mem_dump(mem);
}

#endif
