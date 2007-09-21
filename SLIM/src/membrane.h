/*
 * membrane.h
 */

#ifndef LMN_MEMBRANE_H
#define LMN_MEMBRANE_H

#include "lmntal.h"
#include "internal_hash.h"

struct SimpleHashtbl atomset;

typedef struct RuleSetList RuleSetList;

struct LmnMembrane {
struct SimpleHashtbl atomset;
  LmnMembrane 	   *parent;
  LmnMembrane 	   *child_head;
  LmnMembrane 	   *prev,
                   *next;
/*   struct SimpleHashtbl atomset; */
  RuleSetList 	   *rulesets;
  lmn_interned_str name;
};

typedef struct AtomSetEntry {
  LmnAtomPtr head, tail;
} AtomSetEntry;


#endif /* LMN_MEMBRANE_H */
