/*
 * membrane.c
 */

#include "lmntal.h"
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

/* TODO stub implementation */

static void atom_set_entry_free(AtomSetEntry entry)
{
  LmnAtomPtr p, q;

  p = entry.head;
  while (p) {
    q = LMN_ATOM_GET_NEXT(p);
    LMN_FREE(p);
    p = q;
  }
}

static AtomSet *atom_set_make(int init_size)
{
  struct AtomSet *a = LMN_MALLOC(struct AtomSet);
  memset(a->atoms, 0, sizeof(a->atoms));
  a->size = sizeof(a->atoms)/sizeof(a->atoms[0]);
  return a;
}

static void atom_set_free(AtomSet *atomset)
{
  unsigned int i;

  for (i = 0; i < atomset->size; i++) {
    atom_set_entry_free(atomset->atoms[i]);
  }
  LMN_FREE(atomset);
}

static AtomSetEntry *get_atom_list(struct AtomSet *atomset, LmnFunctor functor)
{
  return &atomset->atoms[functor];
}

static BOOL atom_list_is_empty(AtomSetEntry *entry)
{
  return !entry->head;
}

void lmn_mem_push_atom(LmnMembrane *mem, LmnAtomPtr ap)
{
  AtomSetEntry *as = get_atom_list(mem->atomset, LMN_ATOM_GET_FUNCTOR(ap));
  
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
  AtomSetEntry *ent = get_atom_list(mem->atomset, f);
  
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

  if (next == NULL && prev == NULL) {
    AtomSetEntry *p = lmn_mem_get_atomlist(mem, LMN_ATOM_GET_FUNCTOR(atom));
    p->head = p->tail = NULL;
  }
}

/*----------------------------------------------------------------------
 * Membrane
 */

LmnMembrane *lmn_mem_make(void)
{
  LmnMembrane *mem = LMN_MALLOC(LmnMembrane);
  memset(mem, 0, sizeof(LmnMembrane));
  mem->atomset = atom_set_make(100);
  return mem;
}

void lmn_mem_free(LmnMembrane *mem)
{
  atom_set_free(mem->atomset);
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
  return get_atom_list(mem->atomset, f);
}


unsigned int lmn_mem_natoms(LmnMembrane *mem)
{
  unsigned int i, j = 0;
  for (i = 0; i < mem->atomset->size; i++) {
    AtomSetEntry *ent = get_atom_list(mem->atomset, (LmnFunctor)i);
    if (!atom_list_is_empty(ent)) {
      LmnAtomPtr atom = ent->head;
      while (atom) {
        atom = LMN_ATOM_GET_NEXT(atom);
        j++;
      }
    }
  }
  return j;
}
/*----------------------------------------------------------------------
 * Dump
 */

static void dump_atom(LmnAtomPtr atom)
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

static void dump_ruleset(RuleSetList *p)
{
  fprintf(stdout, "ruleset[");
  while (p) {
    fprintf(stdout, "%d ", p->ruleset->id);
    p = p->next;
  }
  fprintf(stdout, "]\n");
}
                  

void lmn_mem_dump(LmnMembrane *mem)
{
  unsigned int i;

  if (!mem) return;
  
  fprintf(stdout, "{\n");
  for (i = 0; i < mem->atomset->size; i++) {
    AtomSetEntry *ent = get_atom_list(mem->atomset, (LmnFunctor)i);
    if (!atom_list_is_empty(ent)) {
      LmnAtomPtr atom = ent->head;
      while (atom) {
        dump_atom(atom);
        atom = LMN_ATOM_GET_NEXT(atom);
      }
    }
  }
  dump_ruleset(mem->rulesets);
  lmn_mem_dump(mem->child_head);
  lmn_mem_dump(mem->next);
  
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
