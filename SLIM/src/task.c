/*
 * task.c
 */


#include <stdio.h>
#include "lmntal.h"
#include "rule.h"
#include "membrane.h"
#include "instruction.h"
#include "vector.h"
#include "dumper.h"
#include "task.h"
#include "atom.h"
#include "vector.h"

LmnWord *wt; /* variable vector used in interpret */
LmnByte *at; /* attribute vector */
unsigned int wt_size;

LmnCompiledRuleset system_ruleset;

#define SWAP(T,X,Y)       do { T t=(X); (X)=(Y); (Y)=t;} while(0)
#define READ_VAL(T,I,X)      ((X)=*(T*)(I), I+=sizeof(T))

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

static int memstack_isempty()
{
  return memstack.head->next==NULL;
}

static void memstack_destroy()
{
  LMN_ASSERT(memstack_isempty());
  LMN_FREE(memstack.head);
}

void memstack_push(LmnMembrane *mem)
{
  struct Entity *ent = LMN_MALLOC(struct Entity);
  ent->mem = mem;
  ent->next = memstack.head->next;
  memstack.head->next = ent;
  mem->is_activated = TRUE;
}

static LmnMembrane* memstack_pop()
{
  struct Entity *ent = memstack.head->next;
  LmnMembrane *mem = ent->mem;
  memstack.head->next = ent->next;
  mem->is_activated = FALSE;
  LMN_FREE(ent);
  return mem;
}

static LmnMembrane* memstack_peek()
{
  return memstack.head->next->mem;
}

/* DEBUG: */
/* static void memstack_printall() */
/* { */
/*   struct Entity *ent; */
/*   for(ent = memstack.head; ent!=NULL; ent = ent->next){ */
/*     if(ent->mem!=NULL)printf("%d ", ent->mem->name); */
/*   } */
/*   printf("\n"); */
/* } */

static BOOL react_ruleset(LmnMembrane *mem, LmnRuleSet *ruleset)
{
  int i;
  LmnRuleInstr dummy;

  wt[0] = (LmnWord)mem;
  for (i = 0; i < ruleset->num; i++) {
    if (interpret(ruleset->rules[i]->instr, &dummy)) return TRUE;
  }
  
  return FALSE;
}

/* リンクオブジェクトの代替 */
typedef struct LinkObj {
  LmnWord ap;
  LmnLinkAttr pos;
} LinkObj;

static LinkObj *LinkObj_make(LmnWord ap, LmnLinkAttr pos) {
  LinkObj* ret = LMN_MALLOC(LinkObj);
  ret->ap = ap;
  ret->pos = pos;
  return ret;
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
  compiled_ruleset_init(&system_ruleset);
  init_system_ruleset(&system_ruleset);
  memstack_init();
  wt_size = 1024;
  wt = LMN_NALLOC(LmnWord, wt_size);
  at = LMN_NALLOC(LmnByte, wt_size);

  /* make toplevel membrane */
  mem = lmn_mem_make();
  memstack_push(mem);
  /* call init atom creation rule */
  react_ruleset(mem, lmn_ruleset_table.entry[0]);

  while(!memstack_isempty()){
    LmnMembrane *mem = memstack_peek();
    LmnMembrane *m;
    if(!exec(mem)) {
      if (!compiled_ruleset_react(&system_ruleset, mem)) {
        m = memstack_pop();
      }
    }
  }

  memstack_destroy();

  /* 後始末 */
  lmn_dump_cell(mem);
  lmn_mem_drop(mem);
  lmn_mem_free(mem);
  compiled_ruleset_destroy(&system_ruleset);
  LMN_FREE(wt);
  LMN_FREE(at);
  free_atom_memory_pools();
}

/* Utility for reading data */

#define READ_DATA_ATOM(dest, attr)              \
  do {                                          \
    switch (attr) {                             \
    case LMN_INT_ATTR:                     \
       READ_VAL(int, instr, (dest));        \
       break;                                   \
     case LMN_DBL_ATTR:                    \
     {                                          \
        double *x;                              \
        x = LMN_MALLOC(double);                 \
        READ_VAL(double, instr, *x);        \
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
    case LMN_INT_ATTR:                     \
       READ_VAL(int, instr, (dest));        \
       break;                                   \
     case LMN_DBL_ATTR:                    \
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
          case LMN_INT_ATTR:                      \
            {                                          \
              int t;                                   \
              READ_VAL(int, instr, t);             \
              (result) = ((int)(x) == t);              \
              break;                                   \
            }                                          \
          case LMN_DBL_ATTR:                      \
            {                                          \
              double t;                                \
              READ_VAL(double, instr, t);          \
              (result) = (*(double*)(x) == t);         \
              break;                                   \
            }                                          \
          default:                                     \
            LMN_ASSERT(FALSE);                         \
            break;                                     \
          }                                            \
          } while (0)

/* DEBUG: */
/* static void print_wt(void); */

/* mem != NULL ならば memにUNIFYを追加、そうでなければ
   UNIFYは膜に所属しない */
static HashSet *insertconnectors(LmnMembrane *mem, Vector *links)
{
  unsigned int i, j;
  HashSet *retset = hashset_make(8);
  /* EFFICIENCY: retsetがHash Setである意味は?　ベクタでいいのでは？
     中間命令でセットを使うように書かれている */
  
  for(i = 0; i < links->num; i++) {
    LmnWord linkid1 = vec_get(links, i);
    if (LMN_ATTR_IS_DATA(at[linkid1])) continue;
    for(j = i+1; j < links->num; j++) {
      LmnWord linkid2 = vec_get(links, j);
      if (LMN_ATTR_IS_DATA(at[linkid2])) continue;
      /* is buddy? */
      if (wt[linkid2] == LMN_ATOM_GET_LINK(wt[linkid1], at[linkid1]) && 
          at[linkid2] == LMN_ATOM_GET_ATTR(wt[linkid1], at[linkid1])) {
        /* '='アトムをはさむ */
        LmnAtomPtr eq;
        if (mem) eq = lmn_mem_newatom(mem, LMN_UNIFY_FUNCTOR);
        else {
          eq = lmn_new_atom(LMN_UNIFY_FUNCTOR);
        }
                   
        lmn_newlink_in_symbols(LMN_ATOM(wt[linkid1]), at[linkid1], eq, 0);
        lmn_newlink_in_symbols(LMN_ATOM(wt[linkid2]), at[linkid2], eq, 1);
        hashset_add(retset, (HashKeyType)eq);
      }
    }
  }

  return retset;
}

static BOOL interpret(LmnRuleInstr instr, LmnRuleInstr *next_instr)
{
  LmnInstrOp op;
  while (TRUE) {
  LOOP:;
    READ_VAL(LmnInstrOp, instr, op);
/*     fprintf(stderr, "op: %d %p\n", op, next_instr); */
/*     lmn_dump_mem((LmnMembrane*)wt[0]); */
    switch (op) {
    case INSTR_SPEC:
    {
      LmnInstrVar s0, s1;

      READ_VAL(LmnInstrVar, instr, s0);
      READ_VAL(LmnInstrVar, instr, s1);

        /* extend vector if need */
      if (s1 > wt_size) {
        wt_size *= 2;
        wt = LMN_REALLOC(LmnWord, wt, wt_size);
        at = LMN_REALLOC(LmnByte, at, wt_size);
      }
      break;
    }
    case INSTR_INSERTCONNECTORSINNULL:
    {
      LmnInstrVar seti, list_num;
      Vector links;
      unsigned int i;

      READ_VAL(LmnInstrVar, instr, seti);
      READ_VAL(LmnInstrVar, instr, list_num);

      vec_init(&links, list_num); /* TODO: vector_initの仕様変更に伴い変更する */
      for (i = 0; i < list_num; i++) {
        LmnInstrVar t;
        READ_VAL(LmnInstrVar, instr, t);
        vec_push(&links, (LmnWord)t); /* TODO: vector_initの仕様変更に伴い変更する(インデックスアクセスに) */
      }

      wt[seti] = (LmnWord)insertconnectors(NULL, &links);
      vec_destroy(&links);

      /* EFFICIENCY: 解放のための再帰 */
      if(interpret(instr, next_instr)) {
        hashset_free((HashSet *)wt[seti]);
        return TRUE;
      }
      else assert(0);
      break;
    }
    case INSTR_INSERTCONNECTORS:
    {
      LmnInstrVar seti, list_num, memi, enti;
      Vector links; /* src list */
      unsigned int i;

      READ_VAL(LmnInstrVar, instr, seti);
      READ_VAL(LmnInstrVar, instr, list_num);

      vec_init(&links, list_num);

      for (i = 0; i < list_num; i++) {
        READ_VAL(LmnInstrVar, instr, enti);
        vec_push(&links, (LmnWord)enti); /* TODO: vector_initの仕様変更に伴い変更する */
      }

      READ_VAL(LmnInstrVar, instr, memi);
      wt[seti] = (LmnWord)insertconnectors((LmnMembrane *)wt[memi], &links);
      vec_destroy(&links);
      
      /* EFFICIENCY: 解放のための再帰 */
      if(interpret(instr, next_instr)) {
        hashset_free((HashSet *)wt[seti]);
        return TRUE;
      }
      vec_destroy(&links);
      break;
    }
    case INSTR_JUMP:
    {
      /* EFFICIENCY: 新たに作業配列をmallocしているので非常に遅い
                     -O3 で生成される中間命令にJUMPが含まれないため
                     これでもよい */
      LmnInstrVar num, i, n;
      LmnJumpOffset offset;
      BOOL ret;
      LmnWord *wt2 = LMN_NALLOC(LmnWord, wt_size);
      LmnByte *at2 = LMN_NALLOC(LmnByte, wt_size);
      unsigned int wt_size_org = wt_size;

      i = 0;
      /* atom */
      READ_VAL(LmnInstrVar, instr, num);
      for (; num--; i++) {
        READ_VAL(LmnInstrVar, instr, n);
        wt2[i] = wt[n];
        at2[i] = at[n];
      }
      /* mem */
      READ_VAL(LmnInstrVar, instr, num);
      for (; num--; i++) {
        READ_VAL(LmnInstrVar, instr, n);
        wt2[i] = wt[n];
        at2[i] = at[n];
      }
      /* vars */
      READ_VAL(LmnInstrVar, instr, num);
      for (; num--; i++) {
        READ_VAL(LmnInstrVar, instr, n);
        wt2[i] = wt[n];
        at2[i] = at[n];
      }

      READ_VAL(LmnJumpOffset, instr, offset);
      instr += offset;

      ret = interpret(instr, next_instr);
      LMN_FREE(wt2);
      LMN_FREE(at2);
      wt_size = wt_size_org;
      return ret;
    }
    case INSTR_COMMIT:
      instr += sizeof(lmn_interned_str) + sizeof(LmnLineNum);
      break;
    case INSTR_FINDATOM:
    {
      LmnInstrVar atomi, memi;
      LmnLinkAttr attr;

      READ_VAL(LmnInstrVar, instr, atomi);
      READ_VAL(LmnInstrVar, instr, memi);
      READ_VAL(LmnLinkAttr, instr, attr);
      
      if (LMN_ATTR_IS_DATA(attr)) {
        fprintf(stderr, "I can not find data atoms.\n");
        assert(FALSE);
      } else { /* symbol atom */
        LmnFunctor f;
        AtomListEntry *atomlist_ent;
        LmnAtomPtr atom;
        
        READ_VAL(LmnFunctor, instr, f);
        atomlist_ent = lmn_mem_get_atomlist((LmnMembrane*)wt[memi], f);
        if (atomlist_ent) {
          at[atomi] = LMN_ATTR_MAKE_LINK(0);
          for (atom = atomlist_head(atomlist_ent);
               atom != lmn_atomlist_end(atomlist_ent);
               atom = LMN_ATOM_GET_NEXT(atom)) {
            wt[atomi] = (LmnWord)atom;
            if (interpret(instr, &instr)) {
              *next_instr = instr;
              return TRUE;
            }
          }
        }
       return FALSE;
      }
      break;
    }
    case INSTR_LOCKMEM:
    {
      LmnInstrVar mem, atom, memn;
      READ_VAL(LmnInstrVar, instr, mem);
      READ_VAL(LmnInstrVar, instr, atom);
      READ_VAL(lmn_interned_str, instr, memn);
      
      LMN_ASSERT(!LMN_ATTR_IS_DATA(at[atom]));
      LMN_ASSERT(LMN_IS_PROXY_FUNCTOR(LMN_ATOM_GET_FUNCTOR(LMN_ATOM(wt[atom]))));
      wt[mem] = (LmnWord)LMN_PROXY_GET_MEM(wt[atom]);
      LMN_ASSERT(((LmnMembrane *)wt[mem])->parent);
      break;
    }
    case INSTR_ANYMEM:
    {
      LmnInstrVar mem1, mem2, memt, memn; /* dst, parent, type, name */
      LmnMembrane* mp;

      READ_VAL(LmnInstrVar, instr, mem1);
      READ_VAL(LmnInstrVar, instr, mem2);
      READ_VAL(LmnInstrVar, instr, memt);
      READ_VAL(lmn_interned_str, instr, memn);

      mp = ((LmnMembrane*)wt[mem2])->child_head;
      while (mp) {
        wt[mem1] = (LmnWord)mp;
        if (mp->name == memn && interpret(instr, &instr)) {
          *next_instr = instr;
          return TRUE;
        }
        mp = mp->next;
      }
      return FALSE;
      break;
    }
    case INSTR_NMEMS:
    {
      LmnInstrVar memi, nmems;

      READ_VAL(LmnInstrVar, instr, memi);
      READ_VAL(LmnInstrVar, instr, nmems);

      if(!lmn_mem_nmems((LmnMembrane*)wt[memi], nmems)) {
        return FALSE;
      }
      break;
    }
    case INSTR_NORULES:
    {
      LmnInstrVar memi;

      READ_VAL(LmnInstrVar, instr, memi);
      if(((LmnMembrane *)wt[memi])->rulesets.num) return FALSE;
      break;
    }
    case INSTR_NEWATOM:
    {
      LmnInstrVar atomi, memi;
      LmnWord ap;
      LmnLinkAttr attr;

      READ_VAL(LmnInstrVar, instr, atomi);
      READ_VAL(LmnInstrVar, instr, memi);
      READ_VAL(LmnLinkAttr, instr, attr);
      if (LMN_ATTR_IS_DATA(attr)) {
        READ_DATA_ATOM(ap, attr);
      } else { /* symbol atom */
        LmnFunctor f;

      	READ_VAL(LmnFunctor, instr, f);
      	ap = (LmnWord)lmn_new_atom(f);
      }
      lmn_mem_push_atom((LmnMembrane *)wt[memi], ap, attr);
      wt[atomi] = ap;
      at[atomi] = attr;
      break;
    }
    case INSTR_NATOMS:
    {
      LmnInstrVar memi, natoms;
      READ_VAL(LmnInstrVar, instr, memi);
      READ_VAL(LmnInstrVar, instr, natoms);
      if(!lmn_mem_natoms((LmnMembrane*)wt[memi], natoms)) {
        return FALSE;
      }
      break;
    }
    case INSTR_ALLOCLINK:
    {
      LmnInstrVar link, atom, n;
      
      READ_VAL(LmnInstrVar, instr, link);
      READ_VAL(LmnInstrVar, instr, atom);
      READ_VAL(LmnInstrVar, instr, n);

      if (LMN_ATTR_IS_DATA(at[atom])) {
        wt[link] = wt[atom];
        at[link] = at[atom];
      } else { /* link to atom */
        wt[link] = (LmnWord)LMN_ATOM(wt[atom]);
        at[link] = LMN_ATTR_MAKE_LINK(n);
      }
      break;
    }
    case INSTR_UNIFYLINKS:
    {
      LmnInstrVar link1, link2, mem;
      
      READ_VAL(LmnInstrVar, instr, link1);
      READ_VAL(LmnInstrVar, instr, link2);
      READ_VAL(LmnInstrVar, instr, mem);

      if (LMN_ATTR_IS_DATA(at[link1])) {
        if (LMN_ATTR_IS_DATA(at[link2])) { /* 1, 2 are data */
          lmn_mem_link_data_atoms((LmnMembrane *)wt[mem], wt[link1], at[link1], wt[link2], at[link2]);
        }
        else { /* 1 is data */
          LMN_ATOM_SET_LINK(LMN_ATOM(wt[link2]), LMN_ATTR_GET_VALUE(at[link2]), wt[link1]);
          LMN_ATOM_SET_ATTR(LMN_ATOM(wt[link2]),
                                 LMN_ATTR_GET_VALUE(at[link2]),
                                 at[link1]);
        }
      }
      else if (LMN_ATTR_IS_DATA(at[link2])) { /* 2 is data */
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[link1]), LMN_ATTR_GET_VALUE(at[link1]), wt[link2]);
        LMN_ATOM_SET_ATTR(LMN_ATOM(wt[link1]),
                               LMN_ATTR_GET_VALUE(at[link1]),
                               at[link2]);
      }
      else { /* 1, 2 are symbol atom */
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[link1]), LMN_ATTR_GET_VALUE(at[link1]), wt[link2]);
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[link2]), LMN_ATTR_GET_VALUE(at[link2]), wt[link1]);
        LMN_ATOM_SET_ATTR(LMN_ATOM(wt[link1]),
                               LMN_ATTR_GET_VALUE(at[link1]),
                               at[link2]);
        LMN_ATOM_SET_ATTR(LMN_ATOM(wt[link2]),
                               LMN_ATTR_GET_VALUE(at[link2]),
                               at[link1]);
      }
      break;
    }
    case INSTR_NEWLINK:
    {
      LmnInstrVar atom1, atom2, pos1, pos2, memi;

      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, pos1);
      READ_VAL(LmnInstrVar, instr, atom2);
      READ_VAL(LmnInstrVar, instr, pos2);
      READ_VAL(LmnInstrVar, instr, memi);

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

      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, pos1);
      READ_VAL(LmnInstrVar, instr, atom2);
      READ_VAL(LmnInstrVar, instr, pos2);
      READ_VAL(LmnInstrVar, instr, memi);

      ap = LMN_ATOM(LMN_ATOM_GET_LINK(wt[atom2], pos2));
      attr = LMN_ATOM_GET_ATTR(wt[atom2], pos2);

      if(LMN_ATTR_IS_DATA(at[atom1]) && LMN_ATTR_IS_DATA(attr)) {
        #ifdef DEBUG
        fprintf(stderr, "Two data atoms are connected each other.\n");
        #endif 
      }
      else if (LMN_ATTR_IS_DATA(at[atom1])) {
        LMN_ATOM_SET_LINK(ap, attr, wt[atom1]);
        LMN_ATOM_SET_ATTR(ap, attr, at[atom1]);
      }
      else if (LMN_ATTR_IS_DATA(attr)) {
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[atom1]), pos1, (LmnWord)ap);
        LMN_ATOM_SET_ATTR(LMN_ATOM(wt[atom1]), pos1, attr);
      }
      else {
        LMN_ATOM_SET_LINK(ap, attr, wt[atom1]);
        LMN_ATOM_SET_ATTR(ap, attr, pos1);
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[atom1]), pos1, (LmnWord)ap);
        LMN_ATOM_SET_ATTR(LMN_ATOM(wt[atom1]), pos1, attr);
      }
      break;
    }
    case INSTR_INHERITLINK:
    {
      LmnInstrVar atomi, posi, linki, memi;
      READ_VAL(LmnInstrVar, instr, atomi);
      READ_VAL(LmnInstrVar, instr, posi);
      READ_VAL(LmnInstrVar, instr, linki);
      READ_VAL(LmnInstrVar, instr, memi);

      if(LMN_ATTR_IS_DATA(at[atomi]) && LMN_ATTR_IS_DATA(at[linki])) {
        #ifdef DEBUG
        fprintf(stderr, "Two data atoms are connected each other.\n");
        #endif
      }
      else if(LMN_ATTR_IS_DATA(at[atomi])) {
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[linki]), at[linki], wt[atomi]);
        LMN_ATOM_SET_ATTR(LMN_ATOM(wt[linki]), at[linki], at[atomi]);
      }
      else if(LMN_ATTR_IS_DATA(at[linki])) {
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[atomi]), posi, wt[linki]);
        LMN_ATOM_SET_ATTR(LMN_ATOM(wt[atomi]), posi, at[linki]);
      }
      else {
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[atomi]), posi, wt[linki]);
        LMN_ATOM_SET_ATTR(LMN_ATOM(wt[atomi]), posi, at[linki]);
        LMN_ATOM_SET_LINK(LMN_ATOM(wt[linki]), at[linki], wt[atomi]);
        LMN_ATOM_SET_ATTR(LMN_ATOM(wt[linki]), at[linki], posi);
      }
      break;
    }
    case INSTR_GETLINK:
    {
      LmnInstrVar linki, atomi, posi;
      READ_VAL(LmnInstrVar, instr, linki);
      READ_VAL(LmnInstrVar, instr, atomi);
      READ_VAL(LmnInstrVar, instr, posi);

      wt[linki] = LMN_ATOM_GET_LINK(wt[atomi], posi);
      at[linki] = LMN_ATOM_GET_ATTR(wt[atomi], posi);
      break;
    }
    case INSTR_UNIFY:
    {
      LmnInstrVar atom1, pos1, atom2, pos2, memi;
    
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, pos1);
      READ_VAL(LmnInstrVar, instr, atom2);
      READ_VAL(LmnInstrVar, instr, pos2);
      READ_VAL(LmnInstrVar, instr, memi);

      lmn_mem_unify_atom_args((LmnMembrane *)wt[memi],
                              LMN_ATOM(wt[atom1]),
                              pos1,
                              LMN_ATOM(wt[atom2]),
                              pos2);
      break;
    }
    case INSTR_PROCEED:
      *next_instr = instr;
      return TRUE;
    case INSTR_ENQUEUEATOM:
    {
      LmnInstrVar atom;
  
      READ_VAL(LmnInstrVar, instr, atom);
      /* do nothing */
      break;
    }
    case INSTR_DEQUEUEATOM:
    {
      LmnInstrVar atom;
  
      READ_VAL(LmnInstrVar, instr, atom);
      break;
    }
    case INSTR_NEWMEM:
    {
      LmnInstrVar newmemi, parentmemi, memf;
      LmnMembrane *mp;

      READ_VAL(LmnInstrVar, instr, newmemi);
      READ_VAL(LmnInstrVar, instr, parentmemi);
      READ_VAL(LmnInstrVar, instr, memf);

      mp = lmn_mem_make(); /*lmn_new_mem(memf);*/
      lmn_mem_add_child_mem((LmnMembrane*)wt[parentmemi], mp);
      wt[newmemi] = (LmnWord)mp;
      memstack_push(mp);
      break;
    }
    case INSTR_REMOVEATOM:
    {
      LmnInstrVar atomi, memi;

      READ_VAL(LmnInstrVar, instr, atomi);
      READ_VAL(LmnInstrVar, instr, memi);

      lmn_mem_remove_atom((LmnMembrane*)wt[memi], wt[atomi], at[atomi]);
      break;
    }
    case INSTR_FREEATOM:
    {
      LmnInstrVar atomi;

      READ_VAL(LmnInstrVar, instr, atomi);

      lmn_free_atom(wt[atomi], at[atomi]);
      break;
    }
    case INSTR_REMOVEMEM:
    {
      LmnInstrVar memi;
      LmnMembrane *mp;

      READ_VAL(LmnInstrVar, instr, memi);
      instr += sizeof(LmnInstrVar); /* ingnore parent */
      
      mp = (LmnMembrane*)wt[memi];
      LMN_ASSERT(mp->parent);
      if(mp->parent->child_head == mp) mp->parent->child_head = mp->next;
      if(mp->prev) mp->prev->next = mp->next;
      if(mp->next) mp->next->prev = mp->prev;
      mp->parent = NULL; /* removeproxies のために必要 */
      break;
    }
    case INSTR_FREEMEM:
    {
      LmnInstrVar memi;
      LmnMembrane *mp;

      READ_VAL(LmnInstrVar, instr, memi);

      mp = (LmnMembrane*)wt[memi];
      lmn_mem_free(mp);
      break;
    }
    case INSTR_ENQUEUEMEM:
    {
      LmnInstrVar memi;
      READ_VAL(LmnInstrVar, instr, memi);
      memstack_push((LmnMembrane *)wt[memi]);
      break;
    }
    case INSTR_UNLOCKMEM:
    { /* 何もしない */
      LmnInstrVar memi;
      READ_VAL(LmnInstrVar, instr, memi);
      break;
    }
    case INSTR_LOADRULESET:
    {
      LmnInstrVar memi;
      LmnRulesetId id;
      READ_VAL(LmnInstrVar, instr, memi);
      READ_VAL(LmnRulesetId, instr, id);
      
      lmn_mem_add_ruleset((LmnMembrane*)wt[memi], LMN_RULESET_ID(id));
      break;
    }
    case INSTR_DEREFATOM:
    {
      LmnInstrVar atom1, atom2, posi;
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);
      READ_VAL(LmnInstrVar, instr, posi);
            
      wt[atom1] = (LmnWord)LMN_ATOM(LMN_ATOM_GET_LINK(wt[atom2], posi));
      at[atom1] = LMN_ATOM_GET_ATTR(wt[atom2], posi);
      break;
    }
    case INSTR_DEREF:
    {
      LmnInstrVar atom1, atom2, pos1, pos2;
      LmnByte attr;

      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);
      READ_VAL(LmnInstrVar, instr, pos1);
      READ_VAL(LmnInstrVar, instr, pos2);

      attr = LMN_ATOM_GET_ATTR(wt[atom2], pos1);
      LMN_ASSERT(!LMN_ATTR_IS_DATA(at[atom2]));
      if (LMN_ATTR_IS_DATA(attr)) {
        if (pos2 != 0) return FALSE;
      }
      else {
        if (attr != pos2) return FALSE;
      }
      wt[atom1] = LMN_ATOM_GET_LINK(wt[atom2], pos1);
      at[atom1] = attr;
      break;
    }
    case INSTR_FUNC:
    {
      LmnInstrVar atomi;
      LmnFunctor f;
      LmnLinkAttr attr;
      READ_VAL(LmnInstrVar, instr, atomi);
      READ_VAL(LmnLinkAttr, instr, attr);

      if (LMN_ATTR_IS_DATA(at[atomi]) == LMN_ATTR_IS_DATA(attr)) {
        if(LMN_ATTR_IS_DATA(at[atomi])) {
          BOOL eq;
          if(at[atomi] != attr) return FALSE; /* comp attr */
          READ_CMP_DATA_ATOM(attr, wt[atomi], eq);
          if (!eq) return FALSE;
        }
        else /* symbol atom */
          {
            READ_VAL(LmnFunctor, instr, f);
            if (LMN_ATOM_GET_FUNCTOR(LMN_ATOM(wt[atomi])) != f) {
              return FALSE;
            }
          }
      }
      else { /* LMN_ATTR_IS_DATA(at[atomi]) != LMN_ATTR_IS_DATA(attr) */
        return FALSE;
      }
      break;
    }
    case INSTR_NOTFUNC:
    {
      LmnInstrVar atomi;
      LmnFunctor f;
      LmnLinkAttr attr;
      READ_VAL(LmnInstrVar, instr, atomi);
      READ_VAL(LmnLinkAttr, instr, attr);

      if (LMN_ATTR_IS_DATA(at[atomi]) == LMN_ATTR_IS_DATA(attr)) {
        if(LMN_ATTR_IS_DATA(at[atomi])) {
          if(at[atomi] == attr) {
            BOOL eq;
            READ_CMP_DATA_ATOM(attr, wt[atomi], eq);
            if (eq) return FALSE;
          }
        }
        else { /* symbol atom */
          READ_VAL(LmnFunctor, instr, f);
          if (LMN_ATOM_GET_FUNCTOR(LMN_ATOM(wt[atomi])) == f) return FALSE;
        }
      }
      break;
    }
    case INSTR_ISGROUND:
    {
      unsigned int i, atom_num;
      BOOL ret_flag = TRUE;
      LmnInstrVar funci, srclisti, avolisti;
      Vector *srcvec, *avovec; /* 変数番号のリスト */
      HashSet visited_atoms;
      Vector stack, visited_root;
      LinkObj *start;

      READ_VAL(LmnInstrVar, instr, funci);
      READ_VAL(LmnInstrVar, instr, srclisti);
      READ_VAL(LmnInstrVar, instr, avolisti);

      srcvec = (Vector*) wt[srclisti];
      avovec = (Vector*) wt[avolisti];

      vec_init(&stack, 16); /* 再帰除去用スタック */
      start = LinkObj_make((LmnWord)wt[vec_get(srcvec, 0)], at[vec_get(srcvec, 0)]);
      if(!LMN_ATTR_IS_DATA(start->pos)) { /* data atom は積まない */
        vec_push(&stack, (LmnWord)start);
      } else {
        LMN_FREE(start);
      }

      vec_init(&visited_root, 16);
      for(i = 0; i < srcvec->num; i++) {
        vec_push(&visited_root, FALSE);
      }
      vec_set(&visited_root, 0, TRUE);

      hashset_init(&visited_atoms, 256);

      atom_num = 0;

      while(stack.num != 0) { /* main loop: start */
        LinkObj *lo = (LinkObj *)vec_pop(&stack);

        if(hashset_contains(&visited_atoms, (HashKeyType)lo->ap)) {
          LMN_FREE(lo);
          continue;
        }

        /* 対のリンクが禁止リンクでないか */
        for(i = 0; i < avovec->num; i++) {
          LmnAtomPtr avolink = (LmnAtomPtr)wt[vec_get(avovec, i)];
          LmnLinkAttr avoattr = at[vec_get(avovec, i)];
          if((LmnAtomPtr)LMN_ATOM_GET_LINK(lo->ap, LMN_ATOM_GET_ATTR(lo->ap, lo->pos)) == avolink &&
              LMN_ATOM_GET_ATTR(lo->ap, LMN_ATOM_GET_ATTR(lo->ap, lo->pos)) == avoattr) {
            ret_flag = FALSE;
            break;
          }
        }
        if(!ret_flag) {
          LMN_FREE(lo);
          break;
        }

        /* 膜を横切っていないか */
        if(LMN_IS_PROXY_FUNCTOR(LMN_ATOM_GET_FUNCTOR(lo->ap))) {
          LMN_FREE(lo);
          ret_flag = FALSE;
          break;
        }

        /* 根に到達した場合 */
        for(i = 0; i < visited_root.num; i++) {
          unsigned int index = vec_get(srcvec, i);
          if (lo->ap == (LmnWord)LMN_ATOM_GET_LINK((LmnAtomPtr)wt[index], at[index])
              && lo->pos == LMN_ATOM_GET_ATTR((LmnAtomPtr)wt[index], at[index])) {
            vec_set(&visited_root, i, TRUE);
            goto ISGROUND_CONT;
          }
        }

        atom_num++;
        hashset_add(&visited_atoms, (LmnWord)lo->ap);

        /* 子の展開 */
        for(i = 0; i < LMN_ATOM_GET_ARITY(lo->ap); i++) {
          LinkObj *next;
          if (i == lo->pos)
            continue;
          if(!LMN_ATTR_IS_DATA(LMN_ATOM_GET_ATTR(lo->ap, i))) { /* data atom は積まない */
            next = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATTR_GET_VALUE(LMN_ATOM_GET_ATTR(lo->ap, i)));
            vec_push(&stack, (LmnWord)next);
          }
        }

ISGROUND_CONT:
        LMN_FREE(lo);
      } /* main loop: end */

      for(i = 0; i < visited_root.num; i++) {
        if(!vec_get(&visited_root, i)) { /* 未訪問の根がある */
          ret_flag=FALSE;
          break;
        }
      }

      hashset_destroy(&visited_atoms);
      vec_destroy(&stack);
      vec_destroy(&visited_root);
      if(!ret_flag) return FALSE;
      wt[funci] = (LmnWord)atom_num;
      at[funci] = LMN_INT_ATTR;
      break;
    }
    case INSTR_EQGROUND:
    case INSTR_NEQGROUND:
    {
      unsigned int i, j;
      BOOL ret_flag = TRUE;
      LmnInstrVar srci, dsti;
      Vector *srcv, *dstv; /* 変数番号のリスト（比較元、比較先） */
      Vector stack1, stack2;
      SimpleHashtbl map; /* 比較元→比較先 */
      LinkObj *start1, *start2;

      READ_VAL(LmnInstrVar, instr, srci);
      READ_VAL(LmnInstrVar, instr, dsti);

      srcv = (Vector *)wt[srci];
      dstv = (Vector *)wt[dsti];

      hashtbl_init(&map, 256);

      vec_init(&stack1, 16);
      vec_init(&stack2, 16);
      start1 = LinkObj_make((LmnWord)wt[vec_get(srcv, 0)], at[vec_get(srcv, 0)]);
      start2 = LinkObj_make((LmnWord)wt[vec_get(dstv, 0)], at[vec_get(dstv, 0)]);
      if (!LMN_ATTR_IS_DATA(start1->pos) && !LMN_ATTR_IS_DATA(start2->pos)) { /* ともにシンボルアトムの場合 */
        vec_push(&stack1, (LmnWord)start1);
        vec_push(&stack2, (LmnWord)start2);
      }
      else { /* data atom は積まない */
        if(!lmn_eq_func(start1->ap, start1->pos, start2->ap, start2->pos)) ret_flag = FALSE;
        LMN_FREE(start1);
        LMN_FREE(start2);
      }

      while(stack1.num != 0) { /* main loop: start */
        LinkObj *l1 = (LinkObj *)vec_pop(&stack1);
        LinkObj *l2 = (LinkObj *)vec_pop(&stack2);
        BOOL contains1 = FALSE;
        BOOL contains2 = FALSE;

        for(i = 0; i < srcv->num; i++) {
          unsigned int index = vec_get(srcv, i);
          if (l1->ap == (LmnWord)LMN_ATOM_GET_LINK((LmnAtomPtr)wt[index], at[index])
              && l1->pos == LMN_ATOM_GET_ATTR((LmnAtomPtr)wt[index], at[index])) {
            contains1 = TRUE;
            break;
          }
        }
        for(j = 0; j < dstv->num; j++) {
          unsigned int index = vec_get(dstv, j);
          if (l2->ap == (LmnWord)LMN_ATOM_GET_LINK((LmnAtomPtr)wt[index], at[index])
              && l2->pos == LMN_ATOM_GET_ATTR((LmnAtomPtr)wt[index], at[index])) {
            contains2 = TRUE;
            break;
          }
        }
        if(i != j){ /* 根の位置が違う */
          LMN_FREE(l1); LMN_FREE(l2);
          ret_flag = FALSE;
          break;
        }
        if(contains1) { /* 根に到達した場合 */
          LMN_FREE(l1); LMN_FREE(l2);
          continue;
        }

        if(l1->pos != l2->pos){ /* 引数検査 */
          LMN_FREE(l1); LMN_FREE(l2);
          ret_flag = FALSE;
          break;
        }

        if(LMN_ATOM_GET_FUNCTOR(l1->ap) != LMN_ATOM_GET_FUNCTOR(l2->ap)){ /* ファンクタ検査 */
          LMN_FREE(l1); LMN_FREE(l2);
          ret_flag = FALSE;
          break;
        }

        if(!hashtbl_contains(&map, l1->ap)) hashtbl_put(&map, l1->ap, l2->ap); /* 未出 */
        else if(hashtbl_get(&map, l1->ap) != l2->ap) { /* 既出で不一致 */
          LMN_FREE(l1); LMN_FREE(l2);
          ret_flag = FALSE;
          break;
        }
        else continue; /* 既出で一致 */

        for(i = 0; i < LMN_ATOM_GET_ARITY(l1->ap); i++) {
          LinkObj *n1, *n2;
          if(i == l1->pos) continue;
          if (!LMN_ATTR_IS_DATA(LMN_ATOM_GET_ATTR(l1->ap, i)) && !LMN_ATTR_IS_DATA(LMN_ATOM_GET_ATTR(l1->ap, i))) {
            n1 = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(l1->ap, i), LMN_ATTR_GET_VALUE(LMN_ATOM_GET_ATTR(l1->ap, i)));
            n2 = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(l2->ap, i), LMN_ATTR_GET_VALUE(LMN_ATOM_GET_ATTR(l2->ap, i)));
            vec_push(&stack1, (LmnWord)n1);
            vec_push(&stack2, (LmnWord)n2);
          }
          else { /* data atom は積まない */
            if(!lmn_eq_func(LMN_ATOM_GET_LINK(l1->ap, i), LMN_ATOM_GET_ATTR(l1->ap, i),
                  LMN_ATOM_GET_LINK(l2->ap, i), LMN_ATOM_GET_ATTR(l2->ap, i))) {
              LMN_FREE(l1); LMN_FREE(l2);
              ret_flag = FALSE;
              goto EQGROUND_NEQGROUND_BREAK;
            }
          }
        }
        LMN_FREE(l1); LMN_FREE(l2);
      } /* main loop: end */

EQGROUND_NEQGROUND_BREAK:
      vec_destroy(&stack1);
      vec_destroy(&stack2);
      hashtbl_destroy(&map);
      if((!ret_flag && INSTR_EQGROUND == op) || (ret_flag && INSTR_NEQGROUND == op)) {
        return FALSE;
      }
      break;
    }
    case INSTR_COPYGROUND:
    {
      unsigned int i;
      LmnInstrVar dstlist, srclist, memi;
      Vector *srcvec, *dstlovec, *retvec; /* 変数番号のリスト */
      SimpleHashtbl *atommap = LMN_MALLOC(SimpleHashtbl);
      Vector stack;
      LinkObj *start;
      LmnAtomPtr cpatom;

      READ_VAL(LmnInstrVar, instr, dstlist);
      READ_VAL(LmnInstrVar, instr, srclist);
      READ_VAL(LmnInstrVar, instr, memi);

      srcvec = (Vector *)wt[srclist];

      vec_init(&stack, 16); /* 再帰除去用スタック */

      hashtbl_init(atommap, 256);
      /* atommapの初期設定：ループ内で親アトムを参照する必要があるため */
      start = LinkObj_make((LmnWord)wt[vec_get(srcvec, 0)], (LmnLinkAttr)at[vec_get(srcvec, 0)]);
      cpatom = (LmnAtomPtr)lmn_copy_atom(start->ap, start->pos);
      hashtbl_put(atommap, (HashKeyType)start->ap, (HashValueType)cpatom);
      if (!LMN_ATTR_IS_DATA(start->pos)) { /* data atom でない場合 */
        mem_push_symbol_atom((LmnMembrane *)wt[memi], cpatom);
        for(i = 0; i < LMN_ATOM_GET_ARITY(cpatom); i++) {
          if(start->pos == i)
            continue;
          else {
            LmnLinkAttr attr = LMN_ATOM_GET_ATTR(start->ap, i);
            if (!LMN_ATTR_IS_DATA(attr)) {
              LinkObj *next = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(start->ap, i), LMN_ATOM_GET_ATTR(start->ap, i));
              vec_push(&stack, (LmnWord)next);
            }
            else { /* data atom はスタックに積まないで即コピーする */
              LmnWord cpdata = lmn_copy_atom(LMN_ATOM_GET_LINK(start->ap, i), attr);
              lmn_mem_push_atom((LmnMembrane *)wt[memi], cpdata, attr);
              hashtbl_put(atommap, (HashKeyType)LMN_ATOM_GET_LINK(start->ap, i), (HashValueType)cpatom);
              LMN_ATOM_SET_LINK(cpatom, i, (LmnWord)cpdata);
              LMN_ATOM_SET_ATTR(cpatom, i, attr);
            }
          }
        }
      }
      else { /* data atom の場合 */
        lmn_mem_push_atom((LmnMembrane *)wt[memi], (LmnWord)cpatom, start->pos);
      }
      /* atommapの初期設定：ここまで */

      while(stack.num != 0) { /* main loop: start */
        LinkObj *lo = (LinkObj *)vec_pop(&stack);

        /* 根に到達した場合 */
        for(i = 0; i < srcvec->num; i++){
          unsigned int index = vec_get(srcvec, i);
          if (lo->ap == (LmnWord)LMN_ATOM_GET_LINK((LmnAtomPtr)wt[index], at[index])
              && lo->pos == LMN_ATOM_GET_ATTR((LmnAtomPtr)wt[index], at[index])) {
            goto COPYGROUND_CONT;
          }
        }

        if(!hashtbl_contains(atommap, (HashKeyType)lo->ap)) {
          /* アトムのコピーを作成して親アトムのコピーと接続する */
          LmnAtomPtr cpbuddy = (LmnAtomPtr)hashtbl_get(atommap, (HashKeyType)(LmnAtomPtr)LMN_ATOM_GET_LINK(lo->ap, lo->pos));
          cpatom = (LmnAtomPtr)lmn_copy_atom(lo->ap, lo->pos);
          mem_push_symbol_atom((LmnMembrane *)wt[memi], (LmnAtomPtr)cpatom);

          hashtbl_put(atommap, (HashKeyType)lo->ap, (HashValueType)cpatom);
          LMN_ATOM_SET_LINK(cpbuddy, LMN_ATOM_GET_ATTR(lo->ap, lo->pos), (LmnWord)cpatom);
          LMN_ATOM_SET_ATTR(cpbuddy, LMN_ATOM_GET_ATTR(lo->ap, lo->pos), lo->pos);
          for(i = 0; i < LMN_ATOM_GET_ARITY(cpatom); i++) {
            if (!LMN_ATTR_IS_DATA(LMN_ATOM_GET_ATTR(lo->ap, i))) {
              LinkObj *next = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATOM_GET_ATTR(lo->ap, i));
              vec_push(&stack, (LmnWord)next);
            }
            else { /* data atom はスタックに積まないで即コピーする */
              LmnAtomPtr cpdata = (LmnAtomPtr)lmn_copy_atom(LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATOM_GET_ATTR(lo->ap, i));
              hashtbl_put(atommap, (HashKeyType)LMN_ATOM_GET_LINK(lo->ap, i), (HashValueType)cpatom);
              lmn_mem_push_atom((LmnMembrane *)wt[memi], (LmnWord)cpdata, LMN_ATOM_GET_ATTR(lo->ap, i));
              LMN_ATOM_SET_LINK(cpatom, i, (LmnWord)cpdata);
              LMN_ATOM_SET_ATTR(cpatom, i, LMN_ATOM_GET_ATTR(lo->ap, i));
            }
          }
        }
        else {
          /* アトムのコピーを表から取得して親アトムのコピーと接続する */
          LmnAtomPtr cpbuddy = (LmnAtomPtr)hashtbl_get(atommap, (HashKeyType)(LmnAtomPtr)LMN_ATOM_GET_LINK(lo->ap, lo->pos));
          cpatom = (LmnAtomPtr)hashtbl_get(atommap, (HashKeyType)lo->ap);
          LMN_ATOM_SET_LINK(cpbuddy, LMN_ATOM_GET_ATTR(lo->ap, lo->pos), (LmnWord)cpatom);
          LMN_ATOM_SET_ATTR(cpbuddy, LMN_ATOM_GET_ATTR(lo->ap, lo->pos), lo->pos);
        }

COPYGROUND_CONT:
        LMN_FREE(lo);
      } /* main loop: end */

      dstlovec = vec_make(srcvec->num);
      for(i = 0; i < srcvec->num; i++) {
        LmnAtomPtr src_ap = (LmnAtomPtr)wt[vec_get(srcvec, i)];
        LmnByte src_pos = (LmnLinkAttr)at[vec_get(srcvec, i)];
        LinkObj *new = LinkObj_make((LmnWord)hashtbl_get(atommap, (HashKeyType)src_ap), src_pos);
        vec_push(dstlovec, (LmnWord)new);
      }

      /* 返り値の作成 */
      retvec = vec_make(2);
      vec_push(retvec, (LmnWord)dstlovec);
      vec_push(retvec, (LmnWord)atommap);
      wt[dstlist] = (LmnWord)retvec;
      at[dstlist] = (LmnByte)LIST_AND_MAP;

      LMN_FREE(start);
      vec_destroy(&stack);

      /* 解放のための再帰 */
      if(interpret(instr, next_instr)) {
        while(dstlovec->num) {
          LMN_FREE(vec_get(dstlovec, dstlovec->num-1));
          dstlovec->num--;
        }
        vec_free(dstlovec);
        vec_free(retvec);
        return TRUE;
      }
      else assert(0);
      break;
    }
    case INSTR_REMOVEGROUND:
    case INSTR_FREEGROUND:
    {
      unsigned int i;
      LmnInstrVar listi, memi;
      Vector *srcvec; /* 変数番号のリスト */
      HashSet visited_atoms;
      Vector stack;
      LinkObj *start;

      READ_VAL(LmnInstrVar, instr, listi);
      if (INSTR_REMOVEGROUND == op) {
        READ_VAL(LmnInstrVar, instr, memi);
      }

      srcvec = (Vector *)wt[listi];

      vec_init(&stack, 16); /* 再帰除去用スタック */
      start = LinkObj_make((LmnWord)wt[vec_get(srcvec, 0)], at[vec_get(srcvec, 0)]);
      if(!LMN_ATTR_IS_DATA(start->pos)) {
        vec_push(&stack, (LmnWord)start);
      }
      else { /* data atom は積まない */
        switch (op) {
          case INSTR_REMOVEGROUND:
            lmn_mem_remove_atom((LmnMembrane*)wt[memi], start->ap, start->pos);
            LMN_FREE(start);
            break;
          case INSTR_FREEGROUND:
            /* data atomは接続されたsymbol atomがfreeされると一緒にfreeされる */
            LMN_FREE(start);
            break;
        }
      }

      hashset_init(&visited_atoms, 256);

      while(stack.num != 0) { /* main loop: start */
        LinkObj *lo = (LinkObj *)vec_pop(&stack);

        if(hashset_contains(&visited_atoms, (HashKeyType)lo->ap))
          continue;

        hashset_add(&visited_atoms, (LmnWord)lo->ap);
        for(i = 0; i < srcvec->num; i++) { /* 根に到達したか */
          unsigned int index = vec_get(srcvec, i);
          if (lo->ap == (LmnWord)LMN_ATOM_GET_LINK((LmnAtomPtr)wt[index], at[index])
              && lo->pos == LMN_ATOM_GET_ATTR((LmnAtomPtr)wt[index], at[index])) {
            goto REMOVE_FREE_GROUND_CONT;
          }
        }

        for(i = 0; i < LMN_ATOM_GET_ARITY(lo->ap); i++) {
          LinkObj *next;
          if(i == lo->pos)
            continue;
          if(!LMN_ATTR_IS_DATA(LMN_ATOM_GET_ATTR(lo->ap, i))) {
            next = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATTR_GET_VALUE(LMN_ATOM_GET_ATTR(lo->ap, i)));
            vec_push(&stack, (LmnWord)next); 
          }
          else { /* data atom は積まない */
            switch (op) {
              case INSTR_REMOVEGROUND:
                lmn_mem_remove_atom((LmnMembrane*)wt[memi], (LmnWord)LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATOM_GET_ATTR(lo->ap, i));
                break;
              case INSTR_FREEGROUND:
                /* data atomは接続されたsymbol atomがfreeされると一緒にfreeされる */
                break;
            }
          }
        }

        switch (op) {
          case INSTR_REMOVEGROUND:
            lmn_mem_remove_atom((LmnMembrane*)wt[memi], lo->ap, lo->pos);
            break;
          case INSTR_FREEGROUND:
            lmn_free_atom(lo->ap, lo->pos);
            break;
        }

REMOVE_FREE_GROUND_CONT:
        LMN_FREE(lo);
      } /* main loop: end */

      vec_destroy(&stack);
      hashset_destroy(&visited_atoms);
      break;
    }
    case INSTR_ISUNARY:
    {
      LmnInstrVar atomi;
      READ_VAL(LmnInstrVar, instr, atomi);

      if(!LMN_ATTR_IS_DATA(at[atomi]) &&
         LMN_ATOM_GET_ARITY((LmnAtomPtr)wt[atomi]) != 1) return FALSE;
      break;
    }
    case INSTR_ISINT:
    {
      LmnInstrVar atomi;
      READ_VAL(LmnInstrVar, instr, atomi);

      if (at[atomi] != LMN_INT_ATTR)
        return FALSE;
      break;
    }
    case INSTR_ISFLOAT:
    {
      LmnInstrVar atomi;
      READ_VAL(LmnInstrVar, instr, atomi);

      if(at[atomi] != LMN_DBL_ATTR)
        return FALSE;
      break;
    }
    case INSTR_ISINTFUNC:
    {
      LmnInstrVar funci;
      READ_VAL(LmnInstrVar, instr, funci);

      if(at[funci] != LMN_INT_ATTR)
        return FALSE;
      break;
    }
    case INSTR_ISFLOATFUNC:
    {
      LmnInstrVar funci;
      READ_VAL(LmnInstrVar, instr, funci);

      if(at[funci] != LMN_DBL_ATTR)
        return FALSE;
      break;
    }
    case INSTR_COPYATOM:
    {
      LmnInstrVar atom1, memi, atom2;

      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, memi);
      READ_VAL(LmnInstrVar, instr, atom2);

      at[atom1] = at[atom2];
      wt[atom1] = lmn_copy_atom(wt[atom2], at[atom2]);
      lmn_mem_push_atom((LmnMembrane *)wt[memi], wt[atom1], at[atom1]);
      break;
    }
    case INSTR_EQATOM:
    {
      LmnInstrVar atom1, atom2;
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      /* データアトムは１引数なので,この命令が出る状況では
         では常にFALSEのはず */
      if (LMN_ATTR_IS_DATA(at[atom1]) ||
          LMN_ATTR_IS_DATA(at[atom2]) ||
          LMN_ATOM(wt[atom1]) != LMN_ATOM(wt[atom2]))
        return FALSE;
      break;
    }
    case INSTR_NEQATOM:
    {
      LmnInstrVar atom1, atom2;
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);
 
      if (!(LMN_ATTR_IS_DATA(at[atom1]) ||
            LMN_ATTR_IS_DATA(at[atom2]) ||
            LMN_ATOM(wt[atom1]) != LMN_ATOM(wt[atom2])))
        return FALSE;
      break;
    }
    case INSTR_EQMEM:
    {
      LmnInstrVar mem1, mem2;

      READ_VAL(LmnInstrVar, instr, mem1);
      READ_VAL(LmnInstrVar, instr, mem2);
      if (wt[mem1] != wt[mem2]) return FALSE;
      break;
    }
    case INSTR_NEQMEM:
    {
      LmnInstrVar mem1, mem2;
      READ_VAL(LmnInstrVar, instr, mem1);
      READ_VAL(LmnInstrVar, instr, mem2);

      if(wt[mem1] == wt[mem2]) return FALSE;
      break;
    }
    case INSTR_NEWLIST:
    {
      LmnInstrVar listi;
      Vector *listvec = vec_make(16);
      READ_VAL(LmnInstrVar, instr, listi);
      wt[listi] = (LmnWord)listvec;
      /* 解放のための再帰 */
      if(interpret(instr, next_instr)) {
        vec_free(listvec);
        return TRUE;
      }
      else {
        vec_free(listvec);
        return FALSE;
      }
      break;
    }
    case INSTR_ADDTOLIST:
    {
      LmnInstrVar listi, srci;
      READ_VAL(LmnInstrVar, instr, listi);
      READ_VAL(LmnInstrVar, instr, srci);
      vec_push((Vector *)wt[listi], srci);
      break;
    }
    case INSTR_GETFROMLIST:
    {
      LmnInstrVar dsti, listi, posi;
      READ_VAL(LmnInstrVar, instr, dsti);
      READ_VAL(LmnInstrVar, instr, listi);
      READ_VAL(LmnInstrVar, instr, posi);

      switch (at[listi]) {
        case LIST_AND_MAP:
          wt[dsti] = vec_get((Vector *)wt[listi], (unsigned int)posi);
          if (posi == 0)
            at[dsti] = LINK_LIST;
          else if (posi == 1)
            at[dsti] = MAP;
          else
            assert(0);
          break;
        case LINK_LIST: /* LinkObjをfreeするのはここ？ */
        {
          LinkObj *lo = (LinkObj *)vec_get((Vector *)wt[listi], (unsigned int)posi);
          wt[dsti] = (LmnWord)lo->ap; 
          at[dsti] = lo->pos;
          break;
        }
      }
      break;
    }
    case INSTR_IADD:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] + (int)wt[atom2]);
      at[dstatom] = LMN_INT_ATTR;
      break;
    }
    case INSTR_ISUB:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] - (int)wt[atom2]);
      at[dstatom] = LMN_INT_ATTR;
      break;
    }
    case INSTR_IMUL:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] * (int)wt[atom2]);
      at[dstatom] = LMN_INT_ATTR;
      break;
    }
    case INSTR_IDIV:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] / (int)wt[atom2]);
      at[dstatom] = LMN_INT_ATTR;
      break;
    }
    case INSTR_INEG:
    {
      LmnInstrVar dstatom, atomi;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atomi);
      wt[dstatom] = (LmnWord)-(int)atomi;
      at[dstatom] = LMN_INT_ATTR;
      break;
    }
    case INSTR_IMOD:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] % (int)wt[atom2]);
      at[dstatom] = LMN_INT_ATTR;
      break;
    }
    case INSTR_INOT:
    {
      LmnInstrVar dstatom, atomi;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atomi);
      wt[dstatom] = (LmnWord)~(int)atomi;
      at[dstatom] = LMN_INT_ATTR;
      break;
    }
    case INSTR_IAND:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] & (int)wt[atom2]);
      at[dstatom] = LMN_INT_ATTR;
      break;
    }
    case INSTR_IOR:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] | (int)wt[atom2]);
      at[dstatom] = LMN_INT_ATTR;
      break;
    }
    case INSTR_IXOR:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] ^ (int)wt[atom2]);
      at[dstatom] = LMN_INT_ATTR;
      break;
    }
    case INSTR_ILT:
    {
      LmnInstrVar atom1, atom2;
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] < (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_ILE:
    {
      LmnInstrVar atom1, atom2;
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] <= (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_IGT:
    {
      LmnInstrVar atom1, atom2;
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] > (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_IGE:
    {
      LmnInstrVar atom1, atom2;
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] >= (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_IEQ:
    {
      LmnInstrVar atom1, atom2;
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] == (int)wt[atom2])) return FALSE;
      break;
    }
    case INSTR_INE:
    {
      LmnInstrVar atom1, atom2;
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      if(!((int)wt[atom1] != (int)wt[atom2])) return FALSE;
      break;
    }
    case  INSTR_FADD:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)LMN_MALLOC(double);
      *(double *)wt[dstatom] = *(double *)wt[atom1] + *(double *)wt[atom2];
      at[dstatom] = LMN_DBL_ATTR;
      break;
    }
    case  INSTR_FSUB:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)LMN_MALLOC(double);
      *(double *)wt[dstatom] = *(double *)wt[atom1] - *(double *)wt[atom2];
      at[dstatom] = LMN_DBL_ATTR;
      break;
    }
    case  INSTR_FMUL:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)LMN_MALLOC(double);
      *(double *)wt[dstatom] = *(double *)wt[atom1] * *(double *)wt[atom2];
      at[dstatom] = LMN_DBL_ATTR;
      break;
    }
    case  INSTR_FDIV:
    {
      LmnInstrVar dstatom, atom1, atom2;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)LMN_MALLOC(double);
      *(double *)wt[dstatom] = *(double *)wt[atom1] / *(double *)wt[atom2];
      at[dstatom] = LMN_DBL_ATTR;
      break;
    }
    case  INSTR_FNEG:
    {
      LmnInstrVar dstatom, atomi;
      READ_VAL(LmnInstrVar, instr, dstatom);
      READ_VAL(LmnInstrVar, instr, atomi);

      wt[dstatom] = (LmnWord)LMN_MALLOC(double);
      *(double *)wt[dstatom] = -*(double *)wt[atomi];
      at[dstatom] = LMN_DBL_ATTR;
      break;
    }
    case INSTR_ALLOCATOM:
    {
      LmnInstrVar atomi;
      LmnAtomPtr ap;
      LmnLinkAttr attr;

      READ_VAL(LmnInstrVar, instr, atomi);
      READ_VAL(LmnLinkAttr, instr, attr);
      at[atomi] = attr;
      if (LMN_ATTR_IS_DATA(attr)) {
        READ_DATA_ATOM(wt[atomi], attr);
      } else { /* symbol atom */
        LmnFunctor f;
        fprintf(stderr, "symbol atom can't be created in GUARD\n");
        exit(EXIT_FAILURE);
        READ_VAL(LmnFunctor, instr, f);
        wt[atomi] = (LmnWord)ap;
      }
      break;
    }
    case INSTR_ALLOCATOMINDIRECT:
    {
      LmnInstrVar atomi;
      LmnFunctor funci;

      READ_VAL(LmnInstrVar, instr, atomi);
      READ_VAL(LmnInstrVar, instr, funci);

      if (LMN_ATTR_IS_DATA(at[funci])) {
        wt[atomi] = lmn_copy_data_atom(wt[funci], at[funci]);
        at[atomi] = at[funci];
      } else { /* symbol atom */
        fprintf(stderr, "symbol atom can't be created in GUARD\n");
        exit(EXIT_FAILURE);
      }
      break;
    }
    case INSTR_SAMEFUNC:
    {
      LmnInstrVar atom1, atom2;

      READ_VAL(LmnInstrVar, instr, atom1);
      READ_VAL(LmnInstrVar, instr, atom2);

      if (!lmn_eq_func(wt[atom1], at[atom1], wt[atom2], at[atom2]))
        return FALSE;
      break;
    }
    case INSTR_GETFUNC:
    {
      LmnInstrVar funci, atomi;

      READ_VAL(LmnFunctor, instr, funci);
      READ_VAL(LmnInstrVar, instr, atomi);

      if(LMN_ATTR_IS_DATA(at[atomi])) {
        /* ここで得るファンクタはガード命令中で一時的に使われるだけなので
           double はポインタのコピーで十分なはず */
        wt[funci]=wt[atomi];
      }
      else {
        wt[funci] = (LmnWord)LMN_ATOM_GET_FUNCTOR(wt[atomi]);
      }
      at[funci] = at[atomi];

      break;
    }
    case INSTR_PRINTINSTR:
    {
      char c;
      
      fprintf(stderr, "instr:");
      while (TRUE) {
        READ_VAL(char, instr, c);
        if (!c) break;
        fprintf(stderr, "%c", c);
      }
      goto LOOP;
    }
    case INSTR_SETMEMNAME:
    {
      LmnInstrVar memi;
      lmn_interned_str name;

      READ_VAL(LmnInstrVar, instr, memi);
      READ_VAL(lmn_interned_str, instr, name);
      ((LmnMembrane *)wt[memi])->name = name;
      break;
    }
    case INSTR_COPYRULES:
    {
      LmnInstrVar destmemi, srcmemi;
      unsigned int i;
      struct Vector *v;
      
      READ_VAL(LmnInstrVar, instr, destmemi);
      READ_VAL(LmnInstrVar, instr, srcmemi);
      v = &((LmnMembrane *)wt[srcmemi])->rulesets;
      for (i = 0; i< v->num; i++) {
        lmn_mem_add_ruleset((LmnMembrane *)wt[destmemi], (LmnRuleSet *)vec_get(v, i));
      }
      break;
    }
    case INSTR_REMOVEPROXIES:
    {
      LmnInstrVar memi;

      READ_VAL(LmnInstrVar, instr, memi);
      lmn_mem_remove_proxies((LmnMembrane *)wt[memi]);
      break;
    }
    case INSTR_INSERTPROXIES:
    {
      LmnInstrVar parentmemi, childmemi;

      READ_VAL(LmnInstrVar, instr, parentmemi);
      READ_VAL(LmnInstrVar, instr, childmemi);
      lmn_mem_insert_proxies((LmnMembrane *)wt[parentmemi], (LmnMembrane *)wt[childmemi]);
      break;
    }
    case INSTR_DELETECONNECTORS:
    {
      LmnInstrVar srcset, srcmap;
      HashSet *delset;
      SimpleHashtbl *delmap;
      HashSetIterator it;
      READ_VAL(LmnInstrVar, instr, srcset);
      READ_VAL(LmnInstrVar, instr, srcmap);

      delset = (HashSet *)wt[srcset];
      delmap = (SimpleHashtbl *)wt[srcmap];

      for(it = hashset_iterator(delset); !hashsetiter_isend(&it); hashsetiter_next(&it)) {
        LmnAtomPtr orig = (LmnAtomPtr)hashsetiter_entry(&it);
        LmnAtomPtr copy = (LmnAtomPtr)hashtbl_get(delmap, (HashKeyType)orig);
        lmn_mem_unify_symbol_atom_args(copy, 0, copy, 1);
        /* mem がないので仕方なく直接アトムリストをつなぎ変える
           UNIFYアトムはnatomに含まれないので大丈夫 */
        LMN_ATOM_SET_PREV(LMN_ATOM_GET_NEXT(copy), LMN_ATOM_GET_PREV(copy));
        LMN_ATOM_SET_NEXT(LMN_ATOM_GET_PREV(copy), LMN_ATOM_GET_NEXT(copy));

        lmn_delete_atom(orig);
      }

      hashtbl_free(delmap);
      break;
    }
    case INSTR_REMOVETOPLEVELPROXIES:
    {
      LmnInstrVar memi;

      READ_VAL(LmnInstrVar, instr, memi);
      lmn_mem_remove_toplevel_proxies((LmnMembrane *)wt[memi]);
      break;
    }
    case INSTR_DEREFFUNC:
    {
      LmnInstrVar funci, atomi, pos;
      LmnLinkAttr attr;
      
      READ_VAL(LmnInstrVar, instr, funci);
      READ_VAL(LmnInstrVar, instr, atomi);
      READ_VAL(LmnLinkAttr, instr, pos);

      attr = LMN_ATOM_GET_ATTR(LMN_ATOM(wt[atomi]), pos);
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

      READ_VAL(LmnFunctor, instr, funci);
      READ_VAL(LmnInstrVar, instr, attr);
      at[funci] = attr;
      if(LMN_ATTR_IS_DATA(attr)) {
        READ_CONST_DATA_ATOM(wt[funci], attr);
      }
      else {
        LmnFunctor f;
        
        READ_VAL(LmnFunctor, instr, f);
        wt[funci] = (LmnWord)f;
      }
      break;
    }
    case INSTR_ADDATOM:
    {
      LmnInstrVar memi, atomi;

      READ_VAL(LmnInstrVar, instr, memi);
      READ_VAL(LmnInstrVar, instr, atomi);
      lmn_mem_push_atom((LmnMembrane *)wt[memi], wt[atomi], at[atomi]);
      break;
    }
    case INSTR_MOVECELLS:
    {
      LmnInstrVar destmemi, srcmemi;

      READ_VAL(LmnInstrVar, instr, destmemi);
      READ_VAL(LmnInstrVar, instr, srcmemi);
      LMN_ASSERT(wt[destmemi] != wt[srcmemi]);
      lmn_mem_move_cells((LmnMembrane *)wt[destmemi], (LmnMembrane *)wt[srcmemi]);
      break;
    }
    case INSTR_REMOVETEMPORARYPROXIES:
    {
      LmnInstrVar memi;

      READ_VAL(LmnInstrVar, instr, memi);
      lmn_mem_remove_temporary_proxies((LmnMembrane *)wt[memi]);
      break;
    }
    case INSTR_NFREELINKS:
    {
      LmnInstrVar memi, count;

      READ_VAL(LmnInstrVar, instr, memi);
      READ_VAL(LmnInstrVar, instr, count);

      if (!lmn_mem_nfreelinks((LmnMembrane *)wt[memi], count)) return FALSE;
      break;
    }
    case INSTR_COPYCELLS:
    {
      LmnInstrVar mapi, destmemi, srcmemi;

      READ_VAL(LmnInstrVar, instr, mapi);
      READ_VAL(LmnInstrVar, instr, destmemi);
      READ_VAL(LmnInstrVar, instr, srcmemi);
      wt[mapi] = (LmnWord)lmn_mem_copy_cells((LmnMembrane *)wt[destmemi],
                                             (LmnMembrane *)wt[srcmemi]);
      break;
    }
    case INSTR_LOOKUPLINK:
    {
      LmnInstrVar destlinki, tbli, srclinki;
      
      READ_VAL(LmnInstrVar, instr, destlinki);
      READ_VAL(LmnInstrVar, instr, tbli);
      READ_VAL(LmnInstrVar, instr, srclinki);

      at[destlinki] = at[srclinki];
      if (LMN_ATTR_IS_DATA(at[srclinki])) {
        wt[destlinki] = wt[srclinki];
      }
      else { /* symbol atom */
        SimpleHashtbl *ht = (SimpleHashtbl *)wt[tbli];
        wt[destlinki] = hashtbl_get(ht, wt[srclinki]);
      }
      break;
    }
    case INSTR_CLEARRULES:
    {
      LmnInstrVar memi;

      READ_VAL(LmnInstrVar, instr, memi);
      vec_clear(&((LmnMembrane *)wt[memi])->rulesets);
      break;
    }
    case INSTR_DROPMEM:
    {
      LmnInstrVar memi;

      READ_VAL(LmnInstrVar, instr, memi);
      lmn_mem_drop((LmnMembrane *)wt[memi]);
      break;
    }
    case INSTR_TESTMEM:
    {
      LmnInstrVar memi, atomi;

      READ_VAL(LmnInstrVar, instr, memi);
      READ_VAL(LmnInstrVar, instr, atomi);
      LMN_ASSERT(!LMN_ATTR_IS_DATA(at[atomi]));
      LMN_ASSERT(LMN_IS_PROXY_FUNCTOR(LMN_ATOM_GET_FUNCTOR(wt[atomi])));

      if (LMN_PROXY_GET_MEM(wt[atomi]) != (LmnMembrane *)wt[memi]) return FALSE;
      break;
    }
    case INSTR_IADDFUNC:
    {
      LmnInstrVar desti, i0, i1;

      READ_VAL(LmnInstrVar, instr, desti);
      READ_VAL(LmnInstrVar, instr, i0);
      READ_VAL(LmnInstrVar, instr, i1);
      LMN_ASSERT(at[i0] == LMN_INT_ATTR);
      LMN_ASSERT(at[i1] == LMN_INT_ATTR);
      wt[desti] = wt[i0] + wt[i1];
      at[desti] = LMN_INT_ATTR;
      break;
    }
    case INSTR_ISUBFUNC:
    {
      LmnInstrVar desti, i0, i1;

      READ_VAL(LmnInstrVar, instr, desti);
      READ_VAL(LmnInstrVar, instr, i0);
      READ_VAL(LmnInstrVar, instr, i1);
      LMN_ASSERT(at[i0] == LMN_INT_ATTR);
      LMN_ASSERT(at[i1] == LMN_INT_ATTR);
      wt[desti] = wt[i0] - wt[i1];
      at[desti] = LMN_INT_ATTR;
      break;
    }
    case INSTR_IMULFUNC:
    {
      LmnInstrVar desti, i0, i1;

      READ_VAL(LmnInstrVar, instr, desti);
      READ_VAL(LmnInstrVar, instr, i0);
      READ_VAL(LmnInstrVar, instr, i1);
      LMN_ASSERT(at[i0] == LMN_INT_ATTR);
      LMN_ASSERT(at[i1] == LMN_INT_ATTR);
      wt[desti] = wt[i0] * wt[i1];
      at[desti] = LMN_INT_ATTR;
      break;
    }
    case INSTR_IDIVFUNC:
    {
      LmnInstrVar desti, i0, i1;

      READ_VAL(LmnInstrVar, instr, desti);
      READ_VAL(LmnInstrVar, instr, i0);
      READ_VAL(LmnInstrVar, instr, i1);
      LMN_ASSERT(at[i0] == LMN_INT_ATTR);
      LMN_ASSERT(at[i1] == LMN_INT_ATTR);
      wt[desti] = wt[i0] / wt[i1];
      at[desti] = LMN_INT_ATTR;
      break;
    }
    case INSTR_IMODFUNC:
    {
      LmnInstrVar desti, i0, i1;

      READ_VAL(LmnInstrVar, instr, desti);
      READ_VAL(LmnInstrVar, instr, i0);
      READ_VAL(LmnInstrVar, instr, i1);
      LMN_ASSERT(at[i0] == LMN_INT_ATTR);
      LMN_ASSERT(at[i1] == LMN_INT_ATTR);
      wt[desti] = wt[i0] % wt[i1];
      at[desti] = LMN_INT_ATTR;
      break;
    }
    case INSTR_GROUP:
    {
      if (!interpret(instr, &instr)) return FALSE;
      break;
    }
    default:
      fprintf(stderr, "interpret: Unknown operation %d\n", op);
      exit(1);
    }
/*     lmn_dump_mem((LmnMembrane *)wt[0]); */
/*     print_wt(); */

    #ifdef DEBUG
/*     print_wt(); */
    #endif
  }
}

/* DEBUG: */
/* static void print_wt(void) */
/* { */
/*   unsigned int i; */
/*   unsigned int end = 16; */
  
/*   fprintf(stderr, " wt: ["); */
/*   for (i = 0; i < end; i++) { */
/*     if (i>0) fprintf(stderr, ", "); */
/*     fprintf(stderr, "%lu", wt[i]); */
/*   } */
/*   fprintf(stderr, "]"); */
/*   fprintf(stderr, "\n"); */
/*   fprintf(stderr, " at: ["); */
/*   for (i = 0; i < end; i++) { */
/*     if (i>0) fprintf(stderr, ", "); */
/*     fprintf(stderr, "%u", at[i]); */
/*   } */
/*   fprintf(stderr, "]"); */
/*   fprintf(stderr, "\n"); */
/* } */
