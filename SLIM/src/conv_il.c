/* conv_il.c
 *
 * - unimplemented feature
 *   branch
 */

#include <limits.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <unistd.h>
#include <getopt.h>
#include "lmntal.h"
#include "instruction.h"

#define RULESET    "Compiled Ruleset @"
#define RULE       "Compiled Rule "
#define INLINE     "Inline"
#define ATOMMATCH  "--atommatch:"
#define MEMMATCH   "--memmatch:"
#define GUARD      "--guard:"
#define BODY       "--body:"

typedef int INT_T;
typedef unsigned int ID_T;
typedef int16_t RuleNumType;
typedef int16_t RuleSize;
#define REF_CAST(T,X)     (*(T*)&(X))
#define SWAP(T,X,Y)       do { T t=(X); (X)=(Y); (Y)=t;} while(0)
#define ARY_SIZE(X) (sizeof(X)/sizeof(X[0]))
#define SEQ(A,S) (!strncmp((A),(S),strlen(S)))

BOOL dump = FALSE;

/*
 *  Binary Format
 *    - symbol table
 *    - functor table
 *    - ruleset
 *
 *  * symbol table
 *      lmn_interned_str : # of symbols (N)
 *      symbol * N
 *  * symbol
 *      lmn_interned_str : symbol ID
 *      uint16_t         : symbol string length (N)
 *      char * N         : characters
 *
 *  * functor table
 *      LmnFunctor       : # of functors (N)
 *      functor * N
 *  * functor
 *      LmnFunctor       : functor ID
 *      lmn_interned_str : module ID
 *      lmn_interned_str : symbol ID
 *      LmnArity         : arity
 *
 *  * instruction table
 *      uint32_t         : # of ruleset (N)
 *      ruleset * N
 *  * ruleset
 *      uint16_t         : ruleset ID
 *      uint16_t         : # of rules (N)
 *      rule * N
 *  * rule
 *      uint16_t         : size of byte
 *      instructions
 *
 *  * instructions
 *     LmnInstrOp        : instruction ID
 *     Arguments
 *
 *  * Argument
 *   ** functor
 *       BYTE : data tag (see atom description in lmntal.h)
 *       data according to tag
 *   ** InstrVar
 *       LmnInstrVar      : integer value
 *   ** InstrVarList
 *     int16_t           : # of elements (N)
 *     LmnInstrVar * N
 * 
 */

/*----------------------------------------------------------------------
 * Language Specification
 */

enum ArgType {
  InstrVar = 1,
  Label,
  InstrVarList,
  String,
  LineNum,
  Functor,
  ArgRuleset,
  InstrList
};

struct InstrSpec {
  char op_str[128];
  LmnInstrOp op;
  enum ArgType args[128];
} spec[] = {

    {"deref", INSTR_DEREF, {InstrVar, InstrVar, InstrVar, InstrVar, 0}},
    {"derefatom", INSTR_DEREFATOM, {InstrVar, InstrVar, InstrVar, 0}},
    {"dereflink", INSTR_DEREFLINK, {InstrVar, InstrVar, InstrVar, 0}},
    {"findatom", INSTR_FINDATOM, {InstrVar, InstrVar, Functor, 0}},

    /* lockmem unused */
    {"lockmem", INSTR_LOCKMEM, {InstrVar, InstrVar, String, 0}},
    {"anymem", INSTR_ANYMEM, {InstrVar, InstrVar, InstrVar, String, 0}},
    /* lock unused */
    {"getmem", INSTR_GETMEM, {InstrVar, InstrVar, InstrVar, String, 0}},
    {"getparent", INSTR_GETPARENT, {InstrVar, InstrVar, 0}},

    {"testmem", INSTR_TESTMEM, {InstrVar, InstrVar, 0}},
    {"norules", INSTR_NORULES, {InstrVar, 0}},
    {"nfreelinks", INSTR_NFREELINKS, {InstrVar, InstrVar, 0}},
    {"natoms", INSTR_NATOMS, {InstrVar, InstrVar, 0}},
    {"natomsindirect", INSTR_NATOMSINDIRECT, {InstrVar, InstrVar, 0}},
    {"nmems", INSTR_NMEMS, {InstrVar, InstrVar, 0}},
    {"eqmem", INSTR_EQMEM, {InstrVar, InstrVar, 0}},
    {"neqmem", INSTR_NEQMEM, {InstrVar, InstrVar, 0}},
    {"stable", INSTR_STABLE, {InstrVar, 0}},

    {"func", INSTR_FUNC, {InstrVar, Functor, 0}},
    {"notfunc", INSTR_NOTFUNC, {InstrVar, Functor, 0}},
    {"eqatom", INSTR_EQATOM, {InstrVar, InstrVar, 0}},
    {"neqatom", INSTR_NEQATOM, {InstrVar, InstrVar, 0}},
    {"samefunc", INSTR_SAMEFUNC, {InstrVar, InstrVar, 0}},
    
    {"dereffunc", INSTR_DEREFFUNC, {InstrVar, InstrVar, InstrVar, 0}},
    {"getfunc", INSTR_GETFUNC, {InstrVar, InstrVar, 0}},
    {"loadfunc", INSTR_LOADFUNC, {InstrVar, Functor, 0}},
    {"eqfunc", INSTR_EQFUNC, {InstrVar, InstrVar, 0}},
    {"neqfunc", INSTR_NEQFUNC, {InstrVar, InstrVar, 0}},
    
    {"removeatom", INSTR_REMOVEATOM, {InstrVar, InstrVar, Functor, 0}},
    {"newatom", INSTR_NEWATOM, {InstrVar, InstrVar, Functor, 0}},
    {"newatomindirect", INSTR_NEWATOMINDIRECT, {InstrVar, InstrVar, InstrVar, 0}},
    {"enqueueatom", INSTR_ENQUEUEATOM, {InstrVar, 0}},
    {"dequeueatom", INSTR_DEQUEUEATOM, {InstrVar, 0}},
    {"freeatom", INSTR_FREEATOM, {InstrVar, 0}},
    {"alterfunc", INSTR_ALTERFUNC, {InstrVar, Functor, 0}},

    {"allocatom", INSTR_ALLOCATOM, {InstrVar, Functor, 0}},
    {"allocatomindirect", INSTR_ALLOCATOMINDIRECT, {InstrVar, InstrVar, 0}},
    {"copyatom", INSTR_COPYATOM, {InstrVar, InstrVar, InstrVar, 0}},
    {"addatom", INSTR_ADDATOM, {InstrVar, InstrVar, 0}},
    
    {"removemem", INSTR_REMOVEMEM, {InstrVar, InstrVar, 0}},
    {"newmem", INSTR_NEWMEM, {InstrVar, InstrVar, InstrVar, 0}},
    {"allocmeme", INSTR_ALLOCMEM, {InstrVar, 0}},
    {"newroot", INSTR_NEWROOT, {InstrVar, InstrVar, InstrVar, InstrVar, 0}},
    {"movecells", INSTR_MOVECELLS, {InstrVar, InstrVar, 0}},
    {"enqueueallatoms", INSTR_ENQUEUEALLATOMS, {InstrVar, 0}},
    {"freemem", INSTR_FREEMEM, {InstrVar, 0}},
    {"addmem", INSTR_ADDMEM, {InstrVar, InstrVar, 0}},
    {"enqueuemem", INSTR_ENQUEUEMEM, {InstrVar, 0}},
    {"unlockmem", INSTR_UNLOCKMEM, {InstrVar, 0}},
    {"setmemname", INSTR_SETMEMNAME, {InstrVar, String, 0}},

    {"getlink", INSTR_GETLINK, {InstrVar, InstrVar, InstrVar, 0}},
    {"alloclink", INSTR_ALLOCLINK, {InstrVar, InstrVar, InstrVar, 0}},

    {"newlink", INSTR_NEWLINK, {InstrVar, InstrVar, InstrVar, InstrVar, InstrVar, 0}},
    {"relink", INSTR_RELINK, {InstrVar, InstrVar, InstrVar, InstrVar, InstrVar, 0}},
    {"unify", INSTR_UNIFY, {InstrVar, InstrVar, InstrVar, InstrVar, InstrVar, 0}},
    {"inheritlink", INSTR_INHERITLINK, {InstrVar, InstrVar, InstrVar, InstrVar, 0}},
    {"unifylinks", INSTR_UNIFYLINKS, {InstrVar, InstrVar, InstrVar, 0}},

    {"removeproxies", INSTR_REMOVEPROXIES, {InstrVar, 0}},
    {"removetoplevelproxies", INSTR_REMOVETOPLEVELPROXIES, {InstrVar, 0}},
    {"insertproxies", INSTR_INSERTPROXIES, {InstrVar, InstrVar, 0}},
    {"removetemporaryproxies", INSTR_REMOVETEMPORARYPROXIES, {InstrVar, 0}},

    {"loadruleset", INSTR_LOADRULESET, {InstrVar, ArgRuleset, 0}},
    {"copyrules", INSTR_COPYRULES, {InstrVar, InstrVar, 0}},
    {"clearules", INSTR_CLEARRULES, {InstrVar, 0}},
    {"loadmodule", INSTR_LOADMODULE, {InstrVar, String, 0}},

    {"recursivelock", INSTR_RECURSIVELOCK, {InstrVar, 0}},
    {"recursiveunlock", INSTR_RECURSIVEUNLOCK, {InstrVar, 0}},
    {"copycells", INSTR_COPYCELLS, {InstrVar, InstrVar, InstrVar, 0}},
    {"dropmem", INSTR_DROPMEM, {InstrVar, 0}},
    {"lookuplink", INSTR_LOOKUPLINK, {InstrVar, InstrVar, InstrVar, 0}},
    {"insertconnectors", INSTR_INSERTCONNECTORS, {InstrVar, InstrVarList, InstrVar, 0}},
    {"insertconnectorsinnull", INSTR_INSERTCONNECTORSINNULL, {InstrVar, InstrVar, 0}},
    {"deleteconnectors", INSTR_DELETECONNECTORS, {InstrVar, InstrVar, 0}},
     
    {"react", INSTR_REACT, {InstrVar, InstrVarList, InstrVarList, InstrVarList, 0}},
    {"jump", INSTR_JUMP, {Label, InstrVarList, InstrVarList, InstrVarList, 0}},
    {"commit", INSTR_COMMIT, {String, LineNum, 0}},
    {"resetvars", INSTR_RESETVARS, {InstrVarList, InstrVarList, InstrVarList,}},
    {"changevars", INSTR_CHANGEVARS, {InstrVarList, InstrVarList, InstrVarList,}},
    {"spec", INSTR_SPEC, {InstrVar, InstrVar,0}},
    {"proceed", INSTR_PROCEED, {0}},

    {"branch", INSTR_BRANCH, {InstrList, 0}},
    {"loop", INSTR_LOOP, {InstrList, 0}},
    {"run", INSTR_RUN, {InstrList, 0}},
    {"not", INSTR_NOT, {InstrList, 0}},

    /* special */
    {"group", INSTR_GROUP, {InstrList, 0}},

    /* guard */
    {"removeground", INSTR_REMOVEGROUND, {InstrVar, InstrVar, 0}},
    {"isground", INSTR_ISGROUND, {InstrVar, InstrVar, InstrVar, 0}},
    {"isunary", INSTR_ISUNARY, {InstrVar, 0}},
    {"isint", INSTR_ISINT, {InstrVar, 0}},
    {"isintfunc", INSTR_ISINTFUNC, {InstrVar, 0}},
    
    {"newlist", INSTR_NEWLIST, {InstrVar, 0}},
    {"addtolist", INSTR_ADDTOLIST, {InstrVar, InstrVar, 0}},

    /* guard: int */
    {"iadd", INSTR_IADD, {InstrVar, InstrVar, InstrVar, 0}},
    {"isub", INSTR_ISUB, {InstrVar, InstrVar, InstrVar, 0}},
    {"imul", INSTR_IMUL, {InstrVar, InstrVar, InstrVar, 0}},
    {"idiv", INSTR_IDIV, {InstrVar, InstrVar, InstrVar, 0}},
    {"ineg", INSTR_INEG, {InstrVar, InstrVar, 0}},
    {"imod", INSTR_IMOD, {InstrVar, InstrVar, InstrVar, 0}},
    {"ilt", INSTR_ILT, {InstrVar, InstrVar, 0}},
    {"ile", INSTR_ILE, {InstrVar, InstrVar, 0}},
    {"igt", INSTR_IGT, {InstrVar, InstrVar, 0}},
    {"ige", INSTR_IGE, {InstrVar, InstrVar, 0}},
    {"ieq", INSTR_IEQ, {InstrVar, InstrVar, 0}},
    {"ine", INSTR_INE, {InstrVar, InstrVar, 0}},
    
    /* for dump */
    {"rewindstack", INSTR_REWINDSTACK, {InstrVar, 0}},
    {"swapvec", INSTR_SWAP_WORK_VEC, {0}},
    {"success", INSTR_SUCCESS, {0}},
  };

enum LmnInstruction backtrack_points[] =
  {INSTR_FINDATOM, INSTR_ANYMEM};

/*----------------------------------------------------------------------
 * extensible vector
 */

/* remove, insertは効率が悪いけどほとんど使わないので問題ない */

#define VEC_NAME(NAME)  vec_##NAME##_t
  
#define VEC_DEF(T,NAME)                         \
  struct VEC_NAME(NAME) {                       \
    unsigned int cap, num;                      \
    T *v;                                       \
  }

#define VEC_T(NAME) struct VEC_NAME(NAME)

#define VEC_INIT(V)                                 \
  do {                                              \
    (V)->num = 0;                                   \
    (V)->cap = 256;                                 \
    (V)->v = malloc(sizeof((V)->v[0]) * (V)->cap);  \
  } while (0)

#define VEC_EXTEND(V)                                           \
  do {                                                          \
    if ((V)->num == (V)->cap) {                                 \
      (V)->cap *= 2;                                            \
      (V)->v = realloc((V)->v, sizeof((V)->v[0]) * (V)->cap);   \
    }                                                           \
  } while (0)
  

#define PUSH(V,X)                               \
  do {                                          \
    VEC_EXTEND(V);                              \
    (V)->v[(V)->num++] = (X);                   \
  } while(0)

#define POP(V,X)                                    \
  do {                                              \
    if ((V)->num == 0) {                            \
      fprintf(stderr, "pop from empty vector\n");   \
    }                                               \
    *X = (V)->v[--(V)->num];                        \
  } while (0) 

#define VEC_REF(V,N) (&(V).v[N])

#define VEC_FREE0(V) free((V)->v)
  
#define VEC_FREE(V,F)                           \
  do {                                          \
    unsigned int i;                             \
    for (i = 0; i < (V)->num; i++) {            \
      (F)((V)->v[i]);                           \
    }                                           \
    free((V)->v);                               \
  } while(0) 

#define VEC_REMOVE(V,I)                                             \
  do {                                                              \
    unsigned int __i;                                               \
    if ((V)->num == 0 || (I) < 0 || (I) >= (V)->num) assert(FALSE);   \
    for (__i = (I); __i < (V)->num - 1; __i++) {                     \
      (V)->v[__i] = (V)->v[__i+1];                                            \
    }                                                               \
    (V)->num--;                                                      \
  } while (0)                                                       \

#define VEC_INSERT(V,I,X)                           \
  do {                                              \
    unsigned int __i;                               \
    VEC_EXTEND(V);                                  \
    if ((I) < 0 || (I) > (V)->num) assert(FALSE);   \
    for (__i = (V)->num; __i > (I); __i--) {        \
      (V)->v[__i] = (V)->v[__i-1];                  \
    }                                               \
    (V)->v[I] = (X);                                \
    (V)->num++;                                     \
  } while (0)

VEC_DEF(int, INT_V);


/*----------------------------------------------------------------------
 * data types
 */

enum FunctorType {SYMBOL, INT, DOUBLE, IN_PROXY, OUT_PROXY};

struct SymbolFunctor {
  lmn_interned_str module_id;
  lmn_interned_str symbol_id;
  LmnFunctor       functor_id;
  LmnArity         arity;
};

struct Functor {
  enum FunctorType type;
  union {
    int int_value;
    double double_value;
    struct SymbolFunctor sym_atom;
  } v;
};

/* Instruction Argument */

struct InstrArg {
  enum ArgType type;
  void *v;
};

/* Instruction */

struct Instruction {
  enum LmnInstruction id;
  unsigned int arg_num;
  struct InstrArg args[10];
};

VEC_DEF(struct Instruction, INSTR_V);

/* Ruleset */

struct RuleEl {
  unsigned int label;
  VEC_T(INSTR_V) instrs;
};

struct Rule {
  ID_T name;
  struct RuleEl amatch;
  struct RuleEl mmatch;
  struct RuleEl guard;
  struct RuleEl body;
};

VEC_DEF(struct Rule, RULE_V);

struct Ruleset {
  ID_T id;
  VEC_T(RULE_V) rules;
};


VEC_DEF(struct Ruleset, RULESET_V);

/* IL
 *  toplevel structure
 */

struct IL {
  VEC_T(RULESET_V) rulesets;
};


/*----------------------------------------------------------------------
 * symbol table
 */

struct SymbolEntry {
  char *str;
  unsigned int id;
};

VEC_DEF(struct SymbolEntry, SYMBOL_ENTRY_V);
VEC_T(SYMBOL_ENTRY_V) symbols;
static unsigned int symbol_id = 0;

static void symbol_entry_free(struct SymbolEntry e)
{
  free(e.str);
}

static lmn_interned_str get_symbol_id(char *str)
{
  unsigned int i;

  for (i = 0; i < symbols.num; i++) {
    if (!strcmp(symbols.v[i].str, str)) return symbols.v[i].id;
  }
  {
    struct SymbolEntry s;
    s.str = malloc(sizeof(char) * strlen(str)+1);
    strcpy(s.str, str);
    s.id = symbol_id++;
    PUSH(&symbols, s);
    return s.id;
  }
}

/*----------------------------------------------------------------------
 * functor table
 */

VEC_DEF(struct SymbolFunctor, FUNCTOR_ENTRY_V);
VEC_T(FUNCTOR_ENTRY_V) functors;
unsigned int func_id; /* initで設定 */


static LmnFunctor get_functor_id(struct Functor f)
{
  unsigned int i;

  /* TODO 効率的な実装が必要 */
  for (i = 0; i < functors.num; i++) {
    struct SymbolFunctor e = *VEC_REF(functors, i);
    if (e.module_id == f.v.sym_atom.module_id &&
        e.symbol_id == f.v.sym_atom.symbol_id &&
        e.arity == f.v.sym_atom.arity) {
      return e.functor_id;
    }
  }

  /* if no entry */
  {
    struct SymbolFunctor e;
    e.module_id = f.v.sym_atom.module_id;
    e.symbol_id = f.v.sym_atom.symbol_id;
    e.functor_id = func_id++;
    e.arity = f.v.sym_atom.arity;
    PUSH(&functors, e);

    return e.functor_id;
  }
}

/*----------------------------------------------------------------------
 * parse
 */

#define ASSERT(X) do {                                    \
    if (!X) {                                               \
      fprintf(stderr, "line = %d\n", cur_line);    \
      assert(X);                                            \
    }                                                       \
  } while (0)

/* input lines */
VEC_DEF(char*, PCHAR_V);
static VEC_T(PCHAR_V) src_lines;
static unsigned int cur_line;

#define LINE(N)  src_lines.v[N]

/* buffer to write binary data */
static char *out_buf;
static unsigned int out_buf_cap = 0;

/* utility for writing data */
#define WRITE0(T,V,POS)                         \
  do {                                          \
    while ((POS) + sizeof(T) >= out_buf_cap) {  \
      out_buf_cap *= 2;                         \
      out_buf = realloc(out_buf, out_buf_cap);  \
    }                                           \
    *(T*)(out_buf + (POS)) = (V);               \
  } while (0)

#define WRITE_GO(T,V,POS)                       \
  do {                                          \
    WRITE0(T,V,POS);                            \
    (POS) += sizeof(T);                         \
 } while (0)

static BOOL parse_instr(struct Instruction *instr, char **next);
static void instr_free(struct Instruction P); 

static void arg_free(struct InstrArg P)
{
  switch (P.type) {
  case InstrVar:
  case Label:
  case String:
  case LineNum:
  case Functor:
  case ArgRuleset:
    free(P.v);
    break;
  case InstrVarList:
    VEC_FREE0((VEC_T(INT_V)*)P.v);
    free(P.v);
    break;
  case InstrList:
  {
    VEC_FREE((VEC_T(INSTR_V)*)P.v, instr_free);
    free(P.v);
    break;
  }
  default:
    fprintf(stderr, "type = %d\n", P.type);
    ASSERT(FALSE);
    break;
  }
}

static void instr_init(struct Instruction *P) 
{                                            
  P->arg_num = 0;
}

static void instr_free(struct Instruction P) 
{
  unsigned int i;

  for (i = 0; i < P.arg_num; i++) {
    arg_free(P.args[i]);
  }
}

static void rule_el_init(struct RuleEl *P)                            
{
  P->label = 0;
  VEC_INIT(&(P)->instrs);                     
}

static void rule_el_free(struct RuleEl P)
{
  VEC_FREE(&P.instrs, instr_free);
}

static void rule_init(struct Rule *P) 
{                                          
  (P)->name = -1;                             
  rule_el_init(&P->amatch);
  rule_el_init(&P->mmatch);
  rule_el_init(&P->guard);
  rule_el_init(&P->body);
}

static void rule_free(struct Rule P)                            
{
  rule_el_free(P.amatch);
  rule_el_free(P.mmatch);
  rule_el_free(P.guard);
  rule_el_free(P.body);
}

static void ruleset_init(struct Ruleset *P)                         
{                                            
  (P)->id = 0;                                
  VEC_INIT(&(P)->rules);                    
}

static void ruleset_free(struct Ruleset P) 
{                                           
  VEC_FREE(&(P).rules, rule_free); 
}

static void il_init(struct IL *P)                              
{                                           
  VEC_INIT(&(P)->rulesets);                   
} 

static void il_free(struct IL P) 
{                                           
  VEC_FREE(&(P).rulesets, ruleset_free);      
} 

/*----------------------------------------------------------------------*/

static char* skipws(char* s)
{
  while (*s == ' ' || *s == '\t') s++;
  return s;
}

static BOOL is_empty_line(char *line)
{
  for (; *line==' ' || *line=='\t';  line++) ;
  if (*line == '\r' || *line == '\n') return TRUE;
  else return FALSE;
}

static BOOL str_to_int(char *buf, int *x)
{
  long int l;
  char *end;

  l = strtol(buf, &end, 10);
  if (end==0 || buf==end || *end!='\0') return FALSE;
  if (errno == ERANGE) return FALSE;
  if (errno == EINVAL) return FALSE;
  if (l > INT_MAX || l < INT_MIN) {
    fprintf(stderr, "Integer, out of range %s\n", buf);
  }
  *x = (int)l;
  return TRUE;
}

static BOOL str_to_double(char *buf, double *v)
{
  char *end;

  *v = strtod(buf, &end);
  if (end==0 || buf==end || *end!='\0') return FALSE;
  if (errno == ERANGE) return FALSE;
  return TRUE;
}

/* output is str */
static char *read_quoted_str(char *line, char *str)
{
  int i = 0;
  ASSERT(*line == '\'');
  line++;
  
  while (TRUE) {
    switch (*line) {
    case '\\':
      line++;
      str[i++] = *line++;
      break;
    case '\'':
      str[i] = '\0';
      return line+1;
    case EOF:
      ASSERT(FALSE);
      break;
    default:
      str[i++] = *line++;
      break;
    }
  }

  ASSERT(FALSE);
  return NULL;
}

static char *read_int(char *line, int *n)
{
  int sign  = 1;

  ASSERT(isdigit(*line) || *line == '-');
  if (*line == '-') sign = -1;
  
  *n = 0;
  while (isdigit(*line)) {
    *n *= 10;
    *n += (*line) - '0';
    line++;
  }
  *n *= sign;
  
  return line;
}

static char *read_uint(char *line, unsigned int *n)
{
  /* 横着実装 */
  return read_int(line, (int*)n);
}

static char *read_string(char *line, ID_T *ret)
{
  char *start, *end;
  int num;


  if (SEQ(line, "null")) {
    num = strlen("null");
    start = line;
    end = line + num;
  } else {
    char *t;
    ASSERT((*line) == '"');
    start = line + 1;
    for (t = start, num = 0; *t != '"'; t++, num++) ;
    end = t + 1;
  }

  {
    unsigned int id;
    char *s = malloc(sizeof(char) * num + 1);
    strncpy(s, start, num);
    s[num] = '\0';
    id = get_symbol_id(s);
    *ret = id;
    free(s);
  }
  return end;
}

static char *read_functor(char *line, struct Functor *functor)
{
  if (*line == '$') {
    line++;
    if (SEQ(line, "in")) {
      functor->type = IN_PROXY;
      line += strlen("in");
    } else if (SEQ(line, "out")) {
      functor->type = OUT_PROXY;
      line += strlen("out");
    } else {
      ASSERT(FALSE);
    }
    return line;
  }
  else if (*line == '\'') { /* symbol functor */
    char s[1<<16];
    int n;
    
    functor->type = SYMBOL;
    line = read_quoted_str(line, s);

    functor->v.sym_atom.module_id = 0;
    functor->v.sym_atom.symbol_id = get_symbol_id(s);
    if (*line == '.') { /* symbol with module */
      functor->v.sym_atom.module_id = functor->v.sym_atom.symbol_id;
      line = read_quoted_str(line+1, s);
      functor->v.sym_atom.symbol_id = get_symbol_id(s);
    }
    /* skip '_' */
    ASSERT(*line == '_');
    line++;
    
    line = read_int(line, &n);
    functor->v.sym_atom.arity = (LmnArity)n;
    functor->v.sym_atom.functor_id = get_functor_id(*functor);
    return line;
  }
  else { /* data atom */
    int i;
    char s[1<<8], *t;

    /* copy before '_' */
    for (i = 0, t = line; *t != '_'; i++, t++) {
      s[i] = *t;
    }
    s[i] = '\0';
    line = t + 1;
    
    if (str_to_int(s, &functor->v.int_value)) {
        functor->type = INT;
    }
    else if (str_to_double(s, &functor->v.double_value)) {
      functor->type = DOUBLE;
    }
    else {
      fprintf(stderr, "read_functor: invalid format %d %s %s\n", cur_line, LINE(cur_line), line);
      exit(1);
    }

    ASSERT(*line == '1');
    return line + 1;
  }
}

/*----------------------------------------------------------------------
 * Parse
 */

/*
 * 入力を行に分解してからパースをする.再帰降下型構文解析だが,
 * 入力の性質上バックトラックはほとんどない.
 */

static char *parse_typed_arg(enum ArgType type, char *line, void **ret)
{
  line = skipws(line);

  switch (type) {
  case InstrVar:
  case LineNum:
  {
    int n;
    *ret = malloc(sizeof(INT_T));
    line = read_int(line, &n);
    *(INT_T*)*ret = n;
    return line;
  }
  case String:
  {
    ID_T n;
    *ret = malloc(sizeof(INT_T));
    line = read_string(line, &n);
    *(ID_T*)*ret = n;
    return line;
  }
  case Functor:
  {
    *ret = malloc(sizeof(struct Functor));
    line = read_functor(line, *ret);
    return line;
  }
  case ArgRuleset:
  {
    int n;
    ASSERT(*line == '@');
    *ret = malloc(sizeof(ID_T));
    line = read_int(line+1, &n);
    *(ID_T*)*ret = n;
    return line;
  }
  case Label:
  {
    int n;
    ASSERT(*line == 'L');
    *ret = malloc(sizeof(ID_T));
    line = read_int(line+1, &n);
    *(ID_T*)*ret = n;
    return line;
  }
  case InstrVarList:
  {
    char *result = NULL;
    char delims[] = ", ";
    char buf[1<<16];
    char *end;
    int i;
    VEC_T(INT_V) v;
    ASSERT(*line == '[');
    line++;

    VEC_INIT(&v);

    end = strchr(line, ']');
    for (i = 0; line+i!=end; i++) buf[i] = line[i];
    buf[i] = '\0';
    line = end+1;
    result = strtok(buf, delims);
    while (result) {
      int n;
      read_int(result, &n);
      PUSH(&v, n);
      result = strtok(NULL, delims);
    }
    *ret = malloc(sizeof(VEC_T(INT_V)));
    *(VEC_T(INT_V)*)*ret = v;

    return line;
  }
  case InstrList:
  {
    VEC_T(INSTR_V) v;
    VEC_INIT(&v);

    while (TRUE) {
      char *l;
      struct Instruction i;
      cur_line++;
      if (parse_instr(&i, &l)) {
        PUSH(&v, i);
        if (SEQ(skipws(l), "]")) {
          break;
        }
      } else {
        fprintf(stderr, "unknown instruction : %s", skipws(LINE(cur_line)));
        ASSERT(FALSE);
      }
    }
    *ret = malloc(sizeof(VEC_T(INSTR_V)));
    *(VEC_T(INSTR_V)*)*ret = v;
    return LINE(cur_line);
  }
  default:
    ASSERT(FALSE);
    break;
  }
  ASSERT(FALSE);
  return NULL;
}

static char *parse_instr_arg(char *line, enum ArgType *at, struct Instruction *instr)
{
  line = skipws(line);
  ASSERT(*line == '[');

  while (*at) {
    struct InstrArg a;
    line = skipws(line)+1;
    a.type = *at;
    line = parse_typed_arg(*at, line, &a.v);
    
    instr->args[instr->arg_num++] = a;
    at++;
    
    if (*line == ']' && *at) {
      /* removeatom の引数の数が固定でないので、引数の残存検査 */
      /* TODO print message if verbose mode*/
      break;
    }
  }
  line = skipws(line);

/*   ASSERT(*line == ']'); */
  return line + 1;
  return NULL;
}

static BOOL parse_instr(struct Instruction *instr, char **next)
{
  char *line;
  unsigned int i;

  line = skipws(LINE(cur_line));

  instr_init(instr);
  
  for (i = 0; i < sizeof(spec)/sizeof(spec[0]); i++) {
    if (SEQ(line, spec[i].op_str) &&
        !isalpha(*(line + strlen(spec[i].op_str)))) {
      line = skipws(line+strlen(spec[i].op_str));
      instr->id = spec[i].op;
      if ((line = parse_instr_arg(line, spec[i].args, instr)) != NULL) {
        *next = line;
        return TRUE;
      } else ASSERT(FALSE);
    }
  }
  instr_free(*instr);
  return FALSE;
}

static BOOL parse_rule_el(struct RuleEl *r, char *category)
{
  char *line;

  line = skipws(LINE(cur_line));
  if (SEQ(line, category)) line += strlen(category);
  else return FALSE;

  if (*line == 'L') {
    unsigned int n;
    line = read_uint(line+1, &n);
    r->label= n;
  }

  cur_line++;

  while (TRUE) {
    struct Instruction instr;
    if (parse_instr(&instr, &line)) {
      PUSH(&r->instrs, instr);
      cur_line++;
    }
    else break;
  }

  return TRUE;
}

static BOOL parse_rule(struct Rule *r)
{
  BOOL b = FALSE;
  if (!SEQ(LINE(cur_line), RULE)) return FALSE;
  cur_line++;
  rule_init(r);


  b = parse_rule_el(&r->amatch, ATOMMATCH) || b;
  b = parse_rule_el(&r->mmatch, MEMMATCH) || b;
  b = parse_rule_el(&r->guard, GUARD) || b;
  b = parse_rule_el(&r->body, BODY) || b;

  return b;
}

static BOOL parse_ruleset(struct Ruleset *rs)
{
  char *line = LINE(cur_line);
  unsigned int n;
  if (!SEQ(line, RULESET)) return FALSE;

  ruleset_init(rs);
  
  line += strlen(RULESET);
  line = read_uint(line, &n);
  rs->id = n;

  cur_line++;

  while (1) {
    struct Rule r;
    fflush(stdout);
    if (parse_rule(&r)) {
      PUSH(&rs->rules, r);
    } else if (is_empty_line(LINE(cur_line))) {
      cur_line++;
    } else {
      break;
    }
  }
  return TRUE;
}

static BOOL parse_il(struct IL *il)
{
  cur_line = 0;

  il_init(il);
  
  while (cur_line < src_lines.num) {
    struct Ruleset rs;
    if (parse_ruleset(&rs)) {
      PUSH(&il->rulesets, rs);
      continue;
    }
    else if (is_empty_line(LINE(cur_line))) cur_line++;
    else if (SEQ(LINE(cur_line), INLINE)) break;
    else { ASSERT(FALSE); }
  }

  return TRUE;
}

static void read_all_lines(void)
{
  int line_size = 1<<16;
  char *line = malloc(sizeof(char) * line_size);
  char *p;

  while (1) {
    if (!fgets(line, line_size, stdin)) {
      break;
    }
    p = malloc(sizeof(char) * strlen(line) + 1);
    strcpy(p, line);
    PUSH(&src_lines, p);
  }

  free(line);
}

/*----------------------------------------------------------------------
 * Dump
 */

static char* get_op_str(enum LmnInstruction op)
{
  unsigned int i;

  for (i = 0; i < ARY_SIZE(spec); i++) {
    if (spec[i].op == op) {
      return spec[i].op_str;
    }
  }
  return "null";
}

static void symbols_dump(void)
{
  unsigned int i;

  printf("--- symbols ---\n");
  for (i = 0; i < symbols.num; i++) {
    struct SymbolEntry *e = VEC_REF(symbols, i);
    printf("%d: %s\n", e->id, e->str);
  }   
}

static void functors_dump(void)
{
  unsigned int i;
  
  printf("--- functors ---\n");
  for (i = 0; i < functors.num; i++) {
    struct SymbolFunctor *e = VEC_REF(functors, i);
    printf("%d: %d.%d_%d\n", e->functor_id, e->module_id, e->symbol_id, e->arity);
  }
}

static void functor_dump(struct Functor a)
{
  switch (a.type) {
  case SYMBOL:
    printf("%d.%d_%d[%d]", a.v.sym_atom.module_id,
           a.v.sym_atom.symbol_id, a.v.sym_atom.arity, a.v.sym_atom.functor_id);
    break;
  case INT:
    printf("%d", a.v.int_value);
    break;
  case DOUBLE:
    printf("%f", a.v.double_value);
    break;
  case IN_PROXY:
    printf("in-proxy");
    break;
  case OUT_PROXY:
    printf("out-proxy");
    break;
  default:
    ASSERT(FALSE);
  }
}

static void instr_dump(struct Instruction a);

static void arg_dump(struct InstrArg a)
{
  switch (a.type) {
  case InstrVar:
  case Label:
  case String:
  case LineNum:
  case ArgRuleset:
    printf("%d", *(int*)a.v);
    break;
  case Functor:
    functor_dump(*(struct Functor*)a.v);
    break;
  case InstrVarList:
  {
    unsigned int i;
    VEC_T(INT_V) v = *(VEC_T(INT_V)*)a.v;
    printf("[");
    for (i = 0; i < v.num; i++) {
      if (i>0) printf(", ");
      printf("%d", v.v[i]);
    }
    printf("]");
    break;
  }
  case InstrList:
  {
    unsigned int i;
    VEC_T(INSTR_V) v = *(VEC_T(INSTR_V)*)a.v;
    printf("[\n");
    for (i = 0; i < v.num; i++) {
      printf("   ");
      instr_dump(v.v[i]);
    }
    printf("     ]");
    break;
  }
  default:
    ASSERT(FALSE);
    break;
  }
}

static void instr_dump(struct Instruction a)
{
  unsigned int i;
  printf("     %s arg= [", get_op_str(a.id));
  for (i = 0; i < a.arg_num; i++) {
    if (i>0) printf(", ");
    arg_dump(a.args[i]);
  }
  printf("]\n");
}

static void rule_el_dump(struct RuleEl a)
{
  unsigned int i;

  printf("    Label=%d \n", a.label);
  for (i = 0; i < a.instrs.num; i++) {
    instr_dump(a.instrs.v[i]);
  }
}

static void rule_dump(struct Rule a)
{
  printf("Rule NAME=%d \n", a.name);
  printf("  amatch \n");
  rule_el_dump(a.amatch);
  printf("  mmatch \n");
  rule_el_dump(a.mmatch);
  printf("  guard \n");
  rule_el_dump(a.guard);
  printf("  body \n");
  rule_el_dump(a.body);
}

static void ruleset_dump(struct Ruleset a)
{
  unsigned int i;

  printf("Ruleset ID=%d \n", a.id);
  for (i = 0; i < a.rules.num; i++) {
    rule_dump(a.rules.v[i]);
  }
}

static void il_dump(struct IL il)
{
  unsigned int i;

  symbols_dump();
  functors_dump();
  
  for (i = 0; i < il.rulesets.num; i++) {
    ruleset_dump(il.rulesets.v[i]);
  }
}

/*----------------------------------------------------------------------
 * Optimization
 */

/*
 * * 不要な命令の削除
 * * 命令列の変換
 */

static BOOL is_not_allowed(struct Instruction *instr)
{
  if (instr->id == INSTR_FINDATOM) {
    struct Functor *a = ((struct Functor *)instr->args[2].v);
    if (a->type != SYMBOL) return TRUE;
  }
  return FALSE;
}

static BOOL is_unused(struct Instruction *instr)
{
  if (instr->id == INSTR_REMOVEATOM) {
    struct Functor *a = ((struct Functor *)instr->args[2].v);
    if (a->type != SYMBOL) return TRUE;
  }

  return FALSE;
}

static void optimize_instrs(VEC_T(INSTR_V) *v)
{
  int i;

  for (i = v->num-1; i >= 0; i--) {
    struct Instruction *instr = VEC_REF(*v, i);
    
    if (instr->id == INSTR_JUMP) {
      /* move first argument(Label) to last */
      SWAP(struct InstrArg, instr->args[0], instr->args[1]);
      SWAP(struct InstrArg, instr->args[1], instr->args[2]);
      SWAP(struct InstrArg, instr->args[2], instr->args[3]);
    }
    if (instr->id == INSTR_REMOVEATOM) {
      /* 第３引数は無視する */
      if (instr->arg_num == 3) {
        instr->arg_num--;
        arg_free(instr->args[2]);
      }
    }
    if (instr->id == INSTR_GROUP) {
      { /* 保持している命令列を再帰的に処理 */
        VEC_T(INSTR_V) *instrs = (VEC_T(INSTR_V)*)instr->args[0].v;
        optimize_instrs(instrs);
      }
    }

    if (is_unused(instr)) {
      VEC_REMOVE(v, (unsigned int)i);
    }
    if (is_not_allowed(instr)) {
      fprintf(stderr, "Instruction: ");
      instr_dump(*instr);
      fprintf(stderr, "not allowed\n");
      exit(1);
    }
  }
}

static void optimize_amatch(struct RuleEl *r)
{

}

static void optimize_mmatch(struct RuleEl *r)
{

}

static void optimize_guard(struct RuleEl *r)
{
  if (r->instrs.num > 0) {
    struct Instruction instr;

    instr.id = INSTR_SWAP_WORK_VEC;
    instr.arg_num = 0;
    VEC_INSERT(&r->instrs, 0, instr);
  }
}

static void optimize_rule(struct Rule *r)
{
  optimize_instrs(&r->amatch.instrs);
  optimize_instrs(&r->mmatch.instrs);
  optimize_instrs(&r->guard.instrs);
  optimize_instrs(&r->body.instrs);
}

static void optimize_ruleset(struct Ruleset *rs)
{
  unsigned int i;

  for (i = 0; i < rs->rules.num; i++) {
    optimize_rule(VEC_REF(rs->rules, i));
  }
}

static void optimize(struct IL il)
{
  unsigned int i;

  for (i = 0; i < il.rulesets.num; i++) {
    optimize_ruleset(VEC_REF(il.rulesets, i));
  }
}

/*----------------------------------------------------------------------
 * Resolve ruleset tag
 */

struct RulesetTag {
  ID_T src_id;
  ID_T id;
};

VEC_DEF(struct RulesetTag, RULESETTAG_V);

static struct RulesetTag find_tag(VEC_T(RULESETTAG_V) *v, ID_T id)
{
  unsigned int i;

  for (i = 0; i < v->num; i++) {
    struct RulesetTag t = *VEC_REF(*v, i);
    if (t.src_id == id) {
      return t;
    }
  }
  ASSERT(FALSE);
}

static void resolve_ruleset_re(struct RuleEl r, VEC_T(RULESETTAG_V) *v)
{
  unsigned int i, j;

  for (i = 0; i < r.instrs.num; i++) {
    struct Instruction *instr = VEC_REF(r.instrs, i);
    for (j = 0; j < instr->arg_num; j++) {
      struct InstrArg *a = &instr->args[j];
      if (a->type == ArgRuleset) {
        struct RulesetTag t = find_tag(v, *(ID_T*)a->v);
        *(ID_T*)a->v = t.id;
      }
    }
  }
}

static void resolve_ruleset(struct IL *il)
{
  unsigned int i0, i1;
  unsigned int id;
  VEC_T(RULESETTAG_V) v;
  VEC_INIT(&v);

  id = 0;

  for (i0 = 0; i0 < il->rulesets.num; i0++) {
    struct Ruleset *rs = VEC_REF(il->rulesets, i0);
    struct RulesetTag t;
    t.src_id = rs->id;
    t.id = id++;
    rs->id = t.id;
    PUSH(&v, t);
  }

  for (i0 = 0; i0 < il->rulesets.num; i0++) {
    struct Ruleset *rs = VEC_REF(il->rulesets, i0);
    for (i1 = 0; i1 < rs->rules.num; i1++) {
      struct Rule *r = VEC_REF(rs->rules, i1);

      resolve_ruleset_re(r->amatch, &v);
      resolve_ruleset_re(r->mmatch, &v);
      resolve_ruleset_re(r->guard, &v);
      resolve_ruleset_re(r->body, &v);
    }
  }

  VEC_FREE0(&v);
}

/*----------------------------------------------------------------------
 * output binary
 */ 

struct LabelInfo {
  unsigned int label;
  unsigned int pos;      /* position of output buffer */
};

VEC_DEF(struct LabelInfo, LABEL_V);

static void output_instr(struct Instruction *instr,
                         VEC_T(LABEL_V) *labels,
                         unsigned int *pos,
                         BOOL b);

/* output symbol table */
static void output_symbols(unsigned int *pos)
{
  unsigned int i, j;

  WRITE_GO(lmn_interned_str, symbols.num, *pos);
  
  for (i = 0; i < symbols.num; i++) {
    struct SymbolEntry e = *VEC_REF(symbols, i);
    unsigned int len = strlen(e.str);
    WRITE_GO(lmn_interned_str, e.id, *pos);
    WRITE_GO(int16_t, len, *pos);
    for (j = 0; j < len; j++) {
      WRITE_GO(char, e.str[j], *pos);
    }
  }   
}

/* output functor table */
static void output_functors(unsigned int *pos)
{
  unsigned int i;

  WRITE_GO(LmnFunctor, functors.num, *pos);
  for (i = 0; i < functors.num; i++) {
    struct SymbolFunctor e = *VEC_REF(functors, i);
    WRITE_GO(LmnFunctor, e.functor_id, *pos);
    WRITE_GO(lmn_interned_str, e.module_id, *pos);
    WRITE_GO(lmn_interned_str, e.symbol_id, *pos);
    WRITE_GO(LmnArity, e.arity, *pos);
  }
}

static void output_arg(struct InstrArg a, VEC_T(LABEL_V) *labels, unsigned int *pos, BOOL first)
{
  switch (a.type) {
  case InstrVar:
    WRITE_GO(LmnInstrVar, *(LmnInstrVar*)a.v, *pos);
    break;
  case Label:
  {
    ID_T n = *(ID_T*)a.v;
    
    if (first) {
      /* write temporary value */
      WRITE_GO(LmnJumpOffset, 0, *pos);
    } else {
      unsigned int i;
      /* find label */
      for (i = 0; i < labels->num; i++) {
        struct LabelInfo l = *VEC_REF(*labels, i);
        if (l.label == n) {
          WRITE_GO(LmnJumpOffset, l.pos - *pos - sizeof(LmnJumpOffset), *pos);
          break;
        }
      }
      if (i >= labels->num) assert(FALSE);
    }
    break;
  }
  case InstrVarList:
  {
    unsigned int i;
    VEC_T(INT_V) v = *(VEC_T(INT_V)*)a.v;
    unsigned int num = v.num;

    WRITE_GO(LmnInstrVar, num, *pos);
    for (i = 0; i < num; i++) {
      WRITE_GO(LmnInstrVar, *VEC_REF(v, i), *pos);
    }
    break;
  }
  case String:
    WRITE_GO(lmn_interned_str, *(lmn_interned_str*)a.v, *pos);
    break;
  case LineNum:
    WRITE_GO(LmnLineNum, *(LmnLineNum*)a.v, *pos);
    break;
  case Functor:
  {
    struct Functor f = *(struct Functor*)a.v;
    switch (f.type) {
    case SYMBOL:
    {
      WRITE_GO(LmnLinkAttr, LMN_ATTR_MAKE_LINK(0), *pos);
      WRITE_GO(LmnFunctor, f.v.sym_atom.functor_id, *pos);
      break;
    }
    case INT:
    {
      WRITE_GO(LmnLinkAttr, LMN_ATOM_INT_ATTR, *pos);
      WRITE_GO(int, f.v.int_value, *pos);
      break;
    }
    case DOUBLE:
    {
      WRITE_GO(LmnLinkAttr, LMN_ATOM_DBL_ATTR, *pos);
      WRITE_GO(double, f.v.double_value, *pos);
      break;
    }
    case IN_PROXY:
    {
      WRITE_GO(LmnLinkAttr, LMN_ATTR_MAKE_LINK(0), *pos);
      WRITE_GO(LmnFunctor, LMN_IN_PROXY_FUNCTOR, *pos);
      break;
    }
    case OUT_PROXY:
    {
      WRITE_GO(LmnLinkAttr, LMN_ATTR_MAKE_LINK(0), *pos);
      WRITE_GO(LmnFunctor, LMN_OUT_PROXY_FUNCTOR, *pos);
      break;
    }
    default:
      assert(FALSE);
      break;
    }
    break;
  }
  case ArgRuleset:
  {
    /* resolve ruleset でIDは振り直してある */
    WRITE_GO(LmnRulesetId, *(LmnRulesetId*)a.v, *pos);
    break;
  }
  case InstrList:
  {
    unsigned int i;
    VEC_T(INSTR_V) *instr = (VEC_T(INSTR_V)*)a.v;

    for (i = 0; i < instr->num; i++) {
      output_instr(VEC_REF(*instr, i), labels, pos, first);
    }
    break;
  }
  default:
    assert(FALSE);
    break;
  }
}

static void output_instr(struct Instruction *instr,
                         VEC_T(LABEL_V) *labels,
                         unsigned int *pos,
                         BOOL b)
{
  unsigned int i;
  
  if (!(instr->id == INSTR_GROUP ||
        instr->id == INSTR_BRANCH)) {
    WRITE_GO(LmnInstrOp, instr->id, *pos);
  }
  for (i = 0; i < instr->arg_num; i++) {
    struct InstrArg a = instr->args[i];
    output_arg(a, labels, pos, b);
  }
}

static void output_rule_el(struct RuleEl r, VEC_T(LABEL_V) *labels, unsigned int *pos, BOOL b)
{
  unsigned int i;

  for (i = 0; i < r.instrs.num; i++) {
    struct Instruction *instr = VEC_REF(r.instrs, i);
    output_instr(instr, labels, pos, b);
  }
}

static void output_rule(struct Rule r, VEC_T(LABEL_V) *labels, unsigned int *pos, BOOL b)
{
  struct LabelInfo l;
  unsigned int start = *pos;

  *pos += sizeof(RuleSize);

  /*
   * Atom-match is not used.
   */
  /* output_rule_el(r.amatch, labels, pos, b); */

  output_rule_el(r.mmatch, labels, pos, b);
  l.label = r.guard.label;

  /* ガードがなくてもラベルはbodyの位置になるのでok */
  l.pos = *pos;
  PUSH(labels, l);
  output_rule_el(r.guard, labels, pos, b);

  l.label = r.body.label;
  l.pos = *pos;
  PUSH(labels, l);
  output_rule_el(r.body, labels, pos, b);

  WRITE0(RuleSize, *pos - start - sizeof(RuleSize), start);
}

static void output_ruleset(struct Ruleset rs, unsigned int *pos)
{
  unsigned int i;
  VEC_T(LABEL_V) labels;
  VEC_INIT(&labels);

  WRITE_GO(int16_t, rs.id, *pos);
  WRITE_GO(int16_t, rs.rules.num, *pos);
  
  for (i = 0; i < rs.rules.num; i++) {
    struct Rule r = *VEC_REF(rs.rules, i);
    unsigned int t = *pos;
    /* output twice for labeling */
    output_rule(r, &labels, pos, TRUE);
    *pos = t;
    output_rule(r, &labels, pos, FALSE);
  }
}

static void output_il(struct IL il)
{
  unsigned int i;
  unsigned int pos = 0;

  output_symbols(&pos);
  output_functors(&pos);

  WRITE_GO(uint32_t, il.rulesets.num, pos);
  
  for (i = 0; i < il.rulesets.num; i++) {
    struct Ruleset rs = *VEC_REF(il.rulesets, i);
    output_ruleset(rs, &pos);
  }

  fwrite(out_buf, pos, 1, stdout);
}

/*----------------------------------------------------------------------*/

static void add_functors(int functor_id,
                         int module_id,
                         char *symbol,
                         int arity)
{
  struct SymbolFunctor f;

  f.functor_id = functor_id;
  f.module_id = module_id;
  f.symbol_id = get_symbol_id(symbol);
  f.arity = arity;
  PUSH(&functors, f);
}

static void init_functors(void)
{
  add_functors(LMN_IN_PROXY_FUNCTOR, 0, "$in", 3);
  add_functors(LMN_OUT_PROXY_FUNCTOR, 0, "$out", 3);
  add_functors(LMN_LIST_FUNCTOR, 0, ".", 3);
  add_functors(LMN_NIL_FUNCTOR, 0, "[]", 1);
  func_id = 4;
}

static void init(void)
{
  VEC_INIT(&src_lines);
  VEC_INIT(&symbols);
  VEC_INIT(&functors);
  out_buf_cap = 1024;
  out_buf = malloc(sizeof(char) * out_buf_cap);

  get_symbol_id("null");
  init_functors();
}

static void finalize(void)
{
  VEC_FREE(&src_lines, free);
  VEC_FREE(&symbols, symbol_entry_free);
  VEC_FREE0(&functors);
  free(out_buf);
}

static void usage(void)
{
  fprintf(stderr,
          "Usage: conv_il [file]\n"
          "options:\n"
          "  --dump       dump parsed structure.\n"
          );
  exit(1);
}
 
static int parse_options(int argc, char *argv[])
{
  int c, option_index;

  struct option long_options[] = {
    {"dump", 0, 0, 1000},
    {"help",    0, 0, 1001},
    {0, 0, 0, 0}
  };

  while ((c = getopt_long(argc, argv, "+s", long_options, &option_index)) != -1) {
    switch (c) {
    case 0:
      printf("log_options entries must have positive 4th member.\n");
      exit(1);
      break;
    case 1000:
      dump = TRUE;
      break;
    case 1001: /* help */ /*FALLTHROUGH*/
    case '?': usage(); break;
    default:
      printf("?? getopt returned character code 0x%x ??\n", c);
      exit(1);
      break;
    }
  }
  
  return optind;
}

int main(int argc, char* argv[])
{
  struct IL il;

  parse_options(argc, argv);

  init();
  read_all_lines();
  
  if (!parse_il(&il)) ASSERT(FALSE);

  resolve_ruleset(&il);
  optimize(il);
  
  if (dump) {
    il_dump(il);
  } else {
    output_il(il);
  }
  
  il_free(il);
  
  finalize();

  return 0;
}