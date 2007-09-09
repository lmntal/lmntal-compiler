/*
 * lmntal.h - global header file
 */

#ifndef LMNTAL_H
#define LMNTAL_H

#include "config.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#if defined(HAVE_INTTYPES_H)
# include <inttypes.h>
#endif

#define LMN_EXTERN extern

/* Some useful macros */
#ifndef FALSE
#define FALSE 0
#endif
#ifndef TRUE
#define TRUE (!FALSE)
#endif

/* This defines several auxiliary routines that are useful for debugging */
#ifndef LMN_DEBUG_HELPER
#define LMN_DEBUG_HELPER      TRUE
#endif

#ifndef LMN_DECL_BEGIN
#ifdef __cplusplus
# define LMN_DECL_BEGIN  extern "C" {
# define LMN_DECL_END    }
#else 
# define LMN_DECL_BEGIN
# define LMN_DECL_END
#endif
#endif /*!defined(LMN_DECL_BEGIN)*/

LMN_DECL_BEGIN

/*----------------------------------------------------------------------
 * data types
 */

#if SIZEOF_LONG != SIZEOF_VOIDP
#error sizeof(long) != sizeof(void*)
#endif

typedef unsigned long LmnWord;
typedef unsigned char BYTE;

#define LMN_WORD_BYTES  SIZEOF_LONG
#define LMN_WORD_BITS   (SIZEOF_LONG*8)
#define LMN_WORD(X)     ((LmnWord)(X))

typedef uint16_t LmnFunctor; /* int16_t may be larger than 16 bit */
#define LMN_FUNCTOR_BYTES 2
#define LMN_FUNCTOR_BITS (LMN_FUNCTOR_BYTES*8)
#define LMN_FUNCTOR(X) ((LmnFunctor)((X)&0xffff))

typedef int lmn_interned_str;

/* てきとー */
typedef BYTE* lmn_rule_instr;

typedef struct LmnRule    LmnRule;
typedef struct LmnRuleSet LmnRuleSet;
typedef struct LmnMembrane LmnMembrane;


/*----------------------------------------------------------------------
 * Atom
 */


/*
 * Atom Structure
 *
 *  * Atom
 *      first Word                        : pointer to next atom
 *      next 2 Bytes                      : Functor
 *      next N Bytes                      : Link Attribute
 *      N Word from next aligned position : Link
 *    (N is the arity of atom)
 *
 *  * Link Attribute
 *     If high 1 bit is 1 then other bits represent 'Link Number'
 *     else other bits represent 'Proxy' or 'Premitive data types'.
 *
 *     [Link Number]
 *       0-------
 *     [in-proxy]
 *       10000000
 *     [out-proxy]
 *       10000001
 *     [otherwise]
 *       We are going to support some premitive data types.
 *
 *       (signed/unsigned) int, short int, long int, byte, long long int
 *       float, double, long double
 *       bool, string, character
 *       ground array, ground with membrane array, premitive arrays
 *
 *       But, incompletely-specified.
 *
 */

typedef LmnWord *LmnAtom;

typedef uint8_t LmnAtomArgAttr;
#define ARG_ATTR_BYTES 1

#define LMN_ATOM(X)               ((LmnAtom)(X))
#define LMN_ATOM_WORD(ATOM, N)    (LMN_ATOM(ATOM)[N])
#define LMN_ATOM_NEXT(ATOM)       ((LmnAtom)LMN_ATOM_WORD(ATOM, 0))
#define LMN_ATOM_FUNCTOR(ATOM)    LMN_FUNCTOR(LMN_ATOM_WORD(ATOM, 1))
#define LMN_ATOM_ARG_ATTR(ATOM,N) \
  (((BYTE*)(((LmnWord*)(ATOM)) + 1)) + LMN_FUNCTOR_BYTES + (N)*ARG_ATTR_BYTES)

#if LMN_WORD_BYTES == 4
#define WORD_SHIFT 2
#elif LMN_WORD_BYTES == 8
#define WORD_SHIFT 3
#else
#error Word size is not 2^N
#endif

#define LMN_ATOM_ARG(ATOM, N) \
  ((LmnWord*)(ATOM) + 1 + ((N - (LMN_WORD_BYTES - LMN_FUNCTOR_BYTES)) >> WORD_SHIFT) + N)

#undef WORD_SHIFT

/*----------------------------------------------------------------------
 * link attribute of premitive data type
 */

/* low 7 bits of link attribute */

#define ATOM_IN_PROXY_ATTR   0
#define ATOM_OUT_PROXY_ATTR  1

#define ATOM_INT_ATTR        2
#define ATOM_DBL_ATTR        3


/*---------------------------------------------------
 * Utility
 */

/*  Memory */

void *lmn_calloc(size_t num, size_t size);
void *lmn_malloc (size_t num);
void *lmn_realloc (void *p, size_t num);
void lmn_free (void *p);

#define LMN_CALLOC(TYPE, NUM)	       ((TYPE *)lmn_calloc ((NUM), sizeof(TYPE)))
#define LMN_MALLOC(TYPE)	           ((TYPE *)lmn_malloc(sizeof(TYPE)))
#define LMN_REALLOC(TYPE, P, NUM)	   ((TYPE *)lmn_realloc((P), (NUM) * sizeof(TYPE)))
#define LMN_FREE(P)				       (lmn_free((void*)(P)))

/* Assertion */

#include <assert.h>
#ifdef DEBUG
#define LMN_ASSERT(expr)   assert(expr)
#else
#define LMN_ASSERT(expr)   ((void)0)/* nothing */
#endif


LMN_DECL_END

#endif /* LMNTAL_H */
