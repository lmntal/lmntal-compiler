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

#if LMN_WORD_BYTES == 4
#define LMN_WORD_SHIFT 2
#elif LMN_WORD_BYTES == 8
#define LMN_WORD_SHIFT 3
#else
#error Word size is not 2^N
#endif

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
void unread_il(void);

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

#define NULL_STRING_ID 0
#define LMN_SYMBOL_STR(ID)       (lmn_symbol_table.entry[(ID)])

/* RuleSet Information */

typedef struct LmnRuleSetTable {
  unsigned int size;
  LmnRuleSet **entry;
} LmnRuleSetTable;			 

extern struct LmnRuleSetTable lmn_ruleset_table;

#define LMN_RULESET_ID(ID)      lmn_ruleset_table.entry[(ID)]

/* Runtime Environment */

enum OutputFormat { DEFAULT, DOT };

struct LmnEnv {
  BOOL dev_dump;
  BOOL show_proxy;
  BOOL show_ruleset;
  enum OutputFormat output_format;
};

extern struct LmnEnv  lmn_env;

LMN_DECL_END

#endif /* LMNTAL_H */
