/*
 * system_ruleset.c - System Ruleset
 */

#include "system_ruleset.h"
#include "lmntal.h"
#include "membrane.h"
#include "task.h"

BOOL delete_redundant_outproxies(LmnMembrane *mem)
{
  AtomSetEntry *ent = (AtomSetEntry *)hashtbl_get_default(&mem->atomset,
                                                          LMN_OUT_PROXY_FUNCTOR,
                                                          0);
  LmnAtomPtr o0;
  
  if (!ent) return FALSE;
  
  for (o0 = atomlist_head(ent);
       o0 != lmn_atomset_end(ent);
       o0 = LMN_ATOM_GET_NEXT(o0)) {
    LmnAtomPtr o1;
    if (LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(o0, 1))) return FALSE;
     o1 = LMN_ATOM(LMN_ATOM_GET_LINK(o0, 1));
    if (LMN_ATOM_GET_FUNCTOR(o1) == LMN_OUT_PROXY_FUNCTOR) {
      LmnAtomPtr i0 = LMN_ATOM(LMN_ATOM_GET_LINK(o0, 0));
      LmnAtomPtr i1 = LMN_ATOM(LMN_ATOM_GET_LINK(o1, 0));
      LmnMembrane *m0 = LMN_PROXY_GET_MEM(i0);
      LmnMembrane *m1 = LMN_PROXY_GET_MEM(i1);
      if (m0 == m1) {
        lmn_mem_remove_atom(mem, o0);
        lmn_mem_remove_atom(mem, o1);
        lmn_delete_atom(o0);
        lmn_delete_atom(o1);
        lmn_mem_unify_atom_args(m0, i0, 1, i1, 1);
        lmn_mem_remove_atom(m0, i0);
        lmn_mem_remove_atom(m1, i1);
        memstack_push(m0);
        return TRUE;
      }
    }
  }
  return FALSE;
}

BOOL delete_redundant_inproxies(LmnMembrane *mem)
{
  AtomSetEntry *ent = (AtomSetEntry *)hashtbl_get_default(&mem->atomset,
                                                          LMN_OUT_PROXY_FUNCTOR,
                                                          0);
  LmnAtomPtr o0;
  
  if (!ent) return FALSE;
  
  for (o0 = atomlist_head(ent);
       o0 != lmn_atomset_end(ent);
       o0 = LMN_ATOM_GET_NEXT(o0)) {
    LmnAtomPtr i0 = LMN_ATOM(LMN_ATOM_GET_LINK(o0, 0));
    LmnAtomPtr i1;
    if (LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(i0, 1))) return FALSE;
    i1 =LMN_ATOM( LMN_ATOM_GET_LINK(i0, 1));
    if (LMN_ATOM_GET_FUNCTOR(i1) == LMN_IN_PROXY_FUNCTOR) {
      LmnAtomPtr o1 = LMN_ATOM(LMN_ATOM_GET_LINK(i1, 0));
      LmnMembrane *min = LMN_PROXY_GET_MEM(i0);
      lmn_mem_remove_atom(mem, o0);
      lmn_mem_remove_atom(mem, o1);
      lmn_delete_atom(o0);
      lmn_delete_atom(o1);
      lmn_mem_unify_atom_args(mem, o0, 1, o1, 1);
      lmn_mem_remove_atom(min, i0);
      lmn_mem_remove_atom(min, i1);
      return TRUE;
    }
  }
  return FALSE;
}

