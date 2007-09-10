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

struct AtomSet {
  LmnAtomPtr atoms[1<<LMN_FUNCTOR_BITS];
};

static AtomSet *make_atom_set(int init_size)
{
  struct AtomSet *a = LMN_MALLOC(struct AtomSet);
  memset(a->atoms, 0, sizeof(a->atoms));
  return a;
}

static LmnAtomPtr get_atom_list(struct AtomSet *atom_set, LmnFunctor functor)
{
  return atom_set->atoms[functor];
}
