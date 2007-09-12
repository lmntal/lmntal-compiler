#include <limits.h>
#include <errno.h>
#include <stdio.h>
#include "lmntal.h"
#include "instruction.h"

enum ArgType {
  InstrVar = 1,
  Label,
  InstrVarList,
  String,
  LineNum,
  Functor,
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
  {"spec", INSTR_SPEC,          {InstrVar, InstrVar}},
  {"jump", INSTR_BRANCH,        {Label, InstrVarList, InstrVarList, InstrVarList}},
  {"commit", INSTR_COMMIT,      {String, LineNum}},
  {"newatom", INSTR_NEWATOM,    {InstrVar, InstrVar, Functor}},
  {"alloclink", INSTR_ALLOCLINK, {InstrVar, InstrVar, InstrVar}},
  {"unifylinks", INSTR_UNIFYLINKS, {InstrVar, InstrVar, InstrVar}},
  {"enqueueatom", INSTR_ENQUEUEATOM, {InstrVar}},
  {"proceed", INSTR_PROCEED, {}}
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


char *gbuf;
unsigned int buf_size;
char **src_lines;
unsigned int src_line_num;
unsigned int src_line_cap;

unsigned int cur_line;

char *out;
unsigned int out_pos;

#define PUSH(V,X)                   \
  do {                             \
    if (V##_num == V##_cap) {      \
      V##_cap *= 2;                                       \
      V = realloc(V, sizeof(V[0]) * V##_cap); \
    }                                                     \
    V[V##_num++] = (X);                                   \
  } while(0)

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


/*----------------------------------------------------------------------
 * Ruleset
 */

struct Rule {
  unsigned int name;
};

struct Ruleset {
  unsigned int id;
  unsigned int pos;
  unsigned int rule_num;
};

struct Ruleset *ruleset;
unsigned int ruleset_num, ruleset_cap;

/*----------------------------------------------------------------------
 * Symbol
 */

lmn_interned_str get_symbol_id(char *st)
{
  return -1;
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

char* skipws(char* s)
{
  while (*s == ' ' || *s == '\t') s++;
  return s;
}

/* void skipws(FILE* file) */
/* { */
/*   char c; */
/*   do { */
/*     c = fgetc(file); */
/*   } while (c == ' ' || c == '\t'); */

/*   ungetc(c, file); */
/* } */

BOOL str_to_int(char *buf, int *x)
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

BOOL str_to_double(char *buf, double *v)
{
  char *end;

  *v = strtod(buf, &end);
  if (end==0 || buf==end || *end!='\0') return FALSE;
  if (errno == ERANGE) return FALSE;
  return TRUE;
}

struct Functor read_functor(FILE* file)
{
  char c;
  struct Functor functor;
  
  do {
    c = fgetc(file);
  } while (c == ' ' || c == '\t');

  if (c == '\'') {
    unsigned int str_len;

    functor.type = SYMBOL;
    ungetc(c, file);
    read_quoted_str(file, buf, &buf_size, &str_len);
    functor.v.sym_atom.symbol_id = get_symbol_id(buf);
    /* skip '_' */
    fgetc(file);
    fscanf(file, "%d", &functor.v.sym_atom.arity);
  }
  else {
    int i, dummy;
    /* read before '_'  */
    i = 0;
    while ((c = fgetc(file)) != '_') {
      buf[i++] = c;
    }
    buf[i] = '\0';

    if (str_to_int(buf, &functor.v.int_value)) {
        functor.type = INT;
    }
    else if (str_to_double(buf, &functor.v.double_value)) {
      functor.type = DOUBLE;
    } else {
      fprintf(stderr, "read_functor: invalid format %s\n", buf);
      exit(1);
    }

    fscanf(file, "%d", &dummy);
    assert(dummy == 1);
  }
  return functor;
}

#define SEQ(A,S) (!strncmp((A),(S),sizeof(S)))


BOOL parse_instr(Rule *rule)
{
  char *line;
  int i;
  
  line = skipws(src_lines[cur_line]);

  if (SEQ(line, ATOMMATCH)) {
    return TRUE;
  }
  if (SEQ(line, MEMMATCH)) return TRUE;
  if (SEQ(line, GUARD) || SEQ(line, BODY)) {
    int label;
    LabelInfo l;
    
    line += sizeof(GUARD) + 1/*L*/;
    label = atoi(line);
    l.label = label;
    l.pos = pos;
    PUSH(labels, l);
    return TRUE;
  }
  if (/*empty_line.bb*/) return FALSE;

  /* ちゅう刊語命令*/
  for (i = 0; i < sizeof(spec)/sizeof(spec[0]); i++) {
    if (!strcmp(spec[i].op_str, op_st)) {
      line = skipws(line)+1;
      
    }
  }
  
  return TRUE;
}

BOOL *parse_rule(void)
{
  unsigned int name;
  Rule rule;
  char *s;
  
  if (!SEQ(src_lines[cur_lnie], RULE)) return NULL;

  cur_line++;
  
  while (1) {
    if (!parse_instr(&rule)) break;
  }
  
}

Ruleset *parse_ruleset(void)
{
  unsigned int id;
  char *end;
  char *line;
  Ruleset *rs;
  
  line = src_lines[cur_ilne];
  
  if (!SEQ(line, RULESET)) return NULL;
  
  line += sizeof(RULESET) + 2/*space + atmark*/;
  id = strtoul(line, &end, 10);

  rs = malloc(sizeof(Ruleset));
  
  rs->id = id;
  rs->pos = pos;
  rs->rule_num = 0;

  cur_line++;
  
  while (1) {
    Rule *r;

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
  
  return rs;
}

void parse(void)
{

  Ruleset *rs;
  
  cur_line = 0;
  
  while (cur_line < src_line_num) {
    rs = parse_ruleset();
    if (rs) {
      PUSH(ruleset, *rs);
      continue;
    }

    /* Inline */
  }

}

void read_all_lines(void)
{
  int line_size = 1<<16;
  char *line;
  
  while (1) {
    line = malloc(line_size);
    if (!fgets(line, line_size, stdin)) {
      free(line);
      break;
    }
    if (src_line_num == src_line_cap) {
      src_lien_cap *= 2;
      src_lines = realloc(sizeof(char*) * src_line_cap);
    }
    src_lines[src_line_num++] = line;
  }
}
  
int main(int argc, char* argv[])
{
  /* initialize */
  buf_size = 1<<16;
  gbuf = malloc(buf_size);

  label_cap = 1<<16;
  label_num = 0;
  labels = malloc(sizeof(struct LabelINfo) * label_cap);

  ruleset_cap = 1<<16;
  ruleset_num = 0;
  ruleset = malloc(sizeof(struct Ruleset) * ruleset_cap);

  src_line_cap = 1<<16;
  src_line_num = 0;
  src_lines = malloc(sizeof(char*) * src_line_cap);

  read_all_lines();
  
  return 0;
}
    
