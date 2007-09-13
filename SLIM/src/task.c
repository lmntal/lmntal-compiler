/*
 * task.c
 */


#include <stdio.h>
#include "lmntal.h"
#include "instruction.h"
#include "read_instr.h"

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
#define REF_CAST(T,X)     (*(T*)&(X))

struct Entity {
  LmnMembrane	*mem;
  struct Entity	*next;
};

struct MemStack {
  struct Entity	*head, *tail;
} memstack;

/*----------------------------------------------------------------------
 * Stack for backtrack
 */

enum StackEntryType { PT_FINDATOM, PT_GROUP };

typedef struct StackEntry {
  enum StackEntryType type;
  LmnRuleInstr ret_pt;
  union {
    LmnAtomPtr find_atom_iter;
  } v;
} StackEntry;

/* 失敗したときに戻るところ */
struct Stack {
	StackEntry *entry;
	unsigned int num;
	unsigned int cap;
};

static struct Stack make_stack(unsigned int size) 
{
	struct Stack st;
	st.entry = LMN_NALLOC(StackEntry, size);
	st.num = 0;
	st.cap = size;
	return st;
}

#define STACK_PUSH stack_push
#define STACK_POP stack_pop

static void stack_push(struct Stack st, StackEntry x)
{
	if (st.num == st.cap) {
		st.cap *= 2;
		st.entry = LMN_REALLOC(StackEntry, st.entry, st.cap);
	}
	st.entry[st.num++] = x;
}

static StackEntry stack_pop(struct Stack st)
{
	if (st.num == 0) {
		fprintf(stderr, "stack is empty\n");
        assert(FALSE);
	}
	return st.entry[--st.num];
}

static BOOL stack_is_empty(struct Stack st)
{
	return st.num == 0;
}

struct Stack st;

static BOOL interpret(LmnRuleInstr instr);

static void memstack_init()
{
  memstack.head = LMN_MALLOC(struct Entity);
  memstack.head->next = NULL;
  memstack.head->mem = NULL;
  memstack.tail = memstack.head;
}

static void memstack_push(LmnMembrane *mem)
{
  struct Entity *ent = LMN_MALLOC(struct Entity);
  ent->mem = mem;
  ent->next = memstack.head->next;
  memstack.head->next = ent;
}

static int memstack_isempty()
{
  return memstack.head->next==NULL;
}

static LmnMembrane* memstack_pop()
{
  struct Entity *ent = memstack.head->next;
  LmnMembrane *mem = ent->mem;
  memstack.head->next = ent->next;
  LMN_FREE(ent);
  return mem;
}

static LmnMembrane* memstack_peek()
{
  return memstack.head->next->mem;
}

static void memstack_printall()
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

  REF_CAST(LmnMembrane*, wv[0]) = mem;
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

static int exec(LmnMembrane *mem)
{
  RuleSetNode *rs = mem->rulesets;
  int flag = FALSE;
/*   	flag = react(mem, systemrulesets); */
  if (!flag) {
    while (rs != NULL) {
      if (react_ruleset(mem, rs->ruleset)) {
        flag = TRUE;
        break;
      }
      rs = rs->next;
    }
  }
	
  return flag;
}

void run(void)
{
  LmnMembrane *mem;

  /* Initialize for running */
  wv = v1;
  tv = v2;
  wkv = k1;
  tkv = k2;

  st = make_stack(16);
  memstack_init();
  
  /* make toplevel membrane */
  
  mem = lmn_mem_make();

  lmn_mem_dump(mem);

  /*     lmn_mem_add_ruleset(mem, lmn_ruleset_table.entry[0]); */

  memstack_push(mem);
  /* call init atom creation rule */
  react_ruleset(mem, lmn_ruleset_table.entry[0]);

  lmn_mem_dump(mem);
  
  while(!memstack_isempty()){
    LmnMembrane *mem = memstack_peek();
    if(!exec(mem))
      memstack_pop();
    memstack_printall();
  }

  lmn_mem_dump(mem);
}

static BOOL interpret(LmnRuleInstr instr)
{
  LmnInstrOp op;
  LmnRuleInstr start = instr;
  BOOL is_backtrack;
  StackEntry st_top;

#define BACKTRACK                               \
  do {                                          \
    if (stack_is_empty(st)) goto FAIL;          \
    else {                                      \
      is_backtrack = TRUE;                      \
      st_top = stack_pop(st);                   \
      instr = st_top.ret_pt;                    \
      goto LOOP_HEAD;                           \
    }                                           \
  } while (0)

  st.num = 0;

  is_backtrack = FALSE;
  
  while (TRUE) {
  LOOP_HEAD:;
    LMN_IMS_READ(LmnInstrOp, instr, op);
    printf("op: %d %d\n", op, instr - start - 2);
    switch (op) {
    case INSTR_SPEC:
      /* ignore spec, because wv is initially large enough. */
      instr += sizeof(LmnInstrVar)*2;
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
        tkv[i] = wkv[n];
      }
      /* mem */
      LMN_IMS_READ(LmnInstrVar, instr, num);
      for (; num--; i++) {
        LMN_IMS_READ(LmnInstrVar, instr, n);
        tv[i] = wv[n];
        tkv[i] = wkv[n];
      }
      /* vars */
      LMN_IMS_READ(LmnInstrVar, instr, num);
      for (; num--; i++) {
        LMN_IMS_READ(LmnInstrVar, instr, n);
        tv[i] = wv[n];
        tkv[i] = wkv[n];
      }

      SWAP(LmnWord*, wv, tv);
      SWAP(LmnByte*, wkv, tkv);

      LMN_IMS_READ(uint16_t, instr, offset);
      instr += offset;
      
      break;
    }
    case INSTR_COMMIT:
      instr += sizeof(lmn_interned_str) + sizeof(LmnLineNum);
      break;
    case INSTR_FINDATOM:
    {
      LmnInstrVar atomi, memi;
      LmnLinkAttr attr;
      LmnRuleInstr ret_pt;

      ret_pt = instr;
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnLinkAttr, instr, attr);
      
      if (LMN_ATTR_IS_DATA(attr)) {
        if (LMN_ATTR_GET_VALUE(attr) == LMN_ATOM_IN_PROXY_ATTR ||
            LMN_ATTR_GET_VALUE(attr) == LMN_ATOM_OUT_PROXY_ATTR) {
          fprintf(stderr, "どうすりゃいいの？ プロキシのfindatom\n");
        }
        fprintf(stderr, "I can not find data atoms.\n");
        assert(FALSE);
      } else { /* symbol atom */
        LmnFunctor f;
        LmnAtomPtr atom;

        LMN_IMS_READ(LmnFunctor, instr, f);
        if (is_backtrack) {
          atom = LMN_ATOM_GET_NEXT((LmnAtomPtr)st_top.v.find_atom_iter);
        } else { /* first processing */
          atom = lmn_mem_get_atomlist((LmnMembrane*)wv[memi], f)->head;
        }
        if (atom) {
          StackEntry e = {PT_FINDATOM};
          e.ret_pt = ret_pt;
          e.v.find_atom_iter = atom;
          stack_push(st, e);
          REF_CAST(LmnAtomPtr, wv[atomi]) = atom;
        } else {
          BACKTRACK;
        }
      }
      break;
    }
    case INSTR_NEWATOM:
    {
      LmnInstrVar atomi, memi;
      LmnAtomPtr ap;
      LmnLinkAttr attr;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnLinkAttr, instr, attr);
      if (LMN_ATTR_IS_DATA(attr)) {
        switch (attr) {
/*         case LMN_ATOM_IN_PROXY_ATTR: */
/*           /\* TODO: proxy implementation *\/ */
/*           ap = lmn_new_atom(0); */
/*           lmn_mem_push_atom((LmnMembrane*)wv[memi], ap); */
/*           REF_CAST(LmnAtomPtr, wv[atomi]) = ap; */
/*           break; */
/*         case LMN_ATOM_OUT_PROXY_ATTR: */
/*           break; */
        case LMN_ATOM_INT_ATTR:
        {
          int x;
          LMN_IMS_READ(int, instr, x);
          REF_CAST(int, wv[atomi]) = x;
          break;
        }
        case LMN_ATOM_DBL_ATTR:
        {
          double *x;

          x = LMN_MALLOC(double);
          LMN_IMS_READ(double, instr, *x);
          REF_CAST(double*, wv[atomi]) = x;
          break;
        }
        default:
          assert(FALSE);
          break;
        }
        wkv[atomi] = LMN_ATTR_MAKE_DATA(LMN_ATOM_INT_ATTR);
      } else { /* symbol atom */
        LmnFunctor f;
        LMN_IMS_READ(LmnFunctor, instr, f);
        ap = lmn_new_atom(f);
        lmn_mem_push_atom((LmnMembrane*)wv[memi], ap);
        REF_CAST(LmnAtomPtr, wv[atomi]) = ap;
      }
      break;
    }
    case INSTR_NATOMS:
    {
      LmnInstrVar memi, natoms;
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnInstrVar, instr, natoms);
      if(natoms != (LmnInstrVar)lmn_mem_natoms((LmnMembrane*)wv[memi])) {
        BACKTRACK;
      }
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
        REF_CAST(LmnAtomPtr, wv[link]) = LMN_ATOM(wv[atom]);
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
        fprintf(stderr, "Two data atoms are connected each other.\n");
        #endif
      }
      else if (LMN_ATTR_IS_DATA(wkv[link1])) {
        /* TODO wkvをコピーする必要はないかな? */
           
        LMN_ATOM_SET_LINK(LMN_ATOM(wv[link2]), LMN_ATTR_GET_VALUE(wkv[link2]), wkv[link1]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[link2]),
                               LMN_ATTR_GET_VALUE(wkv[link2]),
                               wkv[link1]);
      }
      else if (LMN_ATTR_IS_DATA(wkv[link2])) {
        LMN_ATOM_SET_LINK(LMN_ATOM(wv[link1]), LMN_ATTR_GET_VALUE(wkv[link1]), wkv[link2]);
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
      lmn_mem_dump((LmnMembrane*)wv[0]);
      goto SUCCESS;
    case INSTR_ENQUEUEATOM:
    {
      LmnInstrVar atom;
	
      LMN_IMS_READ(LmnInstrVar, instr, atom);
      break;
    }
    case INSTR_DEQUEUEATOM:
    {
      LmnInstrVar atom;
	
      LMN_IMS_READ(LmnInstrVar, instr, atom);
      break;
    }
    case INSTR_NEWMEM:
    {
      LmnInstrVar newmemi, parentmemi, memf;
      LmnMembrane *mp;

      LMN_IMS_READ(LmnInstrVar, instr, newmemi);
      LMN_IMS_READ(LmnInstrVar, instr, parentmemi);
      LMN_IMS_READ(LmnInstrVar, instr, memf);

      mp = lmn_mem_make(); /*lmn_new_mem(memf);*/
      lmn_mem_push_mem((LmnMembrane*)wv[parentmemi], mp);
      REF_CAST(LmnMembrane*, wv[newmemi]) = mp;
      break;
    }
    case INSTR_REMOVEATOM:
    {
      LmnInstrVar atomi, memi;
      LmnLinkAttr attr;
      LmnAtomPtr atom;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnLinkAttr, instr, attr);

      if (LMN_ATTR_IS_DATA(attr)) {
        switch (LMN_ATTR_GET_VALUE(attr)) {
        case LMN_ATOM_INT_ATTR: /* notiong */  break;
        case LMN_ATOM_DBL_ATTR:
          LMN_FREE((double*)wv[atomi]);
          break;
        case LMN_ATOM_IN_PROXY_ATTR:
        case LMN_ATOM_OUT_PROXY_ATTR:
          /* TODO */
          fprintf(stderr, "よくわからない\n");
          break;
        default:
          assert(FALSE);
        }
      } else { /* symbol atom */
        LmnFunctor f;
        LMN_IMS_READ(LmnFunctor, instr, f);

        atom = (LmnAtomPtr)wv[atomi];
        if (! LMN_ATTR_IS_DATA(wkv[atomi])){
          lmn_mem_remove_atom((LmnMembrane*)wv[memi], (LmnAtomPtr)wv[atomi]);
        }
      }
      break;
    }
    case INSTR_FREEATOM:
    {
      LmnInstrVar atomi;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);
	
      if(! LMN_ATTR_IS_DATA(wkv[atomi])){
        lmn_delete_atom((LmnAtomPtr)wv[atomi]);
      }
      break;
    }
    case INSTR_REMOVEMEM:
    {
      LmnInstrVar memi;
      LmnMembrane *mp;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      instr += sizeof(LmnInstrVar); /* ingnore parent */

      mp = (LmnMembrane*)wv[memi];
      if(mp->parent->child_head == mp) mp->parent->child_head = mp->next;
      if(mp->prev) mp->prev->next = mp->next;
      if(mp->next) mp->next->prev = mp->prev;
      break;
    }
    case INSTR_FREEMEM:
    {
      LmnInstrVar memi;
      LmnMembrane *mp;

      LMN_IMS_READ(LmnInstrVar, instr, memi);

      mp = (LmnMembrane*)wv[memi];
      lmn_mem_free(mp);
      break;
    }
    case INSTR_LOADRULESET:
    {
      LmnInstrVar memi;
      LmnRulesetId id;
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnRulesetId, instr, id);
      
      lmn_mem_add_ruleset((LmnMembrane*)wv[memi], LMN_RULESET_ID(id));
      break;
    }
    default:
      fprintf(stderr, "interpret: Unknown operation %d\n", op);
      exit(1);
    }
  }
 SUCCESS:
  return TRUE;
 FAIL:
  return FALSE;
}
