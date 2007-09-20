/*
 * lmntal.h - global header file
 */

#ifndef LMNTAL_H
#define LMNTAL_H

#include "config.h"

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#ifdef WITH_DMALLOC
#include <dmalloc.h>
#endif

#if defined(HAVE_INTTYPES_H)
# include <inttypes.h>
#endif

#define LMN_EXTERN extern

/* Some useful macros */
#ifndef BOOL
#define BOOL unsigned char
#endif

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

#if SIZEOF_LONG < SIZEOF_VOIDP
#error sizeof(long) < sizeof(void*)
#endif

typedef unsigned long LmnWord;
typedef unsigned char BYTE;
typedef BYTE LmnByte;

#define LMN_WORD_BYTES  SIZEOF_LONG
#define LMN_WORD_BITS   (SIZEOF_LONG*8)
#define LMN_WORD(X)     ((LmnWord)(X))

/* uint16_t is not defined if there is no 2Byte data type */
typedef uint16_t LmnFunctor; 
#define LMN_FUNCTOR_BYTES (sizeof(LmnFunctor))
#define LMN_FUNCTOR_BITS (LMN_FUNCTOR_BYTES*8)
#define LMN_FUNCTOR(X) ((LmnFunctor)((X)))

/* this type must be enough to represent arity */
typedef uint8_t LmnArity;

typedef unsigned int lmn_interned_str;

typedef BYTE* LmnRuleInstr;
typedef uint16_t LmnInstrOp;
typedef uint16_t LmnInstrVar;
typedef uint16_t LmnJumpOffset;
typedef uint32_t LmnLineNum;
typedef int16_t LmnRulesetId;

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
 *      first Word                        : pointer to previous atom
 *      second Word                       : pointer to next atom
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

typedef LmnWord *LmnAtomPtr;

typedef uint8_t LmnLinkAttr;
#define LMN_ATOM_LINK_ATTR(X)   ((LmnLinkAttr)(X))
#define LMN_LINK_ATTR_BYTES     (sizeof(LmnLinkAttr))
#define LMN_LINK_ATTR_MASK      (0x7f)

#if LMN_WORD_BYTES == 4
#define LMN_WORD_SHIFT 2
#elif LMN_WORD_BYTES == 8
#define LMN_WORD_SHIFT 3
#else
#error Word size is not 2^N
#endif

#define LMN_ATTR_WORDS(ARITY)  \
  (((ARITY)+(LMN_FUNCTOR_BYTES - 1))>>LMN_WORD_SHIFT)

#define LMN_ATOM(X)                 ((LmnAtomPtr)(X))

#define LMN_ATOM_PPREV(ATOM)        ((LmnWord*)(ATOM))
#define LMN_ATOM_PNEXT(ATOM)        (((LmnWord*)(ATOM))+1)
#define LMN_ATOM_PLINK_ATTR(ATOM,N)                       \
  ((LmnLinkAttr*)(((BYTE*)(((LmnWord*)(ATOM))+2))+    \
              LMN_FUNCTOR_BYTES+(N)*LMN_LINK_ATTR_BYTES))
#define LMN_ATOM_PLINK(ATOM,N)                            \
  (((LmnWord*)(ATOM))+3+LMN_ATTR_WORDS(LMN_ATOM_GET_ARITY(ATOM))+(N))

/* get/set prev atom of ATOM */
#define LMN_ATOM_GET_PREV(ATOM)           \
  ((LmnAtomPtr)*LMN_ATOM_PPREV(ATOM))
#define LMN_ATOM_SET_PREV(ATOM,X)         \
  (*LMN_ATOM_PPREV(ATOM)=(LmnWord)(X))
/* get/set next atom of ATOM */
#define LMN_ATOM_GET_NEXT(ATOM)           \
  ((LmnAtomPtr)*LMN_ATOM_PNEXT(ATOM))
#define LMN_ATOM_SET_NEXT(ATOM,X)         \
  (*LMN_ATOM_PNEXT(ATOM)=(LmnWord)(X))
/* get/set ATOM functor */
#define LMN_ATOM_GET_FUNCTOR(ATOM)        \
  LMN_FUNCTOR(*(((LmnWord*)(ATOM))+2))
#define LMN_ATOM_SET_FUNCTOR(ATOM,X)      \
  (*((LmnFunctor*)((LmnWord*)(ATOM)+2))=(X))
#define LMN_ATOM_GET_ARITY(ATOM)          (LMN_FUNCTOR_ARITY(LMN_ATOM_GET_FUNCTOR(ATOM)))
/* get/set N th link attribute of  ATOM */
#define LMN_ATOM_GET_LINK_ATTR(ATOM,N)    \
  (*LMN_ATOM_PLINK_ATTR(ATOM,N))
#define LMN_ATOM_SET_LINK_ATTR(ATOM,N,X)  \
  ((*LMN_ATOM_PLINK_ATTR(ATOM,N))=(X))
/* get/set N th link of ATOM */ 
#define LMN_ATOM_GET_LINK(ATOM, N)        \
  (*LMN_ATOM_PLINK(ATOM,N))
#define LMN_ATOM_SET_LINK(ATOM,N,X)       \
  (*LMN_ATOM_PLINK(ATOM,N)=(X))

/* word size of atom */
#define LMN_ATOM_WORDS(ARITY)         (3+LMN_ATTR_WORDS(ARITY)+(ARITY))

/* operations for link attribute */
#define LMN_ATTR_IS_DATA(X)           ((X)&~LMN_LINK_ATTR_MASK)
#define LMN_ATTR_IS_PROXY(X)                            \
  (LMN_ATTR_IS_DATA(X) &&                               \
   (LMN_ATTR_GET_VALUE(X) == LMN_ATOM_IN_PROXY_ATTR ||	\
    LMN_ATTR_GET_VALUE(X) == LMN_ATOM_OUT_PROXY_ATTR))
/* set data/link tag to link attribute value */
#define LMN_ATTR_MAKE_DATA(X)         (0x80|(X))
#define LMN_ATTR_MAKE_LINK(X)         (X)
/* get link attribute value (remove tag) */
#define LMN_ATTR_GET_VALUE(X)         ((X)&LMN_LINK_ATTR_MASK)
/* set link attribute value. Tag is not changed. */
#define LMN_ATTR_SET_VALUE(PATTR,X)   \
  (*(PATTR)=((((X)&~LMN_LINK_ATTR_MASK))|X))
/*----------------------------------------------------------------------
 * link attribute of premitive data type
 */

/* low 7 bits of link attribute */

#define LMN_ATOM_IN_PROXY_ATTR   0
#define LMN_ATOM_OUT_PROXY_ATTR  1

#define LMN_ATOM_INT_ATTR        2
#define LMN_ATOM_DBL_ATTR        3

/*----------------------------------------------------------------------
 * Membrane
 */

#include "membrane.h"

LMN_EXTERN LmnMembrane *lmn_mem_make(void);
LMN_EXTERN void lmn_mem_free(LmnMembrane *mem);
LMN_EXTERN void lmn_mem_push_mem(LmnMembrane *parentmem, LmnMembrane *newmem);
LMN_EXTERN void lmn_mem_push_atom(LmnMembrane *mem, LmnAtomPtr ap);
LMN_EXTERN LmnAtomPtr lmn_mem_pop_atom(LmnMembrane *mem, LmnFunctor f);

LMN_EXTERN void lmn_mem_add_ruleset(LmnMembrane *mem, LmnRuleSet *ruleset);
LMN_EXTERN void lmn_mem_dump(LmnMembrane *mem);
LMN_EXTERN unsigned int lmn_mem_natoms(LmnMembrane *mem);
LMN_EXTERN AtomSetEntry *lmn_mem_get_atomlist(LmnMembrane *mem, LmnFunctor f);
LMN_EXTERN void lmn_mem_remove_atom(LmnMembrane *mem, LmnAtomPtr atom);
  
/*----------------------------------------------------------------------
 * Rule
 */

#include "rule.h"

/*----------------------------------------------------------------------
 * Atom
 */

LMN_EXTERN LmnAtomPtr lmn_new_atom(LmnFunctor arity);
LMN_EXTERN void lmn_delete_atom(LmnAtomPtr ap);

/*----------------------------------------------------------------------
 * Execution
 */

LMN_EXTERN void run(void);

/*----------------------------------------------------------------------
 * Utility
 */

/*  Memory */

LMN_EXTERN void *lmn_calloc(size_t num, size_t size);
LMN_EXTERN void *lmn_malloc(size_t num);
LMN_EXTERN void *lmn_realloc(void *p, size_t num);
LMN_EXTERN void lmn_free (void *p);

#define LMN_NALLOC(TYPE, NUM)          ((TYPE *)lmn_malloc(sizeof(TYPE)*(NUM)))
#define LMN_CALLOC(TYPE, NUM)          ((TYPE *)lmn_calloc((NUM), sizeof(TYPE)))
#define LMN_MALLOC(TYPE)               ((TYPE *)lmn_malloc(sizeof(TYPE)))
#define LMN_REALLOC(TYPE, P, NUM)      ((TYPE *)lmn_realloc((P), (NUM) * sizeof(TYPE)))
#define LMN_FREE(P)                    (lmn_free((void*)(P)))

/* Error */
LMN_EXTERN void lmn_fatal(const char *msg, ...);

/* Assertion */

#include <assert.h>
#ifdef DEBUG
#define LMN_ASSERT(expr)   assert(expr)
#else
#define LMN_ASSERT(expr)   ((void)0)/* nothing */
#endif

/*----------------------------------------------------------------------
 * Global data
 */

/* load input program file */
void read_il(const char *file_name);

/* Functor Information */

typedef struct LmnFunctorEntry {
  lmn_interned_str  module;
  lmn_interned_str  name;
  LmnArity          arity;
} LmnFunctorEntry;

typedef struct LmnFunctorTable {
  unsigned int size;
  struct LmnFunctorEntry *entry;
} LmnFunctorTable;

extern struct LmnFunctorTable lmn_functor_table;

#define LMN_FUNCTOR_ARITY(F)    (lmn_functor_table.entry[(F)].arity)
#define LMN_FUNCTOR_NAME_ID(F)     (lmn_functor_table.entry[(F)].name)

/* Symbol Information */

typedef struct LmnSymbolTable {
  unsigned int size;
  char **entry;
} LmnSymbolTable;

extern struct LmnSymbolTable lmn_symbol_table;

#define LMN_SYMBOL_STR(ID)       (lmn_symbol_table.entry[(ID)])

/* RuleSet Information */

typedef struct LmnRuleSetTable {
  unsigned int size;
  LmnRuleSet **entry;
} LmnRuleSetTable;			 

extern struct LmnRuleSetTable lmn_ruleset_table;

#define LMN_RULESET_ID(ID)      lmn_ruleset_table.entry[(ID)]

/* Runtime Environment */

struct LmnEnv {
  char    *symbol_file;
};

extern struct LmnEnv  lmn_env;

/*----------------------------------------------------------------------
 * test
 */

#ifdef DEBUG
void test_mem(void);

#endif

LMN_DECL_END

#endif /* LMNTAL_H */
