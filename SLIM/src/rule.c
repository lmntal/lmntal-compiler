/*
 * rule.c
 */

#include "lmntal.h"

/* move ownship of instr */
LmnRule *lmn_rule_make(LmnRuleInstr instr, lmn_interned_str name)
{
  LmnRule *rule = LMN_MALLOC(LmnRule);
  rule->instr = instr;
  rule->name = name;
  return rule;
}

void lmn_rule_free(LmnRule *rule)
{
  LMN_FREE(rule->instr);
  LMN_FREE(rule);
}

/*----------------------------------------------------------------------
 * Rule Set
 */

#define GROWN_RATE 2.0

LmnRuleSet *lmn_ruleset_make(LmnRulesetId id, lmn_ruleset_size_t init_size)
{
  LmnRuleSet *ruleset = LMN_MALLOC(LmnRuleSet);
  ruleset->id = id;
  ruleset->rules = LMN_CALLOC(LmnRule*, init_size);
  ruleset->num = 0;
  ruleset->cap = init_size;
  return ruleset;
}

void lmn_ruleset_free(LmnRuleSet *ruleset)
{
  int i;

  for (i = 0; i < ruleset->num; i++) lmn_rule_free(ruleset->rules[i]);
  LMN_FREE(ruleset->rules);
  LMN_FREE(ruleset);
}

/* move rule's ownership */
void lmn_ruleset_put(LmnRuleSet* ruleset, LmnRule *rule)
{
  if (ruleset->num == ruleset->cap) {
    ruleset->cap = (lmn_ruleset_size_t)(ruleset->cap * GROWN_RATE);
    ruleset->rules = LMN_REALLOC(LmnRule*, ruleset->rules, ruleset->cap);
  }
  ruleset->rules[ruleset->num++] = rule;
}

#undef GROWN_RATE
