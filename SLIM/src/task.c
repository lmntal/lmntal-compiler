/*
 * task.c
 */


#include <stdio.h>
#include "lmntal.h"
#include "instruction.h"

/* this size should be the maximum size of 'spec' arguments */
/* Or allocated when required */
LmnWord v1[1024];
LmnWord v2[1024];
/* for value tag */

LmnByte k1[1024];
LmnByte k2[1024];

LmnWord *wv, *tv;
LmnByte *wkv, *tkv;

#define SWAP(T,X,Y)       do { T t=(X); (X)=(Y); (Y)=t;} while(0)

struct Entity {
  LmnMembrane	*mem;
  struct Entity	*next;
};

struct MemStack {
  struct Entity	*head,
    *tail;
} memstack;

void memstack_init()
{
  memstack.head = LMN_MALLOC(struct Entity);
  memstack.tail = memstack.head;
}

void memstack_push(LmnMembrane *mem)
{
  struct Entity *ent = LMN_MALLOC(struct Entity);
  ent->mem = mem;
  ent->next = memstack.head->next;
  memstack.head->next = ent;
}

int memstack_isempty()
{
  return memstack.head->next==NULL;
}

LmnMembrane* memstack_pop()
{
  struct Entity *ent = memstack.head->next;
  memstack.head->next = ent->next;
  LmnMembrane *mem = ent->mem;
  LMN_FREE(ent);
  return mem;
}

LmnMembrane* memstack_peek()
{
  return memstack.head->next->mem;
}

void memstack_printall()
{
  struct Entity *ent;
  for(ent = memstack.head; ent!=NULL; ent = ent->next){
    if(ent->mem!=NULL)printf("%d ", ent->mem->name);
  }
  printf("\n");
}

static BOOL react_ruleset(LmnMembrane *mem, LmnRuleSet *ruleset)
{
  int i;

  wv[0] = mem;
  for (i = 0; i < ruleset->num; i++) {
    if (interpret(ruleset->rules[i]->instr)) return TRUE;
  }
  
  return FALSE;
}

struct RuleSetList {
  LmnRuleSet *ruleset;
  RuleSetList *next;
};
typedef struct RuleSetList RuleSetNode;

int exec(LmnMembrane *mem)
{
  RuleSetNode *rs = mem->rulesets;
  int flag = FALSE;
/*   	flag = react(mem, systemrulesets); */
  if (!flag) {
    while (rs->ruleset != NULL) {
      if (react(mem, rs->ruleset)) {
        flag = TRUE;
        break;
      }
      rs = rs->next;
    }
  }
	
  return flag;
}

void run()
{
  /* Initialize for running */
  wv = v1;
  tv = v2;
  wkv = kv1;
  tkv = kv2;
  
  while(!memstack_isempty()){
    LmnMembrane *mem = memstack_peek();
    if(!exec(mem))
      memstack_pop();
    memstack_printall();
  }
}

static BOOL interpret(lmn_rule_instr instr)
{
  LmnInstrOp op;
  
  while (TRUE) {
    LMN_INS_READ_OP(instr, op);
    switch (op) {
    case INSTR_SPEC:
      /* ignore spec, because wv is initially large enough. */
      instr += 8*2;
      break;
    case INSTR_JUMP:
    {
      LmnInstrVar num, i, n;
      uint16_t offset;

      i = 0;
      /* atom */
      LMN_IMS_READ(LmnInstrVar, instr, num);
      for (; num--; i++) {
        LMN_IMS_READ(LmnInstrVar, instr, n);
        tv[i] = wv[n];
        kv[i].temp = kv[n].tag;
      }
      /* mem */
      LMN_IMS_READ(LmnInstrVar, instr, num);
      for (; num--; i++) {
        LMN_IMS_READ(LmnInstrVar, instr, n);
        tv[i] = wv[n];
        kv[i].temp = kv[n].tag;
      }
      while (i--) wv[i] = tv[i];
      /* vars */
      LMN_IMS_READ(LmnInstrVar, instr, num);
      for (; num--; i++) {
        LMN_IMS_READ(LmnInstrVar, instr, n);
        tv[i] = wv[n];
        kv[i].temp = kv[n].tag;
      }

      SWAP(LmnWord*, wv, tv);
      SWAP(LmnByte*, wkv, tkv);

      LMN_IMS_READ_UINT16(instr, offset);
      instr += offset;
      break;
    }
    case INSTR_COMMIT:
      instr += sizeof(LmnInstrVar) + sizeof(LmnWord);
      break;
    case INSTR_NEWATOM:
    {
      LmnInstrVar atomi, memi;
      LmnFunctor f;
      LmnAtomPtr ap;
      
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnFunctor, instr, f);
      ap = lmn_new_atom(f);
      lmn_mem_push_atom((LmnMembrane*)wv[memi], ap);
      wv[atomi] = ap;
      break;
    }
    case INSTR_ALLOCLINK:
    {
      LmnInstrVar link, atom, n;
      
      LMN_IMS_READ(LmnInstrVar, instr, link);
      LMN_IMS_READ(LmnInstrVar, instr, atom);
      LMN_IMS_READ(LmnInstrVar, instr, n);

      if (LMN_ATTR_IS_DATA(wkv[atom])) {
        wv[link] = wv[atom];
        wkv[link] = wkv[atom];
      } else { /* link to atom */
        wv[link] = LMN_ATOM(wv[atom]);
        wkv[link] = LMN_ATTR_MAKE_LINK(n);
      }
      break;
    }
    case INSTR_UNIFYLINKS:
    {
      LmnInstrVar link1, link2, mem;
      
      LMN_IMS_READ(LmnInstrVar, instr, link1);
      LMN_IMS_READ(LmnInstrVar, instr, link2);
      LMN_IMS_READ(LmnInstrVar, instr, mem);

      if (LMN_ATTR_IS_DATA(wkv[link1]) && LMN_ATTR_IS_DATA(wkv[link2])) {
        #ifdef DEBUG
        fprintf(stderr, "Tow data atoms are connected each other.\n");
        #endif
      }
      else if (LMN_ATTR_IS_DATA(wkv[link1])) {
        /* TODO wkvをコピーする必要はないかな? */
           
        LMN_ATOM_SET_LINK(LMN_ATOM(wv[link2]), LMN_ATTR_GET_VALUE(wkv[link2]), kv[link1]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[link2]),
                               LMN_ATTR_GET_VALUE(wkv[link2]),
                               wkv[link1]);
      }
      else if (LMN_ATTR_IS_DATA(wkv[link2])) {
        LMN_ATOM_SET_LINK(LMN_ATOM(wv[link1]), LMN_ATTR_GET_VALUE(wkv[link1]), kv[link2]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[link1]),
                               LMN_ATTR_GET_VALUE(wkv[link1]),
                               wkv[link2]);
      } else {
        LMN_ATOM_SET_LINK(LMN_ATOM(wv[link1]), LMN_ATTR_GET_VALUE(wkv[link1]), wv[link2]);
        LMN_ATOM_SET_LINK(LMN_ATOM(wv[link2]), LMN_ATTR_GET_VALUE(wkv[link2]), wv[link1]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[link1]),
                               LMN_ATTR_GET_VALUE(wkv[link1]),
                               wkv[link2]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[link2]),
                               LMN_ATTR_GET_VALUE(wkv[link2]),
                               wkv[link1]);
      }
        break;
    }
    case INSTR_PROCEED:
      /* TODO スタックの操作とか・・・*/
      goot END;
      break;
    default:
      fprintf(stderr, "interpret: Unknown operation %d\n", op);
      exit(1);
    }
  }
 END:;
}


/* int main(int argc, char *argv[]) */
/* { */
/*   LmnMembrane *m1,*m2,*m3,*m4,*m5,*m6; */
/*   m1 = LMN_MALLOC(LmnMembrane); */
/*   m2 = LMN_MALLOC(LmnMembrane); */
/*   m3 = LMN_MALLOC(LmnMembrane); */
/*   m4 = LMN_MALLOC(LmnMembrane); */
/*   m5 = LMN_MALLOC(LmnMembrane); */
/*   m6 = LMN_MALLOC(LmnMembrane); */
/*   m1->name = 1; */
/*   m2->name = 2; */
/*   m3->name = 3; */
/*   m4->name = 4; */
/*   m5->name = 5; */
/*   m6->name = 6; */
/*   memstack_init(); */
/*   memstack_push(m1); */
/*   memstack_push(m2); */
/*   memstack_push(m3); */
/*   memstack_push(m4); */
/*   run(); */
/*   /\* */
/* 	memstack_printall(); */
/* 	memstack_push(m1); */
/* 	memstack_printall(); */
/* 	memstack_push(m2); */
/* 	memstack_printall(); */
/* 	memstack_push(m3); */
/* 	memstack_printall(); */
/* 	memstack_pop(); */
/* 	memstack_printall(); */
/* 	memstack_push(m4); */
/* 	memstack_printall(); */
/* 	memstack_pop(); */
/* 	memstack_pop(); */
/* 	memstack_printall(); */
/* 	memstack_push(m5); */
/* 	memstack_printall(); */
/*     //	printf("Hello! SLIM!\n"); */
/* 	*\/ */
/*   return 0; */
/* } */

