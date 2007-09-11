#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <lmntal.h>
#include <rule.h>

LmnRuleSetTable lmn_ruleset_table;

static unsigned int load_uint8(FILE *in)
{
  unsigned char d[1];
  fread(d, 1, 1, in);
  
  printf("%s\n", __func__);
  return *(uint8_t*)&d[0];
}

static unsigned int load_uint16(FILE *in)
{
  unsigned char d[2];
  fread(d, 2, 1, in);
  
  printf("%s\n", __func__);
  return *(uint16_t*)&d[0];
}

static unsigned int load_uint32(FILE *in)
{
  unsigned char d[4];
  fread(d, 4, 1, in);
  
  printf("%s\n", __func__);
  return *(uint32_t*)&d[0];
}

/*
  load rule and return(malloced)

  data format is::
  byte size of rule(uint32)
  byte * X
*/
static LmnRule *load_rule(FILE *in)
{
  int instr_size = load_uint32(in);
  lmn_rule_instr instr = LMN_CALLOC(BYTE, instr_size);
  int rule_name = load_uint32(in);

  fread(instr, 1, instr_size, in);
  
  return lmn_rule_make(instr, rule_name);
}

/*
  load ruleset and return(malloced)

  data format is::
  count of rules(uint32)
  rule * X
*/
static LmnRuleSet *load_ruleset(FILE *in)
{
  int rule_size = load_uint32(in);
  LmnRuleSet *res =lmn_ruleset_make(rule_size);
  int i;

  for(i=0; i<rule_size; ++i){
    lmn_ruleset_put(res, load_rule(in));
  }
  
  return res;
}

/*
  load rulesets from file_name to lmn_ruleset_table

  data format is::
  count of ruleset(uint32)
  ruleset * X
*/
void load_instr(char *file_name)
{
  FILE *in = fopen(file_name, "rb");
  unsigned int i;

  lmn_ruleset_table.size = load_uint32(in);
  lmn_ruleset_table.entry = LMN_CALLOC(LmnRuleSet*, lmn_ruleset_table.size);

  for(i=0; i<lmn_ruleset_table.size; ++i){
    lmn_ruleset_table.entry[i] = load_ruleset(in);
  }

  fclose(in);
}

