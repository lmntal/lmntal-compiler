/*
 * membrane.h
 */

#ifndef LMN_MEMBRANE_H
#define LMN_MEMBRANE_H

#include "lmntal.h"

typedef struct RuleSetList RuleSetList;
typedef struct AtomSet     AtomSet;

struct LmnMembrane {
  LmnMembrane 	   *parent;
  LmnMembrane 	   *child_head;
  LmnMembrane 	   *prev,
                   *next;
  AtomSet     	   *atomset;
  RuleSetList 	   *rulesets;
  lmn_interned_str  name;
};

typedef struct AtomSetEntry {
  LmnAtomPtr head, tail;
} AtomSetEntry;

struct AtomSet {
  unsigned int size;
  AtomSetEntry atoms[1<<LMN_FUNCTOR_BITS];
};

#endif /* LMN_MEMBRANE_H */
