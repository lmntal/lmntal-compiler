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

static BOOL interpret(LmnRuleInstr instr, LmnRuleInstr *next);

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
  LmnRuleInstr dummy;

  REF_CAST(LmnMembrane*, wv[0]) = mem;
  for (i = 0; i < ruleset->num; i++) {
    if (interpret(ruleset->rules[i]->instr, &dummy)) return TRUE;
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

static BOOL interpret(LmnRuleInstr instr, LmnRuleInstr *next)
{
  LmnInstrOp op;
  LmnRuleInstr start = instr;

  while (TRUE) {
    LMN_IMS_READ(LmnInstrOp, instr, op);
    fprintf(stderr, "op: %d %d\n", op, instr - start - 2);
    switch (op) {
    case INSTR_SPEC:
      /* ignore spec, because wv is initially large enough. */
      instr += sizeof(LmnInstrVar)*2;
      break;
    case INSTR_JUMP:
    {
      LmnInstrVar num, i, n;
      LmnJumpOffset offset;

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

      LMN_IMS_READ(LmnJumpOffset, instr, offset);
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

      ret_pt = instr - sizeof(LmnInstrOp);
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
        atom = lmn_mem_get_atomlist((LmnMembrane*)wv[memi], f)->head;
        
        wkv[atomi] = LMN_ATTR_MAKE_LINK(0);
        while (atom) {
          REF_CAST(LmnAtomPtr, wv[atomi]) = atom;
          if (interpret(instr, &instr)) return TRUE;
          atom = LMN_ATOM_GET_NEXT(atom);
        }
       return FALSE;
      }
      break;
    }
		case INSTR_ANYMEM:
		{
			/* TODO どれをマクロにすればいいのか分からない… */
			LmnInstrVar mem1, mem2, memt, memn;
			LmnMembrane* mp;

			LMN_IMS_READ(LmnInstrVar, instr, mem1);
			LMN_IMS_READ(LmnInstrVar, instr, mem2);
			LMN_IMS_READ(LmnInstrVar, instr, memt);
			LMN_IMS_READ(lmn_interned_str, instr, memn);

			printf("instr_anymem\n");
			mp = ((LmnMembrane*)wv[mem2])->child_head;
			while (mp) {
			/* TODO 膜名と膜種類による判定 */
				REF_CAST(LmnMembrane *, wv[mem1]) = mp;
				if (interpret(instr, &instr)) return TRUE;
				mp = mp->next;
			}
			return FALSE;
			break;
		}
		case INSTR_NMEMS:
		{
			/* TODO どれをマクロにすればいいのか分からない… */
			LmnInstrVar memi, nmems;

			LMN_IMS_READ(LmnInstrVar, instr, memi);
			LMN_IMS_READ(LmnInstrVar, instr, nmems);

			if(nmems != (LmnInstrVar)lmn_mem_nmems((LmnMembrane*)wv[memi])) {
				return FALSE;
			}
			break;
		}
		case INSTR_NORULES:
		{
			/* TODO どれをマクロにすればいいのか分からない… */
			LmnInstrVar memi;
			RuleSetList *rp;
			LMN_IMS_READ(LmnInstrVar, instr, memi);
			rp = REF_CAST(LmnMembrane *, wv[memi])->rulesets;
			if(rp) return FALSE;
		  	
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
        switch (LMN_ATTR_GET_VALUE(attr)) {
          /* TODO */
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
        wkv[atomi] = LMN_ATTR_MAKE_DATA(attr);
      } else { /* symbol atom */
        LmnFunctor f;
        LMN_IMS_READ(LmnFunctor, instr, f);
        ap = lmn_new_atom(f);
        lmn_mem_push_atom((LmnMembrane*)wv[memi], ap);
        REF_CAST(LmnAtomPtr, wv[atomi]) = ap;
        wkv[atomi] = LMN_ATTR_MAKE_LINK(0);
      }
      break;
    }
    case INSTR_NATOMS:
    {
      LmnInstrVar memi, natoms;
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnInstrVar, instr, natoms);
      if(natoms != (LmnInstrVar)lmn_mem_natoms((LmnMembrane*)wv[memi])) {
        return FALSE;
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
           
        LMN_ATOM_SET_LINK(LMN_ATOM(wv[link2]), LMN_ATTR_GET_VALUE(wkv[link2]), wv[link1]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[link2]),
                               LMN_ATTR_GET_VALUE(wkv[link2]),
                               wkv[link1]);
      }
      else if (LMN_ATTR_IS_DATA(wkv[link2])) {
        LMN_ATOM_SET_LINK(LMN_ATOM(wv[link1]), LMN_ATTR_GET_VALUE(wkv[link1]), wv[link2]);
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
		case INSTR_NEWLINK:
		{
			LmnInstrVar atom1, atom2, pos1, pos2, memi;

			LMN_IMS_READ(LmnInstrVar, instr, atom1);
			LMN_IMS_READ(LmnInstrVar, instr, pos1);
			LMN_IMS_READ(LmnInstrVar, instr, atom2);
			LMN_IMS_READ(LmnInstrVar, instr, pos2);
			LMN_IMS_READ(LmnInstrVar, instr, memi);

			if (LMN_ATTR_IS_DATA(wkv[atom1]) && LMN_ATTR_IS_DATA(wkv[atom2])) {
				#ifdef DEBUG
				fprintf(stderr, "Two data atoms are connected each other.\n");
				#endif
			}
			else if (LMN_ATTR_IS_DATA(wkv[atom1])) {
			LMN_ATOM_SET_LINK(LMN_ATOM(wv[atom2]), pos2, wv[atom1]);
			LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[atom2]), LMN_ATTR_GET_VALUE(pos2), wkv[atom1]);
			}
			else if (LMN_ATTR_IS_DATA(wkv[atom2])) {
				LMN_ATOM_SET_LINK(LMN_ATOM(wv[atom1]), pos1, wv[atom2]);
				LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[atom1]), LMN_ATTR_GET_VALUE(pos1), wkv[atom2]);
			}
			else {
				LMN_ATOM_SET_LINK(LMN_ATOM(wv[atom1]), pos1, wv[atom2]);
				LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[atom1]), LMN_ATTR_GET_VALUE(pos1), wkv[atom2]); 
				LMN_ATOM_SET_LINK(LMN_ATOM(wv[atom2]), pos2, wv[atom1]); 
				LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[atom2]), LMN_ATTR_GET_VALUE(pos2), wkv[atom1]);
			}
			break;
		}
		case INSTR_RELINK:
		{
			LmnInstrVar atom1, atom2, pos1, pos2, memi;
			LmnAtomPtr ap;
			LmnByte attr;

			LMN_IMS_READ(LmnInstrVar, instr, atom1);
			LMN_IMS_READ(LmnInstrVar, instr, pos1);
			LMN_IMS_READ(LmnInstrVar, instr, atom2);
			LMN_IMS_READ(LmnInstrVar, instr, pos2);
			LMN_IMS_READ(LmnInstrVar, instr, memi);

			ap = LMN_ATOM(LMN_ATOM_GET_LINK(wv[atom2], pos2));
			attr = LMN_ATOM_GET_LINK_ATTR(wv[atom2], pos2);

			if(LMN_ATTR_IS_DATA(wkv[atom1]) && LMN_ATTR_IS_DATA(attr)) {
			  #ifdef DEBUG
				fprintf(stderr, "Two data atoms are connected each other.\n");
				#endif 
			}
			else if (LMN_ATTR_IS_DATA(wkv[atom1])) {
				LMN_ATOM_SET_LINK(ap, attr, wv[atom1]);
				LMN_ATOM_SET_LINK_ATTR(ap, LMN_ATTR_GET_VALUE(attr), wkv[atom1]);
			}
			else if (LMN_ATTR_IS_DATA(attr)) {
			printf("hogehogehoge\n");
			printf("attr: %d\n", attr);
			printf("wkv[atom1]: %d\n", wkv[atom1]);
			printf("atom1: %d\n", atom1);
				LMN_ATOM_SET_LINK(LMN_ATOM(wv[atom1]), pos1, (LmnWord)ap);
				LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[atom1]), pos1, attr);
			}
			else {
				LMN_ATOM_SET_LINK(ap, attr, wv[atom1]);
				LMN_ATOM_SET_LINK_ATTR(ap, LMN_ATTR_GET_VALUE(attr), wkv[atom1]);
				LMN_ATOM_SET_LINK(LMN_ATOM(wv[atom1]), pos1, (LmnWord)ap);
				LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wv[atom1]), pos1, attr);
			}
			break;
		}
    case INSTR_PROCEED:
      *next = instr;
			lmn_mem_dump((LmnMembrane *)wv[0]);
      return TRUE;
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
      LmnAtomPtr atom;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, memi);

      if (LMN_ATTR_IS_DATA(wkv[atomi])) {
        switch (LMN_ATTR_GET_VALUE(wkv[atomi])) {
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
		case INSTR_DEREFATOM:
		{
			LmnInstrVar atom1, atom2, posi;
			LMN_IMS_READ(LmnInstrVar, instr, atom1);
			LMN_IMS_READ(LmnInstrVar, instr, atom2);
			LMN_IMS_READ(LmnInstrVar, instr, posi);
			
			REF_CAST(LmnAtomPtr, wv[atom1]) = LMN_ATOM(LMN_ATOM_GET_LINK(wv[atom2], posi));
			wkv[atom1] = LMN_ATOM_GET_LINK_ATTR(wv[atom2], posi);
			break;
		}
		case INSTR_ISINT:
		{
			LmnInstrVar atomi;
			LMN_IMS_READ(LmnInstrVar, instr, atomi);

			if (LMN_ATTR_GET_VALUE(wkv[atomi]) != LMN_ATOM_INT_ATTR)
				return FALSE;
			break;
		}
		case INSTR_COPYATOM:
		{
			LmnInstrVar atom1, memi, atom2;
			LMN_IMS_READ(LmnInstrVar, instr, atom1);
			LMN_IMS_READ(LmnInstrVar, instr, memi);
			LMN_IMS_READ(LmnInstrVar, instr, atom2);

			REF_CAST(LmnAtomPtr, wv[atom1]) = REF_CAST(LmnAtomPtr, wv[atom2]);
			wkv[atom1] = wkv[atom2];
			break;
		}
    default:
      fprintf(stderr, "interpret: Unknown operation %d\n", op);
      exit(1);
    }
  }
}
