/*
 * rule.h
 */

#ifndef LMN_RULE_H
#define LMN_RULE_H

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


LMN_EXTERN LmnRule *lmn_rule_make(LmnRuleInstr instr, lmn_interned_str name);
LMN_EXTERN void lmn_rule_free(LmnRule *rule);
LMN_EXTERN LmnRuleSet *lmn_ruleset_make(LmnRulesetId id, lmn_ruleset_size_t init_size);
LMN_EXTERN void lmn_ruleset_free(LmnRuleSet *ruleset);
LMN_EXTERN void lmn_ruleset_put(LmnRuleSet* ruleset, LmnRule *rule);

#endif /* LMN_RULE_H */
