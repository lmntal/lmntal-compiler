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
  LmnMembrane 	   *parent;
  LmnMembrane 	   *child_head;
  LmnMembrane 	   *prev,
                   *next;
  struct SimpleHashtbl atomset;
  RuleSetList 	   *rulesets;
  lmn_interned_str name;
};

typedef struct AtomSetEntry {
  LmnAtomPtr tail, head;
} AtomSetEntry;

struct RuleSetList {
  LmnRuleSet *ruleset;
  RuleSetList *next;
};
typedef struct RuleSetList RuleSetNode;

LMN_EXTERN LmnMembrane *lmn_mem_make(void);
LMN_EXTERN void lmn_mem_free(LmnMembrane *mem);
LMN_EXTERN void lmn_mem_push_mem(LmnMembrane *parentmem, LmnMembrane *newmem);
LMN_EXTERN void lmn_mem_push_atom(LmnMembrane *mem, LmnAtomPtr ap);

LMN_EXTERN void lmn_mem_add_ruleset(LmnMembrane *mem, LmnRuleSet *ruleset);
LMN_EXTERN void lmn_mem_dump(LmnMembrane *mem);
LMN_EXTERN unsigned int lmn_mem_natoms(LmnMembrane *mem);
LMN_EXTERN AtomSetEntry* lmn_mem_get_atomlist(LmnMembrane *mem, LmnFunctor f);
LMN_EXTERN void lmn_mem_remove_atom(LmnMembrane *mem, LmnAtomPtr atom);
LMN_EXTERN unsigned int lmn_mem_nmems(LmnMembrane *mem);
/* LmnAtomPtr* lmn_atomset_end(AtomSetEntry * ent); */
#define lmn_atomset_end(p_atomset_entry) ((LmnAtomPtr)p_atomset_entry)

#endif /* LMN_MEMBRANE_H */
