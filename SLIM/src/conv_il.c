#include <limits.h>
#include <errno.h>
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include "lmntal.h"
#include "instruction.h"

/*
 *  * symbol table
 *      lmn_interned_str : シンボルの数
 *      数 * シンボル
 *  ** シンボル
 *      lmn_interned_str : シンボルID
 *      uint16_t         : シンボルの長さ
 *      char             : 長さ分の文字列
 *
 *  * functor table
 *      LmnFunctor       : 数
 *      entry * 数
 *  ** entry
 *      LmnFunctor       : ID
 *      lmn_interned_str : 名前
 *      LmnArity         : アリティ
 *
 *  * instruction table
 *      uint16_t         : ルールセットの数   PLEASE ADD THIS!!
 *      ruleset * 数
 *  ** ruleset
 *      uint16_t         : ID
 *      uint16_t         : ルールの数
 *      rule * 数
 *  *** rule
 *      uint16_t            : サイズ
 *      instruction (byte * サイズ) 
 */


/*  ???
 *  ** functor
 *     BYTE : tag
 *     tagに応じたデータ
 *        
 */


#define debug fprintf

enum ArgType {
  InstrVar = 1,
  Label,
  InstrVarList,
  String,
  LineNum,
  Functor,
  ArgRuleset
};

struct InstrSpec {
  char op_str[128];
  LmnInstrOp op;
  enum ArgType args[128];
}

/* 初期値のない配列の要素が0に初期化されることを前提にしている
 * これって、仕様なんでしたっけ?
 * むしろグローバル変数の初期化?
 */
  spec[] = {
    {"spec", INSTR_SPEC,          {InstrVar, InstrVar,}},
    /* special */
    {"jump", INSTR_JUMP,        {Label, InstrVarList, InstrVarList, InstrVarList}},
    {"commit", INSTR_COMMIT,      {String, LineNum}},
    {"newatom", INSTR_NEWATOM,    {InstrVar, InstrVar, Functor}},
    {"removeatom", INSTR_REMOVEATOM, {InstrVar, InstrVar, Functor}},
    {"alloclink", INSTR_ALLOCLINK, {InstrVar, InstrVar, InstrVar}},
    {"unifylinks", INSTR_UNIFYLINKS, {InstrVar, InstrVar, InstrVar}},
    {"enqueueatom", INSTR_ENQUEUEATOM, {InstrVar}},
    {"proceed", INSTR_PROCEED, {0}},
    {"loadruleset", INSTR_LOADRULESET, {InstrVar, ArgRuleset}},
    {"deref", INSTR_DEREF, {InstrVar, InstrVar, InstrVar, InstrVar}},
    {"func", INSTR_FUNC, {InstrVar, Functor}},
  };

enum FunctorType {SYMBOL, INT, DOUBLE};

struct Functor {
  enum FunctorType type;

  union {
    int int_value;
    double double_value;
    struct {
      lmn_interned_str symbol_id;
      LmnArity         arity;
    } sym_atom;
  } v;
};


char **src_lines;
unsigned int src_lines_num;
unsigned int src_lines_cap;

unsigned int cur_line;

char *out;
unsigned int out_cap;
unsigned int pos;

#define PUSH(V,X)                               \
  do {                                          \
    if (V##_num == V##_cap) {                   \
      V##_cap *= 2;                             \
      V = realloc(V, sizeof(V[0]) * V##_cap);   \
    }                                           \
    V[V##_num++] = (X);                         \
  } while(0)

#define POP(V,X)                                    \
  do {                                              \
    if (V##_num == 0) {                             \
      fprintf(stderr, "pop from empty vector\n");   \
    }                                               \
    V##_num--;                                      \
    *X = V[V##_num];                                \
  } while (0) 

  
typedef int16_t RuleNumType;
typedef int16_t RulesetId;


/*----------------------------------------------------------------------
 * Label
 */
struct LabelInfo {
  unsigned int label;
  unsigned int pos;
};

struct LabelInfo *labels;
unsigned int labels_num;
unsigned int labels_cap;

/*--------------------------------------------------------------------------------
 * Label Link
 */
struct LabelLink {
  unsigned int label;
  unsigned int write_pos;
  unsigned int start_pos; /* label_pos - pos - offset がJump先 */
};

struct LabelLink *llink;
unsigned int llink_num;
unsigned int llink_cap;

/*----------------------------------------------------------------------
 * Ruleset
 */

struct Rule {
  unsigned int name;
  BYTE *instr;
};

struct Ruleset {
  unsigned int id, src_id;
  unsigned int pos;
  unsigned int rule_num;
};

struct Ruleset *ruleset;
unsigned int ruleset_num, ruleset_cap;

struct RulesetLink {
  unsigned int pos;
  unsigned int src_id;
};

struct RulesetLink *rlink;
unsigned int rlink_num, rlink_cap;

unsigned int rule_id = 0;

/*----------------------------------------------------------------------
 * Symbol
 */

struct SymbolEntry {
  char *str;
  unsigned int id;
};

struct SymbolEntry *symbols;
unsigned int symbols_cap, symbols_num;
unsigned int symbol_id = 0;

static lmn_interned_str get_symbol_id(char *str)
{
  unsigned int i;

  /* TODO 効率的な実装が必要 */
  for (i = 0; i < symbols_num; i++) {
    if (!strcmp(symbols[i].str, str)) return symbols[i].id;
  }
  {
    struct SymbolEntry s;
    s.str = malloc(strlen(str)+1);
    strcpy(s.str, str);
    s.id = symbol_id++;
    PUSH(symbols, s);
  
    return s.id;
  }
}

/*----------------------------------------------------------------------
 * Functor
 */

struct FunctorEntry {
  unsigned int name;
  unsigned int arity;
  unsigned int id;
};
struct FunctorEntry *funcs;
unsigned int funcs_cap, funcs_num;
unsigned int func_id = 0;

static LmnFunctor get_functor_id(struct Functor f)
{
  unsigned int i;

  /* TODO 効率的な実装が必要 */
  for (i = 0; i < funcs_num; i++) {
    if (funcs[i].arity == f.v.sym_atom.arity &&
        funcs[i].name == f.v.sym_atom.symbol_id) {
      return funcs[i].id;
    }
  }
  {
    struct FunctorEntry a;
    a.arity = f.v.sym_atom.arity;
    a.name = f.v.sym_atom.symbol_id;
    a.id = func_id++;
    PUSH(funcs, a);
    return a.id;
  }
}


/*--------------------------------------------------------------------------------
 * Parsing
 */

#define RULESET    "Compiled Ruleset"
#define RULE       "Compiled Rule"
#define INLINE     "Inline"
#define ATOMMATCH  "--atommatch:"
#define MEMMATCH   "--memmatch:"
#define GUARD      "--guard:"
#define BODY       "--body:"

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

static void expand_out_buf()
{
  if (pos >= out_cap) {
    out_cap *= 2;
    out = realloc(out, sizeof(char) * out_cap);
  }

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
  assert(*line == '\'');
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
      assert(FALSE);
      break;
    default:
      str[i++] = *line++;
      break;
    }
  }

  assert(FALSE);
  return NULL;
}

static char *read_int(char *line, int *n)
{
  int sign  = 1;
  
  assert(isdigit(*line) || *line == '-');
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

static char *read_functor(char *line, struct Functor *functor)
{
  
  if (*line == '\'') { /* symbol functor */
    char s[1<<16];
    
    functor->type = SYMBOL;
    line = read_quoted_str(line, s);
    functor->v.sym_atom.symbol_id = get_symbol_id(s);
    
    /* skip '_' */
    assert(*line == '_');
    line++;
    line = read_int(line, (int*)&functor->v.sym_atom.arity);

    return line;
  }
  else { /* data atom */
    int i;
    char s[1<<16], *t;

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
      fprintf(stderr, "read_functor: invalid format %s\n", s);
      exit(1);
    }

    assert(*line == '1');
    return line + 1;
  }
}

#define SEQ(A,S) (!strncmp((A),(S),strlen(S)))
#define WRITE(T,V,POS)                              \
  do { expand_out_buf();                            \
    (*(T*)(out+POS) = (V));                         \
      } while (0)

/* 引数がこれ以上なければ NULLが帰る */
static char *next_arg(char* s)
{
  while (*s != ']' && (*s == '[' || *s == ',' ||*s == ' ' || *s == '\t')) {
    s++;
  }

  return s;
}

static char *parse_string(char *line)
{
  char *start, *end;
  int num;


  if (SEQ(line, "null")) {
    num = strlen("null");
    start = line;
    end = line + num;
  } else {
    char *t;
    assert((*line) == '"');
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
    WRITE(lmn_interned_str, id, pos);
    pos += sizeof(lmn_interned_str);
    free(s);
  }
  return end;
}

static char *parse_var_list(char *line)
{
  unsigned int num = 0;
  unsigned int num_pos = pos;
  pos += sizeof(LmnInstrVar);
  
  while (TRUE) {
    unsigned int n;
    line = next_arg(line);
    if (*line == ']') break;
    line = read_int(line, &n);
    WRITE(LmnInstrVar, n, pos);
    pos += sizeof(LmnInstrVar);
    num++;
  }

  WRITE(LmnInstrVar, num, num_pos);

  return line;
}

static char *parse_arg(char *line, enum ArgType type)
{
  switch (type) {
  case InstrVar:
  {
    unsigned int n;
    line = read_int(line, &n);
    WRITE(LmnInstrVar, n, pos);
    pos += sizeof(LmnInstrVar);
    return line;
  }
  case String:
    line = parse_string(line);
    return line;
  case LineNum:
  {
    unsigned int n;
    line = read_int(line, &n);
    WRITE(LmnLineNum, n, pos);
    pos += sizeof(LmnLineNum);
    return line;
  }
  case Functor:
  {
    struct Functor f;

    line = read_functor(line, &f);
    switch (f.type) {
    case SYMBOL:
    {
      LmnFunctor f_id = get_functor_id(f);
      WRITE(LmnLinkAttr, LMN_ATTR_MAKE_LINK(0), pos);
      pos += sizeof(LmnLinkAttr);
      WRITE(LmnFunctor, f_id, pos);
      pos += sizeof(LmnFunctor);
            
      break;
    }
    case INT:
      WRITE(LmnLinkAttr, LMN_ATTR_MAKE_DATA(LMN_ATOM_INT_ATTR), pos);
      pos += sizeof(LmnLinkAttr);
      WRITE(int, f.v.int_value, pos);
      pos += sizeof(int);
      break;
    case DOUBLE:
      WRITE(LmnLinkAttr, LMN_ATTR_MAKE_DATA(LMN_ATOM_DBL_ATTR), pos);
      pos += sizeof(LmnLinkAttr);
      WRITE(double, f.v.double_value, pos);
      pos += sizeof(double);
      break;
    default:
      fprintf(stderr, "not implemented data type %d\n", f.type);
      exit(1);
      break;
    }

    return line;
  }
  case ArgRuleset:
  {
    struct RulesetLink l;
    assert(*line == '@');
    line = read_int(line, &l.src_id);
    l.pos = pos;
    pos += sizeof(RulesetId);
    return line;
  }
  case Label:
    assert(FALSE);
    break;
  case InstrVarList:
    assert(FALSE);
    break;
  default:
    assert(FALSE);
    break;
  }
  assert(FALSE);
  return NULL;
}

static BOOL parse_special_instr(char *line, struct InstrSpec spec)
{
  switch (spec.op) {
  case INSTR_JUMP:
  {
    unsigned int label;
    struct LabelLink link;

    WRITE(LmnInstrOp, spec.op, pos);
    pos += sizeof(LmnInstrOp);

    line = next_arg(line) + 1/* L */;
    line = read_int(line, &label);

    line = next_arg(parse_var_list(line)); 
    line = next_arg(parse_var_list(line)); 
    line = next_arg(parse_var_list(line)); 

    link.write_pos = pos;
    link.start_pos = pos + sizeof(LmnJumpOffset);
    link.label = label;

    PUSH(llink, link);
    pos += sizeof(LmnJumpOffset);

    return TRUE;
  }
  default:
    break;
  }
  return FALSE;
}

static BOOL parse_instr(struct Rule *rule, struct InstrSpec spec)
{
  char *line;
  enum ArgType *arg;

  line = skipws(src_lines[cur_line]);

  if (!SEQ(line, spec.op_str)) return FALSE;
  line += strlen(spec.op_str);
  line = skipws(line);
  if (parse_special_instr(line, spec)) return TRUE;


  WRITE(LmnInstrOp, spec.op, pos);
  pos += sizeof(LmnInstrOp);
  arg = spec.args;
  line = next_arg(line);
  while (*arg) {
    line = next_arg(parse_arg(line, *arg));
    arg++;
  }
  return TRUE;
}

static BOOL parse_rule_el(struct Rule *rule)
{
  char *line;
  unsigned int i;
  
  line = skipws(src_lines[cur_line]);

  if (SEQ(line, ATOMMATCH)) {
    /* 続く命令を読み捨てる */
    while (!SEQ(line, MEMMATCH)) {
      line = skipws(src_lines[++cur_line]);
    }
    return TRUE;
  }
  if (SEQ(line, MEMMATCH)) {
    cur_line++;
    return TRUE;
  }
  if (SEQ(line, GUARD)) {
    struct LabelInfo l;

    line += strlen(GUARD) + 1/*L*/;
    line = read_int(line, &l.label);
    l.pos = pos;
    PUSH(labels, l);
    cur_line++;
    return TRUE;
  }
  if (SEQ(line, BODY)) {
    struct LabelInfo l;

    line += strlen(BODY) + 1/*L*/;
    line = read_int(line, &l.label);
    l.pos = pos;
    PUSH(labels, l);

    cur_line++;
    return TRUE;
  }

  if (is_empty_line(line)) {
    cur_line++;
    return FALSE;
  }

  for (i = 0; i < sizeof(spec)/sizeof(spec[0]); i++) {
    if (parse_instr(rule, spec[i])) {
      cur_line++;
      return TRUE;
    }
  }

  fprintf(stderr, "Unrecognized line: %s\n", line);
  exit(1);
}

static struct Rule *parse_rule(void)
{
  struct Rule *rule;
  int rule_start_pos;
  typedef int16_t RuleSize;

  rule = malloc(sizeof(struct Rule));
  if (!SEQ(src_lines[cur_line], RULE)) return NULL;

  rule_start_pos = pos;
  pos += sizeof(RuleSize);
  cur_line++;

  while (1) {
    if (!parse_rule_el(rule)) break;
  }
  WRITE(RuleSize, pos - rule_start_pos, rule_start_pos);

  return rule;
}

static struct Ruleset *parse_ruleset(void)
{
  unsigned int id;
  char *end;
  char *line;
  struct Ruleset *rs;
  unsigned int rule_num_pos;
  
  line = src_lines[cur_line];
  
  if (!SEQ(line, RULESET)) return NULL;

  line += strlen(RULESET) + 2/*space + atmark*/;
  line = read_int(line, &id);

  rs = malloc(sizeof(struct Ruleset));

  rs->src_id = id;
  rs->id = rule_id++;
  rs->pos = pos;
  rs->rule_num = 0;

  WRITE(RulesetId, rs->id, pos);
  pos += sizeof(RulesetId);
  rule_num_pos = pos;
  pos += sizeof(RuleNumType);
  
  cur_line++;

  while (1) {
    struct Rule *r;

    r = parse_rule();
    if (r) {
      rs->rule_num++;
    } else {
      break;
    }
  }

  if (rs->rule_num == 0) {
    fprintf(stderr, "parse_ruleset: line[%d], ruleset is empty\n", cur_line);
  }

  WRITE(RuleNumType, rs->rule_num, rule_num_pos);

  return rs;
}

static void parse(void)
{

  struct Ruleset *rs;
  
  cur_line = 0;
  
  while (cur_line < src_lines_num) {
    rs = parse_ruleset();
    if (rs) {
      PUSH(ruleset, *rs);
      continue;
    }

    if (SEQ(src_lines[cur_line], INLINE)) break;
  }

}

static void read_all_lines(void)
{
  int line_size = 1<<16;
  char *line;
  
  while (1) {
    line = malloc(line_size);
    if (!fgets(line, line_size, stdin)) {
      free(line);
      break;
    }
    if (src_lines_num == src_lines_cap) {
      src_lines_cap *= 2;
      src_lines = realloc(src_lines, sizeof(char*) * src_lines_cap);
    }
    src_lines[src_lines_num++] = line;
  }
}

static unsigned int find_label_pos(unsigned int label)
{
  unsigned int i;

  for (i = 0; i < labels_num; i++) {
    if (labels[i].label == label) {
      return labels[i].pos;
    }
  }
  assert(FALSE);
  return 0;
}

static void resolve_label(void)
{
  unsigned int i;

  for (i = 0; i < llink_num; i++) {
    unsigned int label_pos = find_label_pos(llink[i].label);
    WRITE(LmnJumpOffset, label_pos - llink[i].start_pos, llink[i].write_pos);
  }
}

static unsigned int find_ruleset_pos(unsigned int id)
{
  unsigned int i;

  for (i = 0; i < ruleset_num; i++) {
    if (ruleset[i].src_id == id) {
      return ruleset[i].pos;
    }
  }
  assert(FALSE);
  return 0;
}

static void resolve_ruleset(void)
{
  unsigned int i;

  for (i = 0; i < rlink_num; i++) {
    unsigned int label_pos = find_ruleset_pos(rlink[i].src_id);
    WRITE(RulesetId, label_pos, rlink[i].pos);
  }
  
}

#define MAKE_VEC(NAME)                                      \
  do {                                                      \
    NAME##_cap = 1<<16;                                     \
    NAME##_num = 0;                                         \
    NAME = malloc(sizeof(struct LabelInfo) * NAME##_cap);   \
  } while (0)

int main(int argc, char* argv[])
{
  int i;
  
  /* initialize */
  out_cap = 1<<16;
  out = malloc(out_cap);

  MAKE_VEC(labels);
  MAKE_VEC(ruleset);
  MAKE_VEC(src_lines);
  MAKE_VEC(llink);
  MAKE_VEC(funcs);
  MAKE_VEC(rlink);

  read_all_lines();
  parse();

  resolve_label();
  resolve_ruleset();

  /* header */
  f
  /* symbol table */
  fwrite(&symbols_num, sizeof(lmn_interned_str), 1, stdout);
  for (i = 0; i < symbols_num; i++) {
    int n;
    fwrite(&symbols[i].id, sizeof(lmn_interned_str), 1, stdout);
    n = strlen(symbols[i].str);
    fwrite(&n, sizeof(uint16_t), 1, stdout);
    fwrite(symbols[i].str, n, 1, stdout);
  }

  /* functor table */
  fwrite(&funcs_num, sizeof(LmnFunctor), 1, stdout);
  for (i = 0; i < symbols_num; i++) {
    fwrite(&funcs[i].id, sizeof(LmnFunctor), 1, stdout);
    fwrite(&funcs[i].name, sizeof(lmn_interned_str), 1, stdout);
    fwrite(&funcs[i].arity, sizeof(LmnArity), 1, stdout);
  }

  fwrite(&ruleset_num, sizeof(uint32_t), 1, stdout);
  /* print */
  fwrite(out, pos, 1, stdout);

  return 0;
}
