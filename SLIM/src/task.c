#include "membrane.h"
#include <stdio.h>

struct Entity {
	LmnMembrane	*mem;
	struct Entity	*next;
};

struct MemStack {
	struct Entity	*head,
					*tail;
} memstack;

void memstack_init(){
	memstack.head = LMN_MALLOC(struct Entity);
	memstack.tail = memstack.head;
}

void memstack_push(LmnMembrane *mem){
	struct Entity *ent = LMN_MALLOC(struct Entity);
	ent->mem = mem;
	ent->next = memstack.head->next;
	memstack.head->next = ent;
}

int memstack_isempty(){
	return memstack.head->next==NULL;
}

LmnMembrane* memstack_pop(){
	struct Entity *ent = memstack.head->next;
	memstack.head->next = ent->next;
	LmnMembrane *mem = ent->mem;
	free(ent);
	return mem;
}

LmnMembrane* memstack_peek(){
	return memstack.head->mem;
}

void memstack_printall(){
	struct Entity *ent;
	for(ent = memstack.head; ent!=NULL; ent = ent->next){
		if(ent->mem!=NULL)printf("%d ", ent->mem->name);
	}
	printf("\n");
}

int react(LmnMembrane *mem, LmnRuleSet *ruleset){
	return FALSE;
}
struct RuleSetList {
  LmnRuleSet *ruleset;
  RuleSetList *next;
};
typedef struct RuleSetList RuleSetNode;

int exec(LmnMembrane *mem) {
	RuleSetNode *rs = mem->rulesets;
	int flag = FALSE;
//	flag = react(mem, systemrulesets);
	if (!flag) {
//		while(rs->ruleset != NULL){
//			if(react(mem, rs->ruleset)) {
//				flag = TRUE;
//				break;
//			}
//			rs = rs->next;
//		}
	}
	
	return flag;
}

void run(){
	while(!memstack_isempty()){
		LmnMembrane *mem = memstack_peek();
		if(!exec(mem))
			memstack_pop();
		memstack_printall();
	}
}

int main(int argc, char *argv[]) {
	LmnMembrane *m1,*m2,*m3,*m4,*m5,*m6;
	m1 = LMN_MALLOC(LmnMembrane);
	m2 = LMN_MALLOC(LmnMembrane);
	m3 = LMN_MALLOC(LmnMembrane);
	m4 = LMN_MALLOC(LmnMembrane);
	m5 = LMN_MALLOC(LmnMembrane);
	m6 = LMN_MALLOC(LmnMembrane);
	m1->name = 1;
	m2->name = 2;
	m3->name = 3;
	m4->name = 4;
	m5->name = 5;
	m6->name = 6;
	memstack_init();
	memstack_push(m1);
	memstack_push(m2);
	memstack_push(m3);
	memstack_push(m4);
	run();
	/*
	memstack_printall();
	memstack_push(m1);
	memstack_printall();
	memstack_push(m2);
	memstack_printall();
	memstack_push(m3);
	memstack_printall();
	memstack_pop();
	memstack_printall();
	memstack_push(m4);
	memstack_printall();
	memstack_pop();
	memstack_pop();
	memstack_printall();
	memstack_push(m5);
	memstack_printall();
//	printf("Hello! SLIM!\n");
	*/
	return 0;
}

