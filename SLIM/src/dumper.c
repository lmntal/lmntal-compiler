/*
 * dumper.c - dump membrane
 */

#include <ctype.h>
#include "dumper.h"
#include "internal_hash.h"
#include "vector.h"
#include "rule.h"
#include "membrane.h"

#define MAX_DEPTH 1000
#define LINK_FORMAT "L%d"

struct AtomRec {
  BOOL done;
  SimpleHashtbl args;
  int link_num; /* proxy only */
};

struct DumpState {
  int link_num;
};

static BOOL dump_atom(LmnAtomPtr atom,
                      SimpleHashtbl *ht,
                      LmnLinkAttr attr,
                      struct DumpState *s,
                      int indent,
                      int call_depth);
static void lmn_dump_cell_internal(LmnMembrane *mem,
                                   SimpleHashtbl *ht,
                                   struct DumpState *s,
                                   int indent);

static struct AtomRec *atomrec_make()
{
  struct AtomRec *a = LMN_MALLOC(struct AtomRec);
  a->done = FALSE;
  hashtbl_init(&a->args, 16);
  a->link_num = -1;
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

static void dump_atomname(LmnFunctor f)
{
  char *s = LMN_SYMBOL_STR(LMN_FUNCTOR_NAME_ID(f));

  if (LMN_IS_PROXY_FUNCTOR(f) || is_direct_printable(s)) {
    fprintf(stdout, "%s", s);
  } else {
    fprintf(stdout, "'%s'", s);
  }
}

static BOOL dump_data_atom(LmnWord data,
                           SimpleHashtbl *ht,
                           LmnLinkAttr attr,
                           struct DumpState *s,
                           int indent,
                           int call_depth)
{
  /* print only data (no link) */
  switch (attr) {
  case  LMN_ATOM_INT_ATTR:
    fprintf(stdout, "%d", (int)data);
    break;
  case  LMN_ATOM_DBL_ATTR:
    fprintf(stdout, "%f", *(double*)data);
    break;
  default:
    fprintf(stdout, "* ", attr);
    LMN_ASSERT(FALSE);
    break;
  }
  return TRUE;
}

static BOOL dump_list(LmnAtomPtr atom,
                      SimpleHashtbl *ht,
                      struct DumpState *s,
                      int indent,
                      int call_depth)
{
  BOOL first = TRUE;
  LmnLinkAttr attr;

  if (get_atomrec(ht, atom)->done) return FALSE;

  attr = LMN_ATTR_MAKE_LINK(2); /* 2 is output link position */

  fprintf(stdout, "[");
  while (TRUE) {
    LmnFunctor f = LMN_ATOM_GET_FUNCTOR(atom);
    if (!LMN_ATTR_IS_DATA(attr) &&
        f == LMN_LIST_FUNCTOR   &&
        LMN_ATTR_GET_VALUE(attr) == 2) {
      struct AtomRec *rec;

      rec = get_atomrec(ht, atom);
      
      if (rec->done) { /* cyclic */
        int link = s->link_num++;
        fprintf(stdout, "|");
        hashtbl_put(&rec->args, LMN_ATTR_GET_VALUE(attr), link);
        fprintf(stdout, LINK_FORMAT, link);
        break;
      }
      rec->done = TRUE;

      if (!first) fprintf(stdout, ",");
      first = FALSE;

      if (hashtbl_contains(&rec->args, 0)) {
        /* link 0 was already printed */
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
    else { /* list ends with non nil data */
      fprintf(stdout, "|");
      dump_atom(atom, ht, LMN_ATTR_GET_VALUE(attr), s, indent, call_depth + 1);
      break;
    }
  }
  fprintf(stdout, "]");
  return TRUE;
}

/* propagate a link number to connected proxies */
static void propagate_proxy_link(LmnAtomPtr atom,
                                 LmnLinkAttr attr,
                                 SimpleHashtbl *ht,
                                 int link_num)
{
  struct AtomRec *t;
  int i;
  
  if (LMN_ATTR_IS_DATA(attr)) return;
  if (LMN_ATOM_GET_FUNCTOR(atom) != LMN_IN_PROXY_FUNCTOR &&
      LMN_ATOM_GET_FUNCTOR(atom) != LMN_OUT_PROXY_FUNCTOR) return;
  t = get_atomrec(ht, atom);
  if (t->link_num >= 0) return;
  
  t->link_num = link_num;
  for (i = 0; i < 2; i++) {
    propagate_proxy_link(LMN_ATOM(LMN_ATOM_GET_LINK(atom, i)),
                         LMN_ATOM_GET_LINK_ATTR(atom, i),
                         ht,
                         link_num);
  }
}

/* assign a link number to all connected proxies */
static void assign_link_to_proxy(LmnAtomPtr atom, SimpleHashtbl *ht, struct DumpState *s)
{
  struct AtomRec *t;

  t = get_atomrec(ht, atom);
  if (t->link_num < 0) {
    int link_num = s->link_num++;
    propagate_proxy_link(atom, LMN_ATTR_MAKE_LINK(0), ht, link_num);
  }
}
   
static BOOL dump_proxy(LmnAtomPtr atom,
                       SimpleHashtbl *ht,
                       int link_pos,
                       struct DumpState *s,
                       int indent,
                       int call_depth)
{
  struct AtomRec *t;
  t = get_atomrec(ht, atom);
  t->done = TRUE;

  if (call_depth == 0) {
    LmnLinkAttr attr = LMN_ATOM_GET_LINK_ATTR(atom, 1);
    if (LMN_ATTR_IS_DATA(attr)) {
      dump_data_atom((LmnWord)LMN_ATOM_GET_LINK(atom, 1), ht, attr, s, indent, call_depth);
      fprintf(stdout, "(" LINK_FORMAT "). ", t->link_num);
    } else {
      /* symbol atom has dumped */
      return FALSE;
    }
  }
  else {
    fprintf(stdout, LINK_FORMAT, t->link_num);
  }
  return TRUE;
}

static BOOL dump_symbol_atom(LmnAtomPtr atom,
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
  if (LMN_IS_PROXY_FUNCTOR(f)) arity--;
  
  t = get_atomrec(ht, atom);

  if ((call_depth > 0 && link_pos != arity - 1) || /* not last link */
      (call_depth > 0 && t->done)               || /* already printed */
      call_depth > MAX_DEPTH) {                    /* limit overflow */
    int link;
    if (hashtbl_contains(&t->args, link_pos)) {
      link = hashtbl_get(&t->args, link_pos);
    } else {
      link = s->link_num++;
      hashtbl_put(&t->args, link_pos, link);
    }
    fprintf(stdout, LINK_FORMAT, link);
    return TRUE;
  }
  
  if (t->done) return FALSE;
  t->done = TRUE;

  dump_atomname(f);
  limit = arity;
  if (call_depth > 0) limit--;

  if (limit > 0) {
    fprintf(stdout, "(");
    for (i = 0; i < limit; i++) {
      if (i > 0) fprintf(stdout, ",");

      if (hashtbl_contains(&t->args, i)) {
        /* argument has link number */
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

  return TRUE;
}

static BOOL dump_atom(LmnAtomPtr atom,
                      SimpleHashtbl *ht,
                      LmnLinkAttr attr,
                      struct DumpState *s,
                      int indent,
                      int call_depth)
{
  if (LMN_ATTR_IS_DATA(attr)) {
    return dump_data_atom((LmnWord)atom, ht, attr, s, indent, call_depth);
  }
  else {
    LmnFunctor f = LMN_ATOM_GET_FUNCTOR(atom);
    LmnLinkAttr link_pos = LMN_ATTR_GET_VALUE(attr);
    if (!lmn_env.show_proxy &&
        (f == LMN_IN_PROXY_FUNCTOR ||
         f == LMN_OUT_PROXY_FUNCTOR)) {
      return dump_proxy(atom, ht, attr, s, indent, call_depth);
    }
    else if (f == LMN_LIST_FUNCTOR &&
             link_pos == 2) {
      return dump_list(atom, ht, s, indent, call_depth);
    }
    else {
      return dump_symbol_atom(atom, ht, link_pos, s, indent, call_depth);
    }
  }
}

/* atom must be a symbol atom */
static BOOL dump_toplevel_atom(LmnAtomPtr atom,
                               SimpleHashtbl *ht,
                               struct DumpState *s,
                               int indent)
{
  if (!lmn_env.show_proxy &&
      (LMN_ATOM_GET_FUNCTOR(atom) == LMN_IN_PROXY_FUNCTOR ||
       LMN_ATOM_GET_FUNCTOR(atom) == LMN_OUT_PROXY_FUNCTOR)) {
    return dump_proxy(atom, ht, LMN_ATTR_MAKE_LINK(0), s, indent, 0);
  }
  else {
    return dump_symbol_atom(atom, ht, LMN_ATTR_MAKE_LINK(0), s, indent, 0);
  }
}


static void dump_ruleset(struct Vector *v, int indent)
{
  unsigned int i;

  for (i = 0; i < v->num; i++) {
    if (i > 0) fprintf(stdout, ",");
    fprintf(stdout, "@%d", ((LmnRuleSet *)vec_get(v, i))->id);
  }
}
                  
#define INDENT_INCR 2

static void lmn_mem_dump_internal(LmnMembrane *mem,
                                  SimpleHashtbl *ht,
                                  struct DumpState *s,
                                  int indent)
{
  if (mem->name != NULL_STRING_ID) {
    fprintf(stdout, "%s", LMN_SYMBOL_STR(mem->name));
  }
  fprintf(stdout, "{");
  lmn_dump_cell_internal(mem, ht, s, indent);
  fprintf(stdout, "}");
}

static void lmn_dump_cell_internal(LmnMembrane *mem,
                                  SimpleHashtbl *ht,
                                  struct DumpState *s,
                                  int indent)
{
  unsigned int i, j;
  enum {P0, P1, P2, P3, PROXY, PRI_NUM};
  Vector pred_atoms[PRI_NUM];
  HashIterator iter;
  BOOL printed;

  if (!mem) return;

  if (hashtbl_contains(ht, (HashKeyType)mem)) return;

  for (i = 0; i < PRI_NUM; i++) {
    vec_init(&pred_atoms[i], 16);
  }

  /* 優先順位に応じて起点となるアトムを振り分ける */

  for (iter = hashtbl_iterator(&mem->atomset);
       !hashiter_isend(&iter);
       hashiter_next(&iter)) {
    AtomSetEntry *ent = (AtomSetEntry *)hashiter_entry(&iter)->data;
    LmnFunctor f = hashiter_entry(&iter)->key;
    LmnAtomPtr atom;

    for (atom = atomlist_head(ent);
         atom != lmn_atomset_end(ent);
         atom = LMN_ATOM_GET_NEXT(atom)) {
      int arity = LMN_ATOM_GET_ARITY(atom);
      if (f == LMN_IN_PROXY_FUNCTOR ||
          f == LMN_OUT_PROXY_FUNCTOR) {
        vec_push(&pred_atoms[PROXY], (LmnWord)atom);
      }
      /* 0 argument atom */
      else if (arity == 0) { 
        vec_push(&pred_atoms[P0], (LmnWord)atom);
      }
      /* 1 argument, link to the last argument */
      else if (arity == 1 &&
               (LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(atom, 0)) ||
                (int)LMN_ATTR_GET_VALUE(LMN_ATOM_GET_LINK_ATTR(atom, 0)) ==
                LMN_ATOM_GET_ARITY(LMN_ATOM_GET_LINK(atom, 0)) - 1)) {
        vec_push(&pred_atoms[P1], (LmnWord)atom);
      }
      /* link to the last argument */
      else if (arity > 1 &&
               (LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(atom, arity-1)) ||
                (int)LMN_ATTR_GET_VALUE(LMN_ATOM_GET_LINK_ATTR(atom, arity-1)) ==
                LMN_ATOM_GET_ARITY(LMN_ATOM_GET_LINK(atom, arity-1)) - 1)) {
        vec_push(&pred_atoms[P2], (LmnWord)atom);
      }
      else {
        vec_push(&pred_atoms[P3], (LmnWord)atom);
      }
    }
  }

  if (!lmn_env.show_proxy) {
    /* assign link to proxies */
    for (i = 0; i < pred_atoms[PROXY].num; i++) {
      assign_link_to_proxy(LMN_ATOM(vec_get(&pred_atoms[PROXY], i)), ht, s);
    }
  }

  printed = FALSE;
  { /* dump atoms */
    for (i = 0; i < PRI_NUM; i++) {
      for (j = 0; j < pred_atoms[i].num; j++) {
        LmnAtomPtr atom = LMN_ATOM(vec_get(&pred_atoms[i], j));
        if (dump_toplevel_atom(atom, ht, s, indent + INDENT_INCR)) {
          /* TODO アトムの出力の後には常に ". "が入ってしまう.
             アトムの間に ", "を挟んだ方が見栄えが良い */
          fprintf(stdout, ". ");
          printed = TRUE;
        }
      }
    }
  }
  for (i = 0; i < PRI_NUM; i++) {
    vec_destroy(&pred_atoms[i]);
  }

  { /* dump chidren */
    LmnMembrane *m;
    for (m = mem->child_head; m; m = m->next) {
      lmn_mem_dump_internal(m, ht, s, indent);
      if (m->next)
        fprintf(stdout, ", ");
    }
    if (mem->child_head) {
      /* 最後の膜の後に ". "を出力 */
      fprintf(stdout, ". ");
    }
  }

  if (lmn_env.show_ruleset) {
    dump_ruleset(&mem->rulesets, indent);
  }
}

static void lmn_dump_cell_nonewline(LmnMembrane *mem)
{
  SimpleHashtbl ht;
  struct DumpState s;
  s.link_num = 0;

  hashtbl_init(&ht, 128);
  lmn_dump_cell_internal(mem, &ht, &s, 0);

  {
    HashIterator iter;

    /* 開放処理. 今のところdataに0以外が入っていた場合
       struct AtomRecのポインタが格納されている */
    for (iter = hashtbl_iterator(&ht); !hashiter_isend(&iter); hashiter_next(&iter)) {
      if (hashiter_entry(&iter)->data) {
        atomrec_free((struct AtomRec *)hashiter_entry(&iter)->data);
      }
    }
    hashtbl_destroy(&ht);
  }
}

void lmn_dump_cell(LmnMembrane *mem)
{
  lmn_dump_cell_nonewline(mem);
  fprintf(stdout, "\n");
}

static void lmn_mem_dump_dev(LmnMembrane *mem);

/* print membrane structure */
void lmn_mem_dump(LmnMembrane *mem)
{
  if (lmn_env.dev_dump) {
    lmn_mem_dump_dev(mem);
    return;
  }
  /* TODO: 幕名を表示。　関数の構成を見直す */
  fprintf(stdout, "{");
  lmn_dump_cell_nonewline(mem);
  fprintf(stdout, "}\n");
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

static void dump_ruleset_dev(struct Vector *v)
{
  unsigned int i;
  fprintf(stdout, "ruleset[");
  for (i = 0;i < v->num; i++) {
    fprintf(stdout, "%d ", ((LmnRuleSet *)vec_get(v, i))->id);
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
    AtomSetEntry *ent = (AtomSetEntry *)hashiter_entry(&iter)->data;
    LmnAtomPtr atom;

    for (atom = atomlist_head(ent);
         atom != lmn_atomset_end(ent);
         atom = LMN_ATOM_GET_NEXT(atom)) {
      dump_atom_dev(atom);
    }
  }

  dump_ruleset_dev(&mem->rulesets);
  lmn_mem_dump_dev(mem->child_head);
  lmn_mem_dump_dev(mem->next);
  
  fprintf(stdout, "}\n");
}
