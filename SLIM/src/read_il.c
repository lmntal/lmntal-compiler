#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include "lmntal.h"
#include "rule.h"

LmnRuleSetTable lmn_ruleset_table;
LmnSymbolTable lmn_symbol_table;
LmnFunctorTable lmn_functor_table;

static unsigned int load_uint8(FILE *in)
{
  unsigned char d[1];
  fread(d, 1, 1, in);
  return *(uint8_t*)&d[0];
}

static unsigned int load_uint16(FILE *in)
{
  unsigned char d[2];
  fread(d, 2, 1, in);
  return *(uint16_t*)&d[0];
}

static unsigned int load_uint32(FILE *in)
{
  unsigned char d[4];
  fread(d, 4, 1, in);
  return *(uint32_t*)&d[0];
}

static unsigned int load_uint(FILE *in, int size)
{
  switch(size){
  case 1: return load_uint8(in);
  case 2: return load_uint16(in);
  case 4: return load_uint32(in);
  }
  assert(FALSE);
  return 0;
}

static LmnRule *load_rule(FILE *in)
{
  int instr_size = load_uint16(in);
  LmnRuleInstr instr = LMN_CALLOC(BYTE, instr_size);

  fread(instr, 1, instr_size, in);

/*   fprintf(stderr, "loaded rule size:%d\n", instr_size); */
  
  return lmn_rule_make(instr, 0); /* there is no name for rule */
}

static LmnRuleSet *load_ruleset(FILE *in)
{
  /* skip ruleset id because ... */
  int ruleset_id = load_uint16(in);
  int rule_size = load_uint16(in);
  LmnRuleSet *res =lmn_ruleset_make(ruleset_id, rule_size);
  int i;

/*   fprintf(stderr, "loading ruleset >>> size:%d name:%d\n", rule_size, ruleset_id); */
  for(i=0; i<rule_size; ++i){
    lmn_ruleset_put(res, load_rule(in));
  }
/*   fprintf(stderr, "loading ruleset <<< size:%d name:%d\n", rule_size, ruleset_id); */
  
  return res;
}

static void load_instr(FILE *in)
{
  unsigned int i;

  lmn_ruleset_table.size = load_uint32(in);
  lmn_ruleset_table.entry = LMN_NALLOC(LmnRuleSet*, lmn_ruleset_table.size);

/*   fprintf(stderr, "loading instr >>> size:%d\n", lmn_ruleset_table.size); */
  for(i=0; i<lmn_ruleset_table.size; ++i){
    lmn_ruleset_table.entry[i] = load_ruleset(in);
  }
/*   fprintf(stderr, "loading instr <<< size:%d\n", lmn_ruleset_table.size); */
}

static void load_symbols(FILE *in)
{
  unsigned int i;
  unsigned int symbol_num;

  symbol_num = load_uint(in, sizeof(lmn_interned_str));
  lmn_symbol_table.size = symbol_num + 1;
  lmn_symbol_table.entry = LMN_NALLOC(char*, lmn_symbol_table.size);
  memset(lmn_symbol_table.entry, 0, sizeof(char *) * lmn_symbol_table.size);

/*   fprintf(stderr, "loading symbol >>> size:%d\n", lmn_symbol_table.size); */
  for(i=0; i<symbol_num; ++i){
    unsigned int j;
    /* skip symbol id, because it must be sorted in order*/
    unsigned int symbol_id = load_uint(in, sizeof(lmn_interned_str));
    unsigned int symbol_size = load_uint16(in);
    
    lmn_symbol_table.entry[symbol_id] = LMN_NALLOC(char, symbol_size+1);

    for(j=0; j<symbol_size; ++j){
      lmn_symbol_table.entry[symbol_id][j] = load_uint8(in);
    }
    lmn_symbol_table.entry[symbol_id][symbol_size] = '\0';

/*     fprintf(stderr, "loaded a symbol name:%s\n", lmn_symbol_table.entry[symbol_id]); */
  }
/*   fprintf(stderr, "loading symbol <<< size:%d\n", lmn_symbol_table.size); */
}

static void load_functors(FILE *in)
{
  unsigned int i;
  unsigned int data_num = load_uint(in, sizeof(LmnFunctor));
  lmn_functor_table.size = data_num;
  lmn_functor_table.entry = LMN_NALLOC(LmnFunctorEntry, lmn_functor_table.size);
/*   fprintf(stderr, "loading functors >>> size:%d\n", lmn_functor_table.size); */
  for(i=0; i<data_num; ++i){
     /* skip functor id, because it must be sorted in order */
    int functor_id = load_uint(in, sizeof(LmnFunctor));
    lmn_functor_table.entry[functor_id].module = load_uint(in, sizeof(lmn_interned_str));
    lmn_functor_table.entry[functor_id].name = load_uint(in, sizeof(lmn_interned_str));
    lmn_functor_table.entry[functor_id].arity = load_uint(in, sizeof(LmnArity));

/*     fprintf(stderr, "loaded functor id:%d module:%d->%s string:%d->%s arity:%d\n", */
/* 	   functor_id, */
/* 	   lmn_functor_table.entry[functor_id].module, */
/* 	   lmn_symbol_table.entry[lmn_functor_table.entry[functor_id].module], */
/* 	   lmn_functor_table.entry[functor_id].name, */
/* 	   lmn_symbol_table.entry[lmn_functor_table.entry[functor_id].name], */
/* 	   lmn_functor_table.entry[functor_id].arity); */
  }
/*   fprintf(stderr, "loading functors <<< size:%d\n", lmn_functor_table.size); */
}

void read_il(const char *file_name)
{
  FILE *in = fopen(file_name, "rb");

  load_symbols(in);
  load_functors(in);
  load_instr(in);
  
  fclose(in);
}

/*
int main()
{
  load_symbols(stdin);
  load_functors(stdin);
  load_instr(stdin);

  return 0;
}
*/
