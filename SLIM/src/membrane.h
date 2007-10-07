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
LMN_EXTERN void lmn_mem_drop(LmnMembrane *mem);
LMN_EXTERN void lmn_mem_add_child_mem(LmnMembrane *parentmem, LmnMembrane *newmem);
LMN_EXTERN LmnAtomPtr lmn_mem_newatom(LmnMembrane *mem, LmnFunctor f);
LMN_EXTERN void lmn_mem_push_atom(LmnMembrane *mem, LmnWord atom, LmnLinkAttr attr);
LMN_EXTERN void lmn_mem_add_ruleset(LmnMembrane *mem, LmnRuleSet *ruleset);
LMN_EXTERN BOOL lmn_mem_natoms(LmnMembrane *mem, unsigned int count);
LMN_EXTERN AtomSetEntry* lmn_mem_get_atomlist(LmnMembrane *mem, LmnFunctor f);
LMN_EXTERN void lmn_mem_remove_atom(LmnMembrane *mem, LmnWord atom, LmnLinkAttr attr);

/* アトムをアトムリストから削除する.
   リストのつなぎ変えだけを行う */
#define REMOVE_FROM_ATOMLIST(atom)                 \
  do { \
    LMN_ATOM_SET_PREV(LMN_ATOM_GET_NEXT(atom), LMN_ATOM_GET_PREV(atom)); \
    LMN_ATOM_SET_NEXT(LMN_ATOM_GET_PREV(atom), LMN_ATOM_GET_NEXT(atom)); \
  } while (0)

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
                             LmnAtomPtr atom1,
                             int pos1,
                             LmnAtomPtr atom2,
                             int pos2);
LMN_EXTERN void lmn_mem_unify_symbol_atom_args(LmnAtomPtr atom1,
                                               int pos1,
                                               LmnAtomPtr atom2,
                                               int pos2);
LMN_EXTERN void lmn_mem_relink_atom_args(LmnMembrane *mem,
                                         LmnWord atom0,
                                         LmnLinkAttr attr0,
                                         int pos0,
                                         LmnWord atom1,
                                         LmnLinkAttr attr1,
                                         int pos1);
LMN_EXTERN void lmn_mem_move_cells(LmnMembrane *destmem, LmnMembrane *srcmem);
LMN_EXTERN SimpleHashtbl *lmn_mem_copy_cells(LmnMembrane *dest, LmnMembrane *srcmem);
LMN_EXTERN void lmn_mem_remove_proxies(LmnMembrane *mem);
LMN_EXTERN void lmn_mem_insert_proxies(LmnMembrane *mem, LmnMembrane *child_mem);
LMN_EXTERN void lmn_mem_remove_temporary_proxies(LmnMembrane *mem);
LMN_EXTERN void lmn_mem_remove_toplevel_proxies(LmnMembrane *mem);
/* LmnAtomPtr* lmn_atomset_end(AtomSetEntry * ent); */
/* TODO: rename to atomlist_end */
#define lmn_atomset_end(p_atomset_entry) ((LmnAtomPtr)p_atomset_entry)


/* Utility */
/* REFACTOR: このファイルにあるのはふさわしくないので移動する */
LMN_EXTERN void lmn_newlink_in_symbols(LmnAtomPtr atom0,
                                       int pos0,
                                       LmnAtomPtr atom1,
                                       int pos1);
LMN_EXTERN void lmn_free_atom(LmnWord atom, LmnLinkAttr attr);
LMN_EXTERN LmnWord lmn_copy_atom(LmnWord atom, LmnLinkAttr attr);
LMN_EXTERN LmnWord lmn_copy_data_atom(LmnWord atom, LmnLinkAttr);
LMN_EXTERN BOOL lmn_eq_func(LmnWord atom0, LmnLinkAttr attr0, LmnWord atom1, LmnLinkAttr attr1);
#endif /* LMN_MEMBRANE_H */
