/*
 * rule.h
 */

#ifndef LMN_RULE_H
#define LMN_RULE_H

#include "vector.h"

typedef unsigned short lmn_ruleset_size_t;

struct LmnRule {
  LmnRuleInstr    instr;
  lmn_interned_str name;
};


struct LmnRuleSet {
  LmnRulesetId id;
  lmn_ruleset_size_t num, cap;
  LmnRule **rules;
};

/* ルールが関数で表されたルールセット */
typedef struct LmnCompiledRuleset {
  LmnRulesetId id;
  /* 固定長配列でいい気もしますが,とりあえずベクタで */
  Vector rules; /* pointer to rule function */
} LmnCompiledRuleset;

LMN_EXTERN LmnRule *lmn_rule_make(LmnRuleInstr instr, lmn_interned_str name);
LMN_EXTERN void lmn_rule_free(LmnRule *rule);
LMN_EXTERN LmnRuleSet *lmn_ruleset_make(LmnRulesetId id, lmn_ruleset_size_t init_size);
LMN_EXTERN void lmn_ruleset_free(LmnRuleSet *ruleset);
LMN_EXTERN void lmn_ruleset_put(LmnRuleSet* ruleset, LmnRule *rule);


/*----------------------------------------------------------------------
 * System RUleset
 */
void init_system_ruleset(LmnCompiledRuleset *rs);
BOOL compiled_ruleset_react(LmnCompiledRuleset *rs, LmnMembrane *mem);

typedef BOOL (*compiled_rule)(LmnMembrane *);

#endif /* LMN_RULE_H */
