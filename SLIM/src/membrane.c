/*
 * membrane.c
 */

#include <ctype.h>
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
  return entry->head == (LmnAtomPtr)entry;
}

void lmn_mem_push_atom(LmnMembrane *mem, LmnAtomPtr ap)
{
  AtomSetEntry *as;
  as = (AtomSetEntry *)hashtbl_get_default(&mem->atomset, LMN_ATOM_GET_FUNCTOR(ap), 0);
  if (!as) {
    as = LMN_MALLOC(struct AtomSetEntry);
    LMN_ATOM_SET_PREV(as, as);
    LMN_ATOM_SET_NEXT(as, as);
    hashtbl_put(&mem->atomset, LMN_ATOM_GET_FUNCTOR(ap), (HashKeyType)as);
  }

  LMN_ATOM_SET_NEXT(ap, as);
  LMN_ATOM_SET_PREV(ap, as->tail);
  LMN_ATOM_SET_NEXT(as->tail, ap);
  as->tail = ap;
} 

void lmn_mem_remove_atom(LmnMembrane *mem, LmnAtomPtr atom)
{
  LmnAtomPtr prev, next;

  prev = LMN_ATOM_GET_PREV(atom);
  next = LMN_ATOM_GET_NEXT(atom);
  LMN_ASSERT(prev && next);
  LMN_ATOM_SET_PREV(next, prev);
  LMN_ATOM_SET_NEXT(prev, next);
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

/* return NULL when atomlist don't exists. */
AtomSetEntry* lmn_mem_get_atomlist(LmnMembrane *mem, LmnFunctor f)
{
  return (AtomSetEntry*)hashtbl_get_default(&mem->atomset, f, 0);
}

/* TODO: 全てのシンボルアトムをなめているので O(n) になっている */
/* TODO: データアトムの個数を数えていない. 正確に行うには
   引数の先を確かめる必要がある */
unsigned int lmn_mem_natoms(LmnMembrane *mem)
{
  unsigned int n = 0;
  HashIterator iter;

  for (iter = hashtbl_iterator(&mem->atomset);
       !hashiter_isend(&iter);
       hashiter_next(&iter)) {
    AtomSetEntry *ent = (AtomSetEntry *)hashiter_entry(&iter).data;
    LmnAtomPtr atom;
    for (atom = ent->head;
         atom != lmn_atomset_end(ent);
         atom = LMN_ATOM_GET_NEXT(atom)) {
      atom = LMN_ATOM_GET_NEXT(atom);
      n++;
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

#define MAX_DEPTH 1000
#define LINK_FORMAT "L%d"

struct AtomRec {
  BOOL done;
  SimpleHashtbl args;
};

struct DumpState {
  int link_num;
};

static void dump_atom(LmnAtomPtr atom,
                      SimpleHashtbl *ht,
                      LmnLinkAttr attr,
                      struct DumpState *s,
                      int indent,
                      int call_depth);

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

static BOOL is_direct_printable(char *s)
{
  LMN_ASSERT(*s);

  /* head character */
  if (!(isalpha(*s) && islower(*s))) return FALSE;
  while (*(++s)) {
    if (!(isalpha(*s) || isdigit(*s))) return FALSE;
  }
  return TRUE;
}

/* htからatomに対応するAtomRecを取得。なければ追加してから返す */
static struct AtomRec *get_atomrec(SimpleHashtbl *ht, LmnAtomPtr atom)
{
  if (hashtbl_contains(ht, (HashKeyType)atom)) {
    return (struct AtomRec *)hashtbl_get(ht, (HashKeyType)atom);
  } else {
    struct AtomRec *t;
    t = atomrec_make();
    hashtbl_put(ht, (HashKeyType)atom, (HashValueType)t);
    return t;
  }
}

static void dump_atomname(char *s)
{
  if (is_direct_printable(s)) {
    fprintf(stdout, "%s", s);
  } else {
    fprintf(stdout, "'%s'", s);
  }
}

static void dump_list(LmnAtomPtr atom,
                      SimpleHashtbl *ht,
                      struct DumpState *s,
                      int indent,
                      int call_depth) {
  BOOL first = TRUE;
  LmnLinkAttr attr;
  struct AtomRec *t;

  t = get_atomrec(ht, atom);
  if (t->done) return;

  attr = LMN_ATTR_MAKE_LINK(2);

  fprintf(stdout, "[");
  while (TRUE) {
    LmnFunctor f = LMN_ATOM_GET_FUNCTOR(atom);
    if (!LMN_ATTR_IS_DATA(attr) &&
        f == LMN_LIST_FUNCTOR   &&
        LMN_ATTR_GET_VALUE(attr) == 2) {
      struct AtomRec *rec;

      rec = get_atomrec(ht, atom);
      
      if (rec->done) { /* 閉路 */
        int link = s->link_num++;
        fprintf(stdout, " | ");
        hashtbl_put(&rec->args, LMN_ATTR_GET_VALUE(attr), link);
        fprintf(stdout, LINK_FORMAT, link);
        break;
      }
      rec->done = TRUE;

      if (!first) fprintf(stdout, ", ");
      first = FALSE;
      if (hashtbl_contains(&rec->args, 0)) {
        int link = hashtbl_get(&rec->args, 0);
        fprintf(stdout, LINK_FORMAT, link);
      }
      else {
        dump_atom(LMN_ATOM(LMN_ATOM_GET_LINK(atom, 0)),
                  ht,
                  LMN_ATOM_GET_LINK_ATTR(atom, 0),
                  s,
                  indent,
                  call_depth + 1);
      }
      attr = LMN_ATOM_GET_LINK_ATTR(atom, 1);
      atom = LMN_ATOM(LMN_ATOM_GET_LINK(atom, 1));
    }
    else if (!LMN_ATTR_IS_DATA(attr) &&
             f == LMN_NIL_FUNCTOR) {
      struct AtomRec *rec;
      rec = atomrec_make();
      rec->done = TRUE;
      hashtbl_put(ht, (HashKeyType)atom, (HashValueType)rec);
      break;
    }
    else {
      fprintf(stdout, " | ");
      dump_atom(atom, ht, LMN_ATTR_GET_VALUE(attr), s, indent, call_depth + 1);
      break;
    }
  }
  fprintf(stdout, "]");
}

static void dump_proxy(LmnAtomPtr atom,
                       SimpleHashtbl *ht,
                       int link_pos,
                       struct DumpState *s,
                       int indent,
                       int call_depth)
{
  struct AtomRec *t;
  t = atomrec_make();
  t->done = TRUE;
  hashtbl_put(ht, (HashKeyType)atom, (HashValueType)t);

  if (LMN_ATOM_GET_FUNCTOR(atom) == LMN_IN_PROXY_FUNCTOR) {
    fprintf(stdout, "$in");
  }
  if (LMN_ATOM_GET_FUNCTOR(atom) == LMN_OUT_PROXY_FUNCTOR) {
    fprintf(stdout, "$out");
  }
}

static void dump_data_atom(LmnWord data,
                           SimpleHashtbl *ht,
                           LmnLinkAttr attr,
                           struct DumpState *s,
                           int indent,
                           int call_depth)
{
  switch (attr) {
  case  LMN_ATOM_INT_ATTR:
    fprintf(stdout, "%d", (int)data);
    break;
  case  LMN_ATOM_DBL_ATTR:
    fprintf(stdout, "%f", *(double*)data);
    break;
  default:
    fprintf(stdout, "unknown data type[%d], ", attr);
    LMN_ASSERT(FALSE);
    break;
  }
}

/* TODO: ちゃんとした実装はプロキシを実装してから */
static void dump_symbol_atom(LmnAtomPtr atom,
                             SimpleHashtbl *ht,
                             int link_pos,
                             struct DumpState *s,
                             int indent,
                             int call_depth)
{
  LmnFunctor f;
  LmnArity arity;
  int i;
  int limit;
  struct AtomRec *t;
  
  f = LMN_ATOM_GET_FUNCTOR(atom);
  arity = LMN_FUNCTOR_ARITY(f);
  t = get_atomrec(ht, atom);

  if ((call_depth > 0 && link_pos != arity - 1) ||
      (call_depth > 0 && t->done)               ||
      call_depth > MAX_DEPTH) {
    int link;
    if (hashtbl_contains(&t->args, link_pos)) {
      link = hashtbl_get(&t->args, link_pos);
    } else {
      link = s->link_num++;
      hashtbl_put(&t->args, link_pos, link);
    }
    fprintf(stdout, LINK_FORMAT, link);
    return;
  }
  
  if (t->done) return;
  t->done = TRUE;

  dump_atomname(LMN_SYMBOL_STR(LMN_FUNCTOR_NAME_ID(f)));
  limit = call_depth == 0 ? arity : arity - 1;
  if (limit > 0) {
    fprintf(stdout, "(");
    for (i = 0; i < limit; i++) {
      if (i > 0) fprintf(stdout, ", ");

      if (hashtbl_contains(&t->args, i)) {
        int link = hashtbl_get(&t->args, i);
        fprintf(stdout, LINK_FORMAT, link);
      }
      else {
        dump_atom((LmnAtomPtr)LMN_ATOM_GET_LINK(atom, i),
                  ht,
                  LMN_ATOM_GET_LINK_ATTR(atom, i),
                  s,
                  indent,
                  call_depth + 1);
      }
    }
    fprintf(stdout, ")");
  }

  if (call_depth == 0) fprintf(stdout, ". ");
}

static void dump_atom(LmnAtomPtr atom,
                      SimpleHashtbl *ht,
                      LmnLinkAttr attr,
                      struct DumpState *s,
                      int indent,
                      int call_depth)
{
  if (LMN_ATTR_IS_DATA(attr)) {
    dump_data_atom((LmnWord)atom, ht, attr, s, indent, call_depth);
  }
  else {
    LmnFunctor f = LMN_ATOM_GET_FUNCTOR(atom);
    LmnLinkAttr link_pos = LMN_ATTR_GET_VALUE(attr);
    if (f == LMN_IN_PROXY_FUNCTOR ||
        f == LMN_OUT_PROXY_FUNCTOR) {
      dump_proxy(atom, ht, attr, s, indent, call_depth);
    }
    else if (f == LMN_LIST_FUNCTOR &&
             link_pos == 2) {
      dump_list(atom, ht, s, indent, call_depth);
    }
    else {
      dump_symbol_atom(atom, ht, link_pos, s, indent, call_depth);
    }
  }
}

/* atom must be a symbol atom */
static void dump_toplevel_atom(LmnAtomPtr atom,
                               SimpleHashtbl *ht,
                               struct DumpState *s,
                               int indent)
{
  LmnFunctor f = LMN_ATOM_GET_FUNCTOR(atom);
  dump_symbol_atom(atom, ht, LMN_ATTR_MAKE_LINK(0), s, indent, 0);
}


static void dump_ruleset(RuleSetList *p, int indent)
{
  int i;

  for (i = 0; p; i++) {
    if (i > 0 && p->next) fprintf(stdout, ", ");
    fprintf(stdout, "@%d", p->ruleset->id);
    p = p->next;
  }
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
    LmnFunctor f = hashiter_entry(&iter).key;
    LmnAtomPtr atom;

    if (f == LMN_IN_PROXY_FUNCTOR ||
        f == LMN_OUT_PROXY_FUNCTOR) {
      continue;
    }
    for (atom = ent->head;
         atom != lmn_atomset_end(ent);
         atom = LMN_ATOM_GET_NEXT(atom)) {
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


  fprintf(stdout, "{");
  for (i = 0; i < PRI_NUM; i++) {
    for (j = 0; j < hashtbl_num(&pred_atoms[i]); j++) {
      LmnAtomPtr atom = LMN_ATOM(hashtbl_get(&pred_atoms[i], j));
      dump_toplevel_atom(atom,
                ht,
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
      switch (attr) {
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
        fprintf(stdout, "unknown data type[%d], ", attr);
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
    LmnAtomPtr atom;

    for (atom = ent->head;
         atom != lmn_atomset_end(ent);
         atom = LMN_ATOM_GET_NEXT(atom)) {
      dump_atom_dev(atom);
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
