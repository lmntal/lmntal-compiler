/*
 * rule.h
 */

#ifndef LMN_RULE_H
#define LMN_RULE_H

#include "lmntal.h"

struct LmnRule {
  lmn_rule_instr    instr;
  lmn_interned_str name;
};

typedef unsigned short lmn_ruleset_size_t;

struct LmnRuleSet {
  lmn_ruleset_size_t num, cap;
  LmnRule **rules;
};

#endif /* LMN_RULE_H */
