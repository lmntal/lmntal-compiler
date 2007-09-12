/*
 * alloc.c -- memory management
 */

#include "memory_pool.h"
#include "lmntal.h"
#include <malloc.h>

/*----------------------------------------------------------------------
 * memory allocation for atom
 */

/* TODO stub implementation */

static memory_pool *atom_memory_pools[128];

LmnAtomPtr lmn_new_atom(LmnFunctor f)
{
  LmnAtomPtr ap;
  int arity = LMN_FUNCTOR_ARITY(f);
  
  if(atom_memory_pools[arity] == 0){
    atom_memory_pools[arity] = memory_pool_new(sizeof(LmnWord)*LMN_ATOM_WORDS(arity));
  }
  ap = (LmnAtomPtr)memory_pool_malloc(atom_memory_pools[arity]);
  LMN_ATOM_SET_FUNCTOR(ap, f);
  return ap;
}

void lmn_delete_atom(LmnAtomPtr ap)
{
  memory_pool_free(atom_memory_pools[LMN_FUNCTOR_ARITY(LMN_ATOM_GET_FUNCTOR(ap))], ap);
}

/*----------------------------------------------------------------------
 * memory allocation for membrane
 */

/* in membrane.c */
/* lmn_mem_make / lmn_mem_delete */

/*----------------------------------------------------------------------
 * low level allocation
 */

void *lmn_calloc(size_t num, size_t size)
{
#if HAVE_CALLOC
  void *new = calloc (num, size);
  if (!new) lmn_fatal("Memory exhausted");
#else
  void *new = lmn_malloc(num * size);
#endif

  return new;
}

void *lmn_malloc(size_t num)
{
  void *new = malloc(num);
  if (!new) lmn_fatal("Memory exhausted");

  return new;
}

void *lmn_realloc(void *p, size_t num)
{
  void *new;

  if (!p) return lmn_malloc (num);
  new = realloc (p, num);
  if (!new) lmn_fatal("Memory exhausted");

  return new;
}

void lmn_free(void *p)
{
  free(p);
}
