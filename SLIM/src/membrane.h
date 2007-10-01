/*
 * membrane.h
 */

#ifndef LMN_MEMBRANE_H
#define LMN_MEMBRANE_H

#include "lmntal.h"
#include "internal_hash.h"
#include "vector.h"

struct SimpleHashtbl atomset;

typedef struct RuleSetList RuleSetList;

struct LmnMembrane {
  LmnMembrane 	   *parent;
  LmnMembrane 	   *child_head;
  LmnMembrane 	   *prev,
                   *next;
  struct SimpleHashtbl atomset;
  unsigned int     atom_num; /* # of atom except proxy */
  struct Vector	   rulesets;
  lmn_interned_str name;
};

/* TODO: 名前をAtomListに変更 */
/* この構造体をAtomとして扱うことで,この構造体自身が
   HeadとTailの両方の役目を果たしている */
typedef struct AtomSetEntry {
  LmnWord tail, head;
} AtomSetEntry;

#define atomlist_head(LIST)    (LMN_ATOM((LIST)->head))

LMN_EXTERN LmnMembrane *lmn_mem_make(void);
LMN_EXTERN void lmn_mem_free(LmnMembrane *mem);
LMN_EXTERN void lmn_mem_add_child_mem(LmnMembrane *parentmem, LmnMembrane *newmem);
LMN_EXTERN void lmn_mem_push_atom(LmnMembrane *mem, LmnAtomPtr ap);

LMN_EXTERN void lmn_mem_add_ruleset(LmnMembrane *mem, LmnRuleSet *ruleset);
LMN_EXTERN BOOL lmn_mem_natoms(LmnMembrane *mem, unsigned int count);
LMN_EXTERN AtomSetEntry* lmn_mem_get_atomlist(LmnMembrane *mem, LmnFunctor f);
LMN_EXTERN void lmn_mem_remove_atom(LmnMembrane *mem, LmnAtomPtr atom);
LMN_EXTERN BOOL lmn_mem_nmems(LmnMembrane *mem, unsigned int count);
LMN_EXTERN BOOL lmn_mem_nfreelinks(LmnMembrane *mem, unsigned int count);
LMN_EXTERN void lmn_mem_movecells(LmnMembrane *destmem, LmnMembrane *srcmem);
LMN_EXTERN void lmn_mem_newlink(LmnMembrane *mem,
                                LmnWord atom0,
                                LmnLinkAttr attr0,
                                int pos0,
                                LmnWord atom1,
                                LmnLinkAttr attr1,
                                int pos1);
LMN_EXTERN void lmn_mem_link_data_atoms(LmnMembrane *mem,
                                        LmnWord d1,
                                        LmnLinkAttr attr1,
                                        LmnWord d2,
                                        LmnLinkAttr attr2);
LMN_EXTERN void lmn_mem_unify_atom_args(LmnMembrane *mem,
                                        LmnWord atom1,
                                        int pos1,
                                        LmnWord  atom2,
                                        int pos2);
LMN_EXTERN void lmn_mem_relink_atom_args(LmnMembrane *mem,
                                         LmnWord atom0,
                                         LmnLinkAttr attr0,
                                         int pos0,
                                         LmnWord atom1,
                                         LmnLinkAttr attr1,
                                         int pos1);
LMN_EXTERN void lmn_mem_move_cells(LmnMembrane *destmem, LmnMembrane *srcmem);
LMN_EXTERN void lmn_mem_remove_proxies(LmnMembrane *mem);
LMN_EXTERN void lmn_mem_insert_proxies(LmnMembrane *mem, LmnMembrane *child_mem);
/* LmnAtomPtr* lmn_atomset_end(AtomSetEntry * ent); */
#define lmn_atomset_end(p_atomset_entry) ((LmnAtomPtr)p_atomset_entry)

#endif /* LMN_MEMBRANE_H */
