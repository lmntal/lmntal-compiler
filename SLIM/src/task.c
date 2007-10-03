/*
 * task.c
 */


#include <stdio.h>
#include "lmntal.h"
#include "rule.h"
#include "membrane.h"
#include "instruction.h"
#include "read_instr.h"
#include "vector.h"
#include "dumper.h"
#include "task.h"

/* this size should be the maximum size of 'spec' arguments */
/* Or allocated when required */
LmnWord v1[1024];
LmnWord v2[1024];

/* for value tag */
LmnByte k1[1024];
LmnByte k2[1024];

LmnWord *wt, *tv;    /* working table */
LmnByte *at, *tkv;  /* attribute table */

LmnCompiledRuleset system_ruleset;

#define SWAP(T,X,Y)       do { T t=(X); (X)=(Y); (Y)=t;} while(0)
#define REF_CAST(T,X)     (*(T*)&(X))

struct Entity {
  LmnMembrane  *mem;
  struct Entity  *next;
};

struct MemStack {
  struct Entity  *head, *tail;
} memstack;

static BOOL interpret(LmnRuleInstr instr, LmnRuleInstr *next);

static void memstack_init()
{
  memstack.head = LMN_MALLOC(struct Entity);
  memstack.head->next = NULL;
  memstack.head->mem = NULL;
  memstack.tail = memstack.head;
}

void memstack_push(LmnMembrane *mem)
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

  REF_CAST(LmnMembrane*, wt[0]) = mem;
  for (i = 0; i < ruleset->num; i++) {
    if (interpret(ruleset->rules[i]->instr, &dummy)) return TRUE;
  }
  
  return FALSE;
}

/* リンクオブジェクトの代替 */
typedef struct LinkObj {
  LmnAtomPtr ap;
  LmnLinkAttr pos;
} LinkObj;

static LmnAtomPtr get_buddy(LinkObj *linko) {
  return (LmnAtomPtr)LMN_ATOM_GET_LINK(linko->ap, linko->pos);
}

static int exec(LmnMembrane *mem)
{
  unsigned int i;
  int flag = FALSE;
/*     flag = react(mem, systemrulesets); */
  if (!flag) {
    
    for (i = 0; i < mem->rulesets.num; i++) {
      if (react_ruleset(mem, (LmnRuleSet *)vec_get(&mem->rulesets, i))) {
        flag = TRUE;
        break;
      }
    }
  }
  
  return flag;
}

void run(void)
{
  LmnMembrane *mem;

  /* Initialize for running */
  wt = v1;
  tv = v2;
  at = k1;
  tkv = k2;

  init_system_ruleset(&system_ruleset);
  memstack_init();
  
  /* make toplevel membrane */
  
  mem = lmn_mem_make();

/*   lmn_mem_dump(mem); */

  /*     lmn_mem_add_ruleset(mem, lmn_ruleset_table.entry[0]); */

  memstack_push(mem);
  /* call init atom creation rule */
  react_ruleset(mem, lmn_ruleset_table.entry[0]);

/*   lmn_mem_dump(mem); */
  
  while(!memstack_isempty()){
    LmnMembrane *mem = memstack_peek();
    if(!exec(mem)) {
      printf("system_ruleset react \n");
      if (!compiled_ruleset_react(&system_ruleset, mem)) {
        memstack_pop();
      }
    }
/*     memstack_printall(); */
  }

  lmn_mem_dump(mem);
}

/* Utility for reading data */

#define READ_DATA_ATOM(dest, attr)              \
  do {                                          \
    switch (attr) {                             \
    case LMN_ATOM_INT_ATTR:                     \
       LMN_IMS_READ(int, instr, (dest));        \
       break;                                   \
     case LMN_ATOM_DBL_ATTR:                    \
     {                                          \
        double *x;                              \
        x = LMN_MALLOC(double);                 \
        LMN_IMS_READ(double, instr, *x);        \
        (dest) = (LmnWord)x;                    \
        break;                                  \
     }                                          \
     default:                                   \
       LMN_ASSERT(FALSE);                       \
       break;                                   \
     }                                          \
   } while (0)

#define READ_CONST_DATA_ATOM(dest, attr)        \
  do {                                          \
    switch (attr) {                             \
    case LMN_ATOM_INT_ATTR:                     \
       LMN_IMS_READ(int, instr, (dest));        \
       break;                                   \
     case LMN_ATOM_DBL_ATTR:                    \
       (dest) = (LmnWord)instr;                 \
       instr += sizeof(double);                 \
       break;                                   \
     default:                                   \
       LMN_ASSERT(FALSE);                       \
       break;                                   \
     }                                          \
   } while (0)

#define READ_CMP_DATA_ATOM(attr, x, result)            \
       do {                                            \
          switch(attr) {                               \
          case LMN_ATOM_INT_ATTR:                      \
            {                                          \
              int t;                                   \
              LMN_IMS_READ(int, instr, t);             \
              (result) = ((int)(x) == t);              \
              break;                                   \
            }                                          \
          case LMN_ATOM_DBL_ATTR:                      \
            {                                          \
              double t;                                \
              LMN_IMS_READ(double, instr, t);          \
              (result) = (*(double*)(x) == t);         \
              break;                                   \
            }                                          \
          default:                                     \
            LMN_ASSERT(FALSE);                         \
            break;                                     \
          }                                            \
          } while (0)

static void print_wt(void);

static BOOL interpret(LmnRuleInstr instr, LmnRuleInstr *next)
{
  LmnInstrOp op;

  while (TRUE) {
  LOOP:;
    LMN_IMS_READ(LmnInstrOp, instr, op);
/*     fprintf(stderr, "op: %d %d\n", op, instr - start - 2); */
/*     lmn_mem_dump((LmnMembrane*)wt[0]); */
    switch (op) {
    case INSTR_SPEC:
      /* ignore spec, because wt is initially large enough. */
      instr += sizeof(LmnInstrVar)*2;
      break;
    case INSTR_INSERTCONNECTORSINNULL:
    {
        LmnInstrVar seti, num, enti;
        Vector *links; /* src list */
        HashSet *retset = LMN_MALLOC(HashSet); /* dst set */
        unsigned int i, j;
        LMN_IMS_READ(LmnInstrVar, instr, seti);
        LMN_IMS_READ(LmnInstrVar, instr, num);

        links = vec_make(num);
        for(i = 0; i < num; i++) {
          LMN_IMS_READ(LmnInstrVar, instr, enti);
          vec_push(links, (LmnWord)enti);
        }
        hashset_init(retset, num);
        
        /* main loop */
        for(i = 0; i < links->num; i++) {
          for(j = i+1; j < links->num; j++) {
            LmnWord linkid1, linkid2;
            linkid1 = vec_get(links, i);
            linkid2 = vec_get(links, j);
            if(wt[linkid2] == LMN_ATOM_GET_LINK(wt[linkid1], at[linkid1]) && 
                at[linkid2] == LMN_ATOM_GET_LINK_ATTR(wt[linkid1], at[linkid1])) {
              LmnAtomPtr eq = lmn_new_atom(LMN_UNIFY_FUNCTOR);
              /* '='アトムをはさむ */
              LMN_ATOM_SET_LINK(eq, 0, wt[linkid1]);
              LMN_ATOM_SET_LINK(eq, 1, wt[linkid2]);
              LMN_ATOM_SET_LINK_ATTR(eq, 0, at[linkid1]);
              LMN_ATOM_SET_LINK_ATTR(eq, 1, at[linkid2]);
              LMN_ATOM_SET_LINK(wt[linkid2], at[linkid2], (LmnWord)eq);
              LMN_ATOM_SET_LINK(wt[linkid1], at[linkid1], (LmnWord)eq);
              LMN_ATOM_SET_LINK_ATTR(wt[linkid2], at[linkid2], 0);
              LMN_ATOM_SET_LINK_ATTR(wt[linkid1], at[linkid1], 1);
              /* invalid link objects */
              wt[linkid1] = wt[linkid2] = at[linkid1] = at[linkid2] = 0;

              hashset_add(retset, (LmnWord)eq);
            }
          }
        }
        wt[seti] = (LmnWord)retset;
      break;
    }
    case INSTR_JUMP:
    {
      LmnInstrVar num, i, n;
      LmnJumpOffset offset;

      i = 0;
      /* atom */
      LMN_IMS_READ(LmnInstrVar, instr, num);
      for (; num--; i++) {
        LMN_IMS_READ(LmnInstrVar, instr, n);
        tv[i] = wt[n];
        tkv[i] = at[n];
      }
      /* mem */
      LMN_IMS_READ(LmnInstrVar, instr, num);
      for (; num--; i++) {
        LMN_IMS_READ(LmnInstrVar, instr, n);
        tv[i] = wt[n];
        tkv[i] = at[n];
      }
      /* vars */
      LMN_IMS_READ(LmnInstrVar, instr, num);
      for (; num--; i++) {
        LMN_IMS_READ(LmnInstrVar, instr, n);
        tv[i] = wt[n];
        tkv[i] = at[n];
      }

      LMN_IMS_READ(LmnJumpOffset, instr, offset);
      instr += offset;

      SWAP(LmnWord*, wt, tv);
      SWAP(LmnLinkAttr*, at, tkv);
      if (interpret(instr, &instr)) return TRUE;
      else {
        SWAP(LmnWord*, wt, tv);
        SWAP(LmnLinkAttr*, at, tkv);
        return FALSE;
      }
    }
    case INSTR_COMMIT:
      instr += sizeof(lmn_interned_str) + sizeof(LmnLineNum);
      break;
    case INSTR_FINDATOM:
    {
      LmnInstrVar atomi, memi;
      LmnLinkAttr attr;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnLinkAttr, instr, attr);
      
      if (LMN_ATTR_IS_DATA(attr)) {
        fprintf(stderr, "I can not find data atoms.\n");
        assert(FALSE);
      } else { /* symbol atom */
        LmnFunctor f;
        AtomSetEntry *atomlist_ent;
        LmnAtomPtr atom;
        
        LMN_IMS_READ(LmnFunctor, instr, f);
        atomlist_ent = lmn_mem_get_atomlist((LmnMembrane*)wt[memi], f);
        if (atomlist_ent) {
          at[atomi] = LMN_ATTR_MAKE_LINK(0);
          for (atom = atomlist_head(atomlist_ent);
               atom != lmn_atomset_end(atomlist_ent);
               atom = LMN_ATOM_GET_NEXT(atom)) {
            REF_CAST(LmnAtomPtr, wt[atomi]) = atom;
            if (interpret(instr, &instr)) return TRUE;
          }
        }
       return FALSE;
      }
      break;
    }
    case INSTR_LOCKMEM:
    {
      LmnInstrVar mem, atom, memn;
      LMN_IMS_READ(LmnInstrVar, instr, mem);
      LMN_IMS_READ(LmnInstrVar, instr, atom);
      LMN_IMS_READ(lmn_interned_str, instr, memn);

      wt[mem] = (LmnWord)LMN_PROXY_GET_MEM(wt[atom]);
      break;
    }
    case INSTR_ANYMEM:
    {
      LmnInstrVar mem1, mem2, memt, memn; /* dst, parent, type, name */
      LmnMembrane* mp;

      LMN_IMS_READ(LmnInstrVar, instr, mem1);
      LMN_IMS_READ(LmnInstrVar, instr, mem2);
      LMN_IMS_READ(LmnInstrVar, instr, memt);
      LMN_IMS_READ(lmn_interned_str, instr, memn);

      mp = ((LmnMembrane*)wt[mem2])->child_head;
      while (mp) {
        REF_CAST(LmnMembrane *, wt[mem1]) = mp;
        if (mp->name == memn && interpret(instr, &instr)) return TRUE;
        mp = mp->next;
      }
      return FALSE;
      break;
    }
    case INSTR_NMEMS:
    {
      LmnInstrVar memi, nmems;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnInstrVar, instr, nmems);

      if(!lmn_mem_nmems((LmnMembrane*)wt[memi], nmems)) {
        return FALSE;
      }
      break;
    }
    case INSTR_NORULES:
    {
      LmnInstrVar memi;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      if(((LmnMembrane *)wt[memi])->rulesets.num) return FALSE;
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
      at[atomi] = attr;
      if (LMN_ATTR_IS_DATA(attr)) {
        READ_DATA_ATOM(wt[atomi], attr);
      } else { /* symbol atom */
        LmnFunctor f;
      	LMN_IMS_READ(LmnFunctor, instr, f);
      	ap = lmn_new_atom(f);
      	lmn_mem_push_atom((LmnMembrane*)wt[memi], ap);
      	REF_CAST(LmnAtomPtr, wt[atomi]) = ap;
	      if (LMN_IS_PROXY_FUNCTOR(f)) {
      	  LMN_ATOM_SET_LINK(ap, 2, wt[memi]);
      	}
      }
      break;
    }
    case INSTR_NATOMS:
    {
      LmnInstrVar memi, natoms;
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnInstrVar, instr, natoms);
      if(!lmn_mem_natoms((LmnMembrane*)wt[memi], natoms)) {
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

      if (LMN_ATTR_IS_DATA(at[atom])) {
        wt[link] = wt[atom];
        at[link] = at[atom];
      } else { /* link to atom */
        REF_CAST(LmnAtomPtr, wt[link]) = LMN_ATOM(wt[atom]);
        at[link] = LMN_ATTR_MAKE_LINK(n);
      }
      break;
    }
    case INSTR_UNIFYLINKS:
    {
      LmnInstrVar link1, link2, mem;
      
      LMN_IMS_READ(LmnInstrVar, instr, link1);
      LMN_IMS_READ(LmnInstrVar, instr, link2);
      LMN_IMS_READ(LmnInstrVar, instr, mem);

      if (LMN_ATTR_IS_DATA(at[link1])) {
        if (LMN_ATTR_IS_DATA(at[link2])) { /* 1, 2 are data */
          lmn_mem_link_data_atoms((LmnMembrane *)wt[mem], wt[link1], at[link1], wt[link2], at[link2]);
        }
        else { /* 1 is data */
          LMN_ATOM_SET_LINK(LMN_ATOM(wt[link2]), LMN_ATTR_GET_VALUE(at[link2]), wt[link1]);
          LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wt[link2]),
                                 LMN_ATTR_GET_VALUE(at[link2]),
                                 at[link1]);
        }
      }
      else if (LMN_ATTR_IS_DATA(at[link2])) { /* 2 is data */
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[link1]), LMN_ATTR_GET_VALUE(at[link1]), wt[link2]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wt[link1]),
                               LMN_ATTR_GET_VALUE(at[link1]),
                               at[link2]);
      }
      else { /* 1, 2 are symbol atom */
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[link1]), LMN_ATTR_GET_VALUE(at[link1]), wt[link2]);
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[link2]), LMN_ATTR_GET_VALUE(at[link2]), wt[link1]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wt[link1]),
                               LMN_ATTR_GET_VALUE(at[link1]),
                               at[link2]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wt[link2]),
                               LMN_ATTR_GET_VALUE(at[link2]),
                               at[link1]);
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

      lmn_mem_newlink((LmnMembrane *)wt[memi],
                      wt[atom1],
                      at[atom1],
                      pos1,
                      wt[atom2],
                      at[atom2],
                      pos2);
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

      ap = LMN_ATOM(LMN_ATOM_GET_LINK(wt[atom2], pos2));
      attr = LMN_ATOM_GET_LINK_ATTR(wt[atom2], pos2);

      if(LMN_ATTR_IS_DATA(at[atom1]) && LMN_ATTR_IS_DATA(attr)) {
        #ifdef DEBUG
        fprintf(stderr, "Two data atoms are connected each other.\n");
        #endif 
      }
      else if (LMN_ATTR_IS_DATA(at[atom1])) {
        LMN_ATOM_SET_LINK(ap, attr, wt[atom1]);
        LMN_ATOM_SET_LINK_ATTR(ap, attr, at[atom1]);
      }
      else if (LMN_ATTR_IS_DATA(attr)) {
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[atom1]), pos1, (LmnWord)ap);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wt[atom1]), pos1, attr);
      }
      else {
        LMN_ATOM_SET_LINK(ap, attr, wt[atom1]);
        LMN_ATOM_SET_LINK_ATTR(ap, attr, pos1);
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[atom1]), pos1, (LmnWord)ap);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wt[atom1]), pos1, attr);
      }
      break;
    }
    case INSTR_INHERITLINK:
    {
      LmnInstrVar atomi, posi, linki, memi;
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, posi);
      LMN_IMS_READ(LmnInstrVar, instr, linki);
      LMN_IMS_READ(LmnInstrVar, instr, memi);

      if(LMN_ATTR_IS_DATA(at[atomi]) && LMN_ATTR_IS_DATA(at[linki])) {
        #ifdef DEBUG
        fprintf(stderr, "Two data atoms are connected each other.\n");
        #endif
      }
      else if(LMN_ATTR_IS_DATA(at[atomi])) {
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[linki]), at[linki], wt[atomi]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wt[linki]), at[linki], at[atomi]);
      }
      else if(LMN_ATTR_IS_DATA(at[linki])) {
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[atomi]), posi, wt[linki]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wt[atomi]), posi, at[linki]);
      }
      else {
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[atomi]), posi, wt[linki]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wt[atomi]), posi, at[linki]);
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[linki]), at[linki], wt[atomi]);
        LMN_ATOM_SET_LINK_ATTR(LMN_ATOM(wt[linki]), at[linki], posi);
      }
      break;
    }
    case INSTR_GETLINK:
    {
      LmnInstrVar linki, atomi, posi;
      LMN_IMS_READ(LmnInstrVar, instr, linki);
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, posi);

      wt[linki] = LMN_ATOM_GET_LINK(wt[atomi], posi);
      at[linki] = LMN_ATOM_GET_LINK_ATTR(wt[atomi], posi);
      break;
    }
    case INSTR_UNIFY:
    {
      LmnInstrVar atom1, pos1, atom2, pos2, memi;
    
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, pos1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);
      LMN_IMS_READ(LmnInstrVar, instr, pos2);
      LMN_IMS_READ(LmnInstrVar, instr, memi);

      lmn_mem_unify_atom_args((LmnMembrane *)wt[memi],
                              LMN_ATOM(wt[atom1]),
                              pos1,
                              LMN_ATOM(wt[atom2]),
                              pos2);
      break;
    }
    case INSTR_PROCEED:
      *next = instr;
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
      lmn_mem_add_child_mem((LmnMembrane*)wt[parentmemi], mp);
      REF_CAST(LmnMembrane*, wt[newmemi]) = mp;
      memstack_push(mp);
      break;
    }
    case INSTR_REMOVEATOM:
    {
      LmnInstrVar atomi, memi;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, memi);

      if (!LMN_ATTR_IS_DATA(at[atomi])) {
        lmn_mem_remove_atom((LmnMembrane*)wt[memi], (LmnAtomPtr)wt[atomi]);
      }
      break;
    }
    case INSTR_FREEATOM:
    {
      LmnInstrVar atomi;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);

      if (LMN_ATTR_IS_DATA(at[atomi])) {
        switch (at[atomi]) {
        case LMN_ATOM_INT_ATTR:
          break;
        case LMN_ATOM_DBL_ATTR:
          LMN_FREE((double*)wt[atomi]);
          break;
        default:
          LMN_ASSERT(FALSE);
          break;
        }
      }
      else { /* symbol atom */
        lmn_delete_atom((LmnAtomPtr)wt[atomi]);
      }
      break;
    }
    case INSTR_REMOVEMEM:
    {
      LmnInstrVar memi;
      LmnMembrane *mp;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      instr += sizeof(LmnInstrVar); /* ingnore parent */
      
      mp = (LmnMembrane*)wt[memi];
      if(mp->parent->child_head == mp) mp->parent->child_head = mp->next;
      if(mp->prev) mp->prev->next = mp->next;
      if(mp->next) mp->next->prev = mp->prev;
      mp->parent = NULL;
      break;
    }
    case INSTR_FREEMEM:
    {
      LmnInstrVar memi;
      LmnMembrane *mp;

      LMN_IMS_READ(LmnInstrVar, instr, memi);

      mp = (LmnMembrane*)wt[memi];
      lmn_mem_free(mp);
      break;
    }
    case INSTR_LOADRULESET:
    {
      LmnInstrVar memi;
      LmnRulesetId id;
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnRulesetId, instr, id);
      
      lmn_mem_add_ruleset((LmnMembrane*)wt[memi], LMN_RULESET_ID(id));
      break;
    }
    case INSTR_DEREFATOM:
    {
      LmnInstrVar atom1, atom2, posi;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);
      LMN_IMS_READ(LmnInstrVar, instr, posi);
            
      REF_CAST(LmnAtomPtr, wt[atom1]) = LMN_ATOM(LMN_ATOM_GET_LINK(wt[atom2], posi));
      at[atom1] = LMN_ATOM_GET_LINK_ATTR(wt[atom2], posi);
      break;
    }
    case INSTR_DEREF:
    {
      LmnInstrVar atom1, atom2, pos1, pos2;
      LmnAtomPtr ap;
      LmnByte attr;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);
      LMN_IMS_READ(LmnInstrVar, instr, pos1);
      LMN_IMS_READ(LmnInstrVar, instr, pos2);

      ap = LMN_ATOM(LMN_ATOM_GET_LINK(wt[atom2], pos1));
      attr = LMN_ATOM_GET_LINK_ATTR(wt[atom2], pos1);
      at[atom1] = attr;
      if (LMN_ATTR_IS_DATA(at[atom2])) {
#ifdef DEBUG
        fprintf(stderr, "Can't deref from data atom.\n");
#endif
      }
      else if (LMN_ATTR_IS_DATA(attr)) {
        switch (attr) {
        case LMN_ATOM_INT_ATTR:
          {
            REF_CAST(int, wt[atom1]) = (int)ap;
            break;
          }
        case LMN_ATOM_DBL_ATTR:
          {
            REF_CAST(double*, wt[atom1]) = (double *)ap;
            break;
          }
        default:
          LMN_ASSERT(FALSE);
        }
      }
      else {
        if (attr != pos2)
          return FALSE;
        REF_CAST(LmnAtomPtr, wt[atom1]) = ap;
      }
      break;
    }
    case INSTR_FUNC:
    {
      LmnInstrVar atomi;
      LmnFunctor f;
      LmnLinkAttr attr;
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnLinkAttr, instr, attr);

      if (LMN_ATTR_IS_DATA(at[atomi]) == LMN_ATTR_IS_DATA(attr)) {
        if(LMN_ATTR_IS_DATA(at[atomi])) {
          BOOL eq;
          if(at[atomi] != attr) return FALSE; /* comp attr */
          READ_CMP_DATA_ATOM(attr, wt[atomi], eq);
          if (!eq) return FALSE;
        }
        else /* symbol atom */
          {
            LMN_IMS_READ(LmnFunctor, instr, f);
            if (LMN_ATOM_GET_FUNCTOR(LMN_ATOM(wt[atomi])) != f) {
              return FALSE;
            }
          }
      }
      else { /* LMN_ATTR_IS_DATA(at[atomi]) != LMN_ATTR_IS_DATA(attr) */
        return FALSE;
      }
      fprintf(stderr, "instr_func successed\n");
      break;
    }
    case INSTR_NOTFUNC:
    {
      LmnInstrVar atomi;
      LmnFunctor f;
      LmnLinkAttr attr;
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnLinkAttr, instr, attr);

      if (LMN_ATTR_IS_DATA(at[atomi]) == LMN_ATTR_IS_DATA(attr)) {
        if(LMN_ATTR_IS_DATA(at[atomi])) {
          if(at[atomi] == attr) {
            BOOL eq;
            READ_CMP_DATA_ATOM(attr, wt[atomi], eq);
            if (eq) return FALSE;
          }
        }
        else { /* symbol atom */
          LMN_IMS_READ(LmnFunctor, instr, f);
          if (LMN_ATOM_GET_FUNCTOR(LMN_ATOM(wt[atomi])) == f) return FALSE;
        }
      }
      break;
    }
    case INSTR_ISGROUND:
    {
      /* TODO: groundをたどる際にdataアトムを考慮する */
      LmnInstrVar funci, srclisti, avolisti;
      unsigned int i, atom_num;
      Vector *srcvec, *avovec, rootvec; /* 要素はリンク */
      Vector visited_root;
      Vector links;           /* 再帰用スタック */
      HashSet avoset;
      HashSet visited_atoms;  /* 走査済みアトム */

      LMN_IMS_READ(LmnInstrVar, instr, funci);
      LMN_IMS_READ(LmnInstrVar, instr, srclisti);
      LMN_IMS_READ(LmnInstrVar, instr, avolisti);

      srcvec = (Vector*) wt[srclisti];
      
      avovec = (Vector*) wt[avolisti];
      hashset_init(&avoset, 16);
      for(i = 0; i < avovec->num; i++) {
        hashset_add(&avoset, vec_get(avovec, i));
      }

      hashset_init(&visited_atoms, 256);
      vec_init(&links, 16);
      vec_push(&links, (LmnWord)vec_get(srcvec, 0));
      vec_init(&rootvec, 16);
      vec_init(&visited_root, 16);
      atom_num = 0;

      for (i = 0; i < srcvec->num; i++) {
        LinkObj *o = LMN_MALLOC(LinkObj);
        LinkObj *src = (LinkObj *)vec_get(srcvec, i);
        o->ap = LMN_ATOM_GET_LINK(src->ap, src->pos);
        o->pos = LMN_ATOM_GET_LINK_ATTR(src->ap, src->pos);
        vec_push(&rootvec, (HashKeyType)o);
      }

      for(i = 0; i < srcvec->num; i++) {
        vec_push(&visited_root, FALSE);
      }

      /* method: isGround */
      while(links.num!=0) {
        LinkObj *linko = (LinkObj *)vec_pop(&links);

	      for(i = 0; i < rootvec.num; i++) {
          LinkObj *o = vec_get(&rootvec, i);
          if (linko->ap == o->ap && linko->pos == o->pos) {
            vec_set(&visited_root, i, TRUE);
            /*continue;*/
            goto NEXT;
	        }
	      }

        if(hashset_contains(&visited_atoms, (HashKeyType)linko->ap)) /* 走査済みアトム */ 
          continue;
        if(hashset_contains(&avoset, (LmnWord)get_buddy(linko))) /* 出現禁止リンク */
          return FALSE;
        if(LMN_ATOM_GET_FUNCTOR(linko->ap) == LMN_IN_PROXY_FUNCTOR) /* 膜を横断 */
          return FALSE;

        atom_num++;
        hashset_add(&visited_atoms, (LmnWord)linko->ap);
        for(i = 0; i < LMN_ATOM_GET_ARITY(linko->ap); i++) {
          LinkObj *nextlinko;
          nextlinko = LMN_MALLOC(LinkObj);
          nextlinko->ap = (LmnAtomPtr)LMN_ATOM_GET_LINK(linko->ap, i);
          nextlinko->pos = LMN_ATTR_GET_VALUE(LMN_ATOM_GET_LINK_ATTR(linko->ap, i));
          vec_push(&links, (LmnWord)nextlinko); 
        }
      NEXT:;
      } /* while stack */

      for(i = 0; i < visited_root.num; i++) {
        if(!vec_get(&visited_root, i)) return FALSE;
      }
      /* method: isGround */

      wt[funci] = (LmnWord)atom_num;
      at[funci] = LMN_ATOM_INT_ATTR;
      printf("instr_isground: success\n");
      break;
    }
    case INSTR_COPYGROUND: {
      int i;
      LmnInstrVar dstlist, srclist, memi;
      Vector *srcvec;
      Vector stack;
      SimpleHashtbl atoms;
      
      LMN_IMS_READ(LmnInstrVar, instr, dstlist);
      LMN_IMS_READ(LmnInstrVar, instr, srclist);
      LMN_IMS_READ(LmnInstrVar, instr, memi);

      srcvec = (Vector *)wt[srclist];

      vec_init(&stack, 16);
      hashtbl_init(&atoms, 256);
      while(stack.num!=0) {
        LinkObj *lo = vec_pop(&stack);
        
        /* indexof */
        for(i = 0; i < srcvec->num; i++){
          LinkObj *tmp = (LinkObj *)vec_get(srcvec, i);
          if(lo->ap == tmp->ap && lo->pos == tmp->pos) goto CONT;
        }

        if(hashtbl_contains(&atoms, (HashKeyType)lo->ap)) {
        } else {
          
        }
        CONT:;
      }
      break;
    }
    case INSTR_REMOVEGROUND:
    {
      /* TODO: groundをたどる際にdataアトムを考慮する */
      LmnInstrVar listi, memi;
      Vector *srclinks;
      Vector links;
      HashSet *atoms = LMN_MALLOC(HashSet);
      LinkObj *linko, *nextlinko;
      int srci;
      unsigned int i;
      LMN_IMS_READ(LmnInstrVar, instr, listi);
      LMN_IMS_READ(LmnInstrVar, instr, memi);

      srclinks = (Vector *)wt[listi];

      vec_init(&links, 16);
      vec_push(&links, vec_get(srclinks, 0));
      hashset_init(atoms, 256);
      
      /* method: removeGround */
      while(links.num!=0) {
        LmnAtomPtr ap;
        linko = (LinkObj *)vec_pop(&links);
        srci = vec_indexof(srclinks, LMN_ATOM_GET_LINK(linko->ap, linko->pos));
        if(srci >= 0) /* 根に到達 */
          continue;
        if(hashset_contains(atoms, (HashKeyType)linko->ap)) /* 走査済み */
          continue;
        hashset_add(atoms, (LmnWord)linko->ap);
        for(i = 0; i < LMN_ATOM_GET_ARITY(linko->ap); i++) {
          if(i == linko->pos) /* 親へのリンク */
            continue;
          nextlinko = LMN_MALLOC(LinkObj);
          nextlinko->ap = (LmnAtomPtr)LMN_ATOM_GET_LINK(linko->ap, i);
          nextlinko->pos = LMN_ATOM_GET_LINK_ATTR(linko->ap, i);
          vec_push(&links, (LmnWord)nextlinko); 
        }
        lmn_mem_remove_atom((LmnMembrane*)wt[memi], linko->ap);
        lmn_delete_atom(linko->ap);
        /* アトムは親膜への参照を持たない＆アトムスタックがない */
      }
      /* method: removeGround */
/*       lmn_mem_dump((LmnMembrane*)wt[0]); */
      break;
    }
    case INSTR_FREEGROUND:
    {
      LmnInstrVar listi;
      LMN_IMS_READ(LmnInstrVar, instr, listi);
      /* 
       * Java版では何もしていない
       * TODO: GC相当の処理はREMOVEGROUNDで行う？
       */
      break;
    }
    case INSTR_ISUNARY:
    {
      LmnInstrVar atomi;
      LMN_IMS_READ(LmnInstrVar, instr, atomi);

      if(!LMN_ATTR_IS_DATA(at[atomi]) &&
         LMN_ATOM_GET_ARITY((LmnAtomPtr)wt[atomi]) != 1) return FALSE;
      break;
    }
    case INSTR_ISINT:
    {
      LmnInstrVar atomi;
      LMN_IMS_READ(LmnInstrVar, instr, atomi);

      if (at[atomi] != LMN_ATOM_INT_ATTR)
        return FALSE;
      break;
    }
    case INSTR_ISFLOAT:
    {
      LmnInstrVar atomi;
      LMN_IMS_READ(LmnInstrVar, instr, atomi);

      if(at[atomi] != LMN_ATOM_DBL_ATTR)
        return FALSE;
      break;
    }
    case INSTR_ISINTFUNC:
    {
      LmnInstrVar funci;
      LMN_IMS_READ(LmnInstrVar, instr, funci);

      if(at[funci] != LMN_ATOM_INT_ATTR)
        return FALSE;
      break;
    }
    case INSTR_ISFLOATFUNC:
    {
      LmnInstrVar funci;
      LMN_IMS_READ(LmnInstrVar, instr, funci);

      if(at[funci] != LMN_ATOM_DBL_ATTR)
        return FALSE;
      break;
    }
    case INSTR_COPYATOM:
    {
      LmnInstrVar atom1, memi, atom2;

      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      at[atom1] = at[atom2];
      if (LMN_ATTR_IS_DATA(at[atom2])) {
        switch (at[atom2]) {
        case LMN_ATOM_INT_ATTR:
          wt[atom1] = wt[atom2];
          break;
        case LMN_ATOM_DBL_ATTR:
          wt[atom1] = (LmnWord)LMN_MALLOC(double);
          *(double *)wt[atom1] = *(double*)wt[atom2];
          break;
        default:
          LMN_ASSERT(FALSE);
          break;
        }
      } else { /* symbol atom */
        LmnFunctor f = LMN_ATOM_GET_FUNCTOR(LMN_ATOM(wt[atom2]));
        wt[atom1] = (LmnWord)lmn_new_atom(f);
        memcpy((void *)wt[atom1], (void *)wt[atom2], LMN_WORD_BYTES*LMN_ATOM_WORDS(f));
      }
      break;
    }
    case INSTR_EQATOM:
    {
      LmnInstrVar atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      /* atom1 or atom2 is a symbol atom */
      if (LMN_ATTR_IS_DATA(at[atom1]) ||
          LMN_ATTR_IS_DATA(at[atom2]) ||
          LMN_ATOM(wt[atom1]) != LMN_ATOM(wt[atom2]))
        return FALSE;
      break;
    }
    case INSTR_NEQATOM:
    {
      LmnInstrVar atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);
 
     /* atom1 or atom2 is a symbol atom */
      if (!(LMN_ATTR_IS_DATA(at[atom1]) ||
            LMN_ATTR_IS_DATA(at[atom2]) ||
            LMN_ATOM(wt[atom1]) != LMN_ATOM(wt[atom2])))
        return FALSE;
      break;
    }
    case INSTR_EQMEM:
    {
      LmnInstrVar mem1, mem2;

      LMN_IMS_READ(LmnInstrVar, instr, mem1);
      LMN_IMS_READ(LmnInstrVar, instr, mem2);
      if (wt[mem1] != wt[mem2]) return FALSE;
      break;
    }
    case INSTR_NEQMEM:
    {
      LmnInstrVar mem1, mem2;
      LMN_IMS_READ(LmnInstrVar, instr, mem1);
      LMN_IMS_READ(LmnInstrVar, instr, mem2);

      if(wt[mem1] == wt[mem2]) return FALSE;
      break;
    }
    case INSTR_NEWLIST:
    {
      LmnInstrVar listi;
      LMN_IMS_READ(LmnInstrVar, instr, listi);
      wt[listi] = (LmnWord)vec_make_default();
      /* TODO: interpretで再帰して開放する */
      break;
    }
    case INSTR_ADDTOLIST:
    {
      LmnInstrVar listi, srci;
      LinkObj *linko = LMN_MALLOC(LinkObj);
      LMN_IMS_READ(LmnInstrVar, instr, listi);
      LMN_IMS_READ(LmnInstrVar, instr, srci);
      /* おそらくADDTOLISTで追加される要素の種類はリンクのみ */
      linko->ap = wt[srci];
      linko->pos = (at[srci]);
      vec_push((Vector*)wt[listi], (LmnWord)linko);
      break;
    }
    case INSTR_IADD:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      REF_CAST(int, wt[dstatom]) = (int)wt[atom1] + (int)wt[atom2];
      at[dstatom] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_ISUB:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      REF_CAST(int, wt[dstatom]) = (int)wt[atom1] - (int)wt[atom2];
      at[dstatom] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_IMUL:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      REF_CAST(int, wt[dstatom]) = (int)wt[atom1] * (int)wt[atom2];
      at[dstatom] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_IDIV:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      REF_CAST(int, wt[dstatom]) = (int)wt[atom1] / (int)wt[atom2];
      at[dstatom] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_ILT:
    {
      LmnInstrVar atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] < (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_ILE:
    {
      LmnInstrVar atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] <= (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_IGT:
    {
      LmnInstrVar atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] > (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_IGE:
    {
      LmnInstrVar atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] >= (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_IEQ:
    {
      LmnInstrVar atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] == (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_INE:
    {
      LmnInstrVar atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] != (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_ALLOCATOM:
    {
      LmnInstrVar atomi;
      LmnAtomPtr ap;
      LmnLinkAttr attr;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnLinkAttr, instr, attr);
      at[atomi] = attr;
      if (LMN_ATTR_IS_DATA(attr)) {
        READ_DATA_ATOM(wt[atomi], attr);
      } else { /* symbol atom */
        LmnFunctor f;
        fprintf(stderr, "symbol atom can't be created in GUARD\n");
        LMN_IMS_READ(LmnFunctor, instr, f);
        REF_CAST(LmnAtomPtr, wt[atomi]) = ap;
      }
      break;
    }
    case INSTR_ALLOCATOMINDIRECT:
    {
      LmnInstrVar atomi;
      LmnFunctor funci;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, funci);

      if (LMN_ATTR_IS_DATA(at[funci])) {
        switch (at[funci]) {
        case LMN_ATOM_INT_ATTR:
          {
            wt[atomi] = wt[funci];
            break;
          }
        case LMN_ATOM_DBL_ATTR:
          {
            REF_CAST(double*, wt[atomi]) = LMN_MALLOC(double);
            *(double*) wt[atomi] = *(double *) wt[funci];
            break;
          }
        default:
          assert(FALSE);
          break;
        }
        at[atomi] = at[funci];
      } else { /* symbol atom */
        fprintf(stderr, "symbol atom can't be created in GUARD\n");
      }
      break;
    }
    case INSTR_SAMEFUNC:
    {
      LmnInstrVar atom1, atom2;

      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      if(LMN_ATTR_IS_DATA(at[atom1]) == LMN_ATTR_IS_DATA(at[atom2])) {
        if (LMN_ATTR_IS_DATA(at[atom1])) {
          if(at[atom1] != at[atom2]) { /* comp attr */
            return FALSE;
          }
          /* TODO: double*などポインタの場合は値がコピーされている場合がある */
          else if(wt[atom1] != wt[atom2]) { /* comp value */
            return FALSE;
          }
        }
        else if(LMN_ATOM_GET_FUNCTOR((LmnAtomPtr)wt[atom1]) != LMN_ATOM_GET_FUNCTOR((LmnAtomPtr)wt[atom2])) {
          return FALSE;
        }
      }
      else { /* LMN_ATTR_IS_DATA(at[atom1] != LMN_ATTR_IS_DATA(at[atom2]) */
        return FALSE;
      }
      break;
    }
    case INSTR_GETFUNC:
    {
      LmnInstrVar funci, atomi;

      LMN_IMS_READ(LmnFunctor, instr, funci);
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      if(LMN_ATTR_IS_DATA(at[atomi])) {
        wt[funci]=wt[atomi];
      }
      else {
        REF_CAST(LmnFunctor, wt[funci]) = LMN_ATOM_GET_FUNCTOR(wt[atomi]);
      }
      at[funci] = at[atomi];

      break;
    }
    case INSTR_PRINTINSTR:
    {
      char c;
      
      fprintf(stderr, "instr:");
      while (TRUE) {
        LMN_IMS_READ(char, instr, c);
        if (!c) break;
        fprintf(stderr, "%c", c);
      }
      goto LOOP;
    }
    case INSTR_SETMEMNAME:
    {
      LmnInstrVar memi;
      lmn_interned_str name;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(lmn_interned_str, instr, name);
      ((LmnMembrane *)wt[memi])->name = name;
      break;
    }
    case INSTR_COPYRULES:
    {
      LmnInstrVar destmemi, srcmemi;
      unsigned int i;
      struct Vector *v;
      
      LMN_IMS_READ(LmnInstrVar, instr, destmemi);
      LMN_IMS_READ(LmnInstrVar, instr, srcmemi);
      v = &((LmnMembrane *)wt[srcmemi])->rulesets;
      for (i = 0; i< v->num; i++) {
        lmn_mem_add_ruleset((LmnMembrane *)wt[destmemi], (LmnRuleSet *)vec_get(v, i));
      }
      break;
    }
    case INSTR_REMOVEPROXIES:
    {
      LmnInstrVar memi;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      lmn_mem_remove_proxies((LmnMembrane *)wt[memi]);
      break;
    }
    case INSTR_INSERTPROXIES:
    {
      LmnInstrVar parentmemi, childmemi;

      LMN_IMS_READ(LmnInstrVar, instr, parentmemi);
      LMN_IMS_READ(LmnInstrVar, instr, childmemi);
      lmn_mem_insert_proxies((LmnMembrane *)wt[parentmemi], (LmnMembrane *)wt[childmemi]);
      break;
    }
    case INSTR_REMOVETOPLEVELPROXIES:
    {
      LmnInstrVar memi;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      lmn_mem_remove_toplevel_proxies((LmnMembrane *)wt[memi]);
      break;
    }
    case INSTR_DEREFFUNC:
    {
      LmnInstrVar funci, atomi, pos;
      LmnLinkAttr attr;
      
      LMN_IMS_READ(LmnInstrVar, instr, funci);
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnLinkAttr, instr, pos);

      attr = LMN_ATOM_GET_LINK_ATTR(LMN_ATOM(wt[atomi]), pos);
      if (LMN_ATTR_IS_DATA(attr)) {
        wt[funci] = LMN_ATOM_GET_LINK(LMN_ATOM(wt[atomi]), pos);
      }
      else { /* symbol atom */
        wt[funci] = LMN_ATOM_GET_FUNCTOR(LMN_ATOM_GET_LINK(LMN_ATOM(wt[atomi]), pos));
      }
      at[funci] = attr;
      break;
    }
    case INSTR_LOADFUNC:
    {
      LmnInstrVar funci;
      LmnLinkAttr attr;

      LMN_IMS_READ(LmnFunctor, instr, funci);
      LMN_IMS_READ(LmnInstrVar, instr, attr);
      at[funci] = attr;
      if(LMN_ATTR_IS_DATA(attr)) {
        READ_CONST_DATA_ATOM(wt[funci], attr);
      }
      else {
        LmnFunctor f;
        
        LMN_IMS_READ(LmnFunctor, instr, f);
        wt[funci] = (LmnWord)f;
      }
      break;
    }
    case INSTR_ADDATOM:
    {
      LmnInstrVar memi, atomi;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      lmn_mem_push_atom((LmnMembrane *)wt[memi], LMN_ATOM(wt[atomi]));
      break;
    }
    case INSTR_MOVECELLS:
    {
      LmnInstrVar destmemi, srcmemi;

      LMN_IMS_READ(LmnInstrVar, instr, destmemi);
      LMN_IMS_READ(LmnInstrVar, instr, srcmemi);
      lmn_mem_move_cells((LmnMembrane *)wt[destmemi], (LmnMembrane *)wt[srcmemi]);
      break;
    }
    case INSTR_REMOVETEMPORARYPROXIES:
    {
      LmnInstrVar memi;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      lmn_mem_remove_temporary_proxies((LmnMembrane *)wt[memi]);
      break;
    }
    case INSTR_NFREELINKS:
    {
      LmnInstrVar memi, count;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnInstrVar, instr, count);

      if (!lmn_mem_nfreelinks((LmnMembrane *)wt[memi], count)) return FALSE;
      break;
    }
    case INSTR_COPYCELLS:
    {
      LmnInstrVar mapi, destmemi, srcmemi;

      LMN_IMS_READ(LmnInstrVar, instr, mapi);
      LMN_IMS_READ(LmnInstrVar, instr, destmemi);
      LMN_IMS_READ(LmnInstrVar, instr, srcmemi);
      wt[mapi] = (LmnWord)lmn_mem_copy_cells((LmnMembrane *)wt[destmemi],
                                             (LmnMembrane *)wt[srcmemi]);
      break;
    }
    default:
      fprintf(stderr, "interpret: Unknown operation %d\n", op);
      exit(1);
    }
    #ifdef DEBUG
    print_wt();
    #endif
  }
}

static void print_wt(void)
{
  unsigned int i;
  unsigned int end = 16;
  
  fprintf(stderr, " wt: [");
  for (i = 0; i < end; i++) {
    if (i>0) fprintf(stderr, ", ");
    fprintf(stderr, "%lu", wt[i]);
  }
  fprintf(stderr, "]");
  fprintf(stderr, "\n");
  fprintf(stderr, " at: [");
  for (i = 0; i < end; i++) {
    if (i>0) fprintf(stderr, ", ");
    fprintf(stderr, "%u", at[i]);
  }
  fprintf(stderr, "]");
  fprintf(stderr, "\n");
}
