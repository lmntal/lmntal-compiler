/*
 * alloc.c -- memory management
 */

#include "lmntal.h"
#include <malloc.h>

/*----------------------------------------------------------------------
 * memory allocation for atom
 */

/* TODO stub implementation */

LmnAtomPtr lmn_new_atom(LmnFunctor f)
{
  LmnAtomPtr ap;
  ap = LMN_NALLOC(LmnWord, LMN_ATOM_WORDS(LMN_FUNCTOR_ARITY(f)));
  LMN_ATOM_SET_FUNCTOR(ap, f);
  return ap;
}

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
