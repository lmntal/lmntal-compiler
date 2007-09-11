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

static RuleSetNode *make_ruleset_node(LmnRuleSet *ruleset)
{
  RuleSetNode *node = LMN_MALLOC(RuleSetNode);
  node->ruleset = ruleset;
  node->next = 0;
  return node;
}

void lmn_mem_add_ruleset(LmnMembrane *mem, LmnRuleSet *ruleset)
{
  RuleSetNode *new_node = make_ruleset_node(ruleset);
  if (mem->rulesets) new_node->next = mem->rulesets;
  mem->rulesets = new_node;
}

/*----------------------------------------------------------------------
 * Atom Set
 */

/* TODO stub implementation */

struct AtomSetEntry {
  LmnAtomPtr head, tail;
};

struct AtomSet {
  AtomSetEntry atoms[1<<LMN_FUNCTOR_BITS];
};

static AtomSet *make_atom_set(int init_size)
{
  struct AtomSet *a = LMN_MALLOC(struct AtomSet);
  memset(a->atoms, 0, sizeof(a->atoms));
  return a;
}

static inline AtomSetEntry *get_atom_list(struct AtomSet *atomset, LmnFunctor functor)
{
  return &atomset->atoms[functor];
}

static inline BOOL atom_list_is_empty(AtomSetEntry *entry)
{
  return !entry->head;
}

void lmn_mem_push_atom(LmnMembrane *mem, LmnAtomPtr ap)
{
  AtomSetEntry *as = get_atom_list(mem->atomset, LMN_ATOM_FUNCTOR(ap));
  
  LMN_ATOM_SET_NEXT(ap, 0);
  if (atom_list_is_empty(&as)) {
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
  AtomSetEntry *as = get_atom_list(mem->atomset, LMN_ATOM_FUNCTOR(ap));
  LmnAtomPtr ap;
  
  LMN_ASSERT(!atom_list_is_empty(as));
  ap = as->head;
  as->head = as->next;
  return ap;
}
