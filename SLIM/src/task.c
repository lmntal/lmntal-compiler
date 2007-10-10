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
#include "vector.h"

/* TO BE MOVED TO task.h */
#define LINK_LIST     1
#define LIST_AND_MAP  2
#define MAP           3

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
  wt = v1;
  tv = v2;
  at = k1;
  tkv = k2;

  compiled_ruleset_init(&system_ruleset);
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
    LmnMembrane *m;
    if(!exec(mem)) {
      if (!compiled_ruleset_react(&system_ruleset, mem)) {
        m = memstack_pop();
        /* TODO: 確認. 膜スタックから削除される時に親膜を活性化する
           ENQUEUEMEMで積まれるので必要ないっぽい */
        /* if (m->parent && !m->parent->is_activated) memstack_push(m->parent); */
      }
    }
/*     memstack_printall(); */
  }

  memstack_destroy();
  
  lmn_dump_cell(mem);
  lmn_mem_drop(mem);
  lmn_mem_free(mem);
  compiled_ruleset_destroy(&system_ruleset);
  free_atom_memory_pools();
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

/* TODO: 以下にあるutility関数の置き場所を整理する */

/* mem != NULL ならば memにUNIFYを追加、そうでなければ
   UNIFYは膜に所属しない */
static HashSet *insertconnectors(LmnMembrane *mem, Vector *links)
{
  unsigned int i, j;
  HashSet *retset = hashset_make(8);
  for(i = 0; i < links->num; i++) {
    LmnWord linkid1 = vec_get(links, i);
    if (LMN_ATTR_IS_DATA(at[linkid1])) continue;
    for(j = i+1; j < links->num; j++) {
      LmnWord linkid2 = vec_get(links, j);
      if (LMN_ATTR_IS_DATA(at[linkid2])) continue;
      /* is buddy? */
      if (wt[linkid2] == LMN_ATOM_GET_LINK(wt[linkid1], at[linkid1]) && 
          at[linkid2] == LMN_ATOM_GET_LINK_ATTR(wt[linkid1], at[linkid1])) {
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
    LMN_IMS_READ(LmnInstrOp, instr, op);
/*     fprintf(stderr, "op: %d %p\n", op, next_instr); */
/*     lmn_mem_dump((LmnMembrane*)wt[0]); */
    switch (op) {
    case INSTR_SPEC:
      /* ignore spec, because wt is initially large enough. */
      instr += sizeof(LmnInstrVar)*2;
      break;
    case INSTR_INSERTCONNECTORSINNULL:
    {
      LmnInstrVar seti, list_num;
      Vector links;
      unsigned int i;

      LMN_IMS_READ(LmnInstrVar, instr, seti);
      LMN_IMS_READ(LmnInstrVar, instr, list_num);

      vec_init(&links, list_num);
      for (i = 0; i < list_num; i++) {
        LmnInstrVar t;
        LMN_IMS_READ(LmnInstrVar, instr, t);
        vec_push(&links, (LmnWord)t); /* TODO: vector_initの仕様変更に伴い変更する */
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
      /* TODO: retsetがHash Setである意味は?　ベクタでいいのでは？ */
      LmnInstrVar seti, list_num, memi, enti;
      Vector links; /* src list */
      unsigned int i;
      LMN_IMS_READ(LmnInstrVar, instr, seti);
      LMN_IMS_READ(LmnInstrVar, instr, list_num);

      vec_init(&links, list_num);
      /* TODO: このリストを読み込む部分は *NULLと共通化するべき */
      for (i = 0; i < list_num; i++) {
        LMN_IMS_READ(LmnInstrVar, instr, enti);
        vec_push(&links, (LmnWord)enti); /* TODO: vector_initの仕様変更に伴い変更する */
      }

      LMN_IMS_READ(LmnInstrVar, instr, memi);
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
      if (interpret(instr, next_instr)) return TRUE;
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
      LMN_IMS_READ(LmnInstrVar, instr, mem);
      LMN_IMS_READ(LmnInstrVar, instr, atom);
      LMN_IMS_READ(lmn_interned_str, instr, memn);
      
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

      LMN_IMS_READ(LmnInstrVar, instr, mem1);
      LMN_IMS_READ(LmnInstrVar, instr, mem2);
      LMN_IMS_READ(LmnInstrVar, instr, memt);
      LMN_IMS_READ(lmn_interned_str, instr, memn);

      mp = ((LmnMembrane*)wt[mem2])->child_head;
      while (mp) {
        REF_CAST(LmnMembrane *, wt[mem1]) = mp;
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
      LmnWord ap;
      LmnLinkAttr attr;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnLinkAttr, instr, attr);
      if (LMN_ATTR_IS_DATA(attr)) {
        READ_DATA_ATOM(ap, attr);
      } else { /* symbol atom */
        LmnFunctor f;

      	LMN_IMS_READ(LmnFunctor, instr, f);
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
      *next_instr = instr;
      return TRUE;
    case INSTR_ENQUEUEATOM:
    {
      LmnInstrVar atom;
  
      LMN_IMS_READ(LmnInstrVar, instr, atom);
      /* do nothing */
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

      lmn_mem_remove_atom((LmnMembrane*)wt[memi], wt[atomi], at[atomi]);
      break;
    }
    case INSTR_FREEATOM:
    {
      LmnInstrVar atomi;

      LMN_IMS_READ(LmnInstrVar, instr, atomi);

      lmn_free_atom(wt[atomi], at[atomi]);
      break;
    }
    case INSTR_REMOVEMEM:
    {
      LmnInstrVar memi;
      LmnMembrane *mp;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
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

      LMN_IMS_READ(LmnInstrVar, instr, memi);

      mp = (LmnMembrane*)wt[memi];
      lmn_mem_free(mp);
      break;
    }
    case INSTR_ENQUEUEMEM:
    {
      LmnInstrVar memi;
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      memstack_push((LmnMembrane *)wt[memi]);
      /* TODO: 何する -> pushする */
      break;
    }
    case INSTR_UNLOCKMEM:
    {
      LmnInstrVar memi;
      LMN_IMS_READ(LmnInstrVar, instr, memi);
      /* TODO: 何する -> 何もしない */
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
      LmnByte attr;

      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);
      LMN_IMS_READ(LmnInstrVar, instr, pos1);
      LMN_IMS_READ(LmnInstrVar, instr, pos2);

      attr = LMN_ATOM_GET_LINK_ATTR(wt[atom2], pos1);
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
      unsigned int i, atom_num;
      BOOL ret_flag; /* freeをたくさん書くのを避けるため */
      LmnInstrVar funci, srclisti, avolisti;
      Vector *srcvec, *avovec; 
      HashSet /*avoset, */visited_atoms;
      Vector stack, visited_root;
      LinkObj *start;
      LMN_IMS_READ(LmnInstrVar, instr, funci);
      LMN_IMS_READ(LmnInstrVar, instr, srclisti);
      LMN_IMS_READ(LmnInstrVar, instr, avolisti);

      srcvec = (Vector*) wt[srclisti];
      avovec = (Vector*) wt[avolisti];
      /*hashset_init(&avoset, 16);
      for(i = 0; i < avovec->num; i++) {
        hashset_add(&avoset, vec_get(avovec, i));
      }*/

      vec_init(&stack, 16);
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
      ret_flag=TRUE;
      while(stack.num!=0) {
        LinkObj* lo = (LinkObj *)vec_pop(&stack);

        if(hashset_contains(&visited_atoms, (HashKeyType)lo->ap)) {
          LMN_FREE(lo);
          continue;
        }

        /*if(hashset_contains(&avoset, (HashKeyType)LMN_ATOM_GET_LINK(lo->ap, LMN_ATOM_GET_LINK_ATTR(lo->ap, lo->pos))) ||
            LMN_IS_PROXY_FUNCTOR(LMN_ATOM_GET_FUNCTOR(lo->ap))) {
          LMN_FREE(lo);
          ret_flag=FALSE;
          break;
        }*/
        for(i = 0; i < avovec->num; i++) {
          LmnAtomPtr avolink = (LmnAtomPtr)wt[vec_get(avovec, i)];
          LmnLinkAttr avoattr = at[vec_get(avovec, i)];
          if((LmnAtomPtr)LMN_ATOM_GET_LINK(lo->ap, LMN_ATOM_GET_LINK_ATTR(lo->ap, lo->pos)) == avolink &&
              LMN_ATOM_GET_LINK_ATTR(lo->ap, LMN_ATOM_GET_LINK_ATTR(lo->ap, lo->pos)) == avoattr) {
            ret_flag=FALSE;
            break;
          }
        }
        if(!ret_flag) break;

        if(LMN_IS_PROXY_FUNCTOR(LMN_ATOM_GET_FUNCTOR(lo->ap))) {
          LMN_FREE(lo);
          ret_flag=FALSE;
          break;
        }

        for(i = 0; i < visited_root.num; i++) {
          unsigned int index = vec_get(srcvec, i);
          if (lo->ap == (LmnWord)LMN_ATOM_GET_LINK((LmnAtomPtr)wt[index], at[index])
              && lo->pos == LMN_ATOM_GET_LINK_ATTR((LmnAtomPtr)wt[index], at[index])) {
            vec_set(&visited_root, i, TRUE);
            goto ISGROUND_CONT;
          }
        }

        atom_num++;
        hashset_add(&visited_atoms, (LmnWord)lo->ap);

        for(i = 0; i < LMN_ATOM_GET_ARITY(lo->ap); i++) {
          LinkObj *next;
          if (i == lo->pos)
            continue;
          if(!LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(lo->ap, i))) { /* data atom は積まない */
            next = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATTR_GET_VALUE(LMN_ATOM_GET_LINK_ATTR(lo->ap, i)));
            vec_push(&stack, (LmnWord)next);
          }
        }
ISGROUND_CONT:
        LMN_FREE(lo);
      } /* main loop: end */
      for(i = 0; i < visited_root.num; i++) {
        if(!vec_get(&visited_root, i)) {
          ret_flag=FALSE;
          break;
        }
      }
      /*hashset_destroy(&avoset);*/
      hashset_destroy(&visited_atoms);
      vec_destroy(&stack);
      vec_destroy(&visited_root);
      if(!ret_flag) return FALSE;
      wt[funci] = (LmnWord)atom_num;
      at[funci] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_EQGROUND:
    case INSTR_NEQGROUND:
    {
      unsigned int i, j;
      BOOL ret_flag = TRUE; /* 資源解放を一箇所に集めるため */
      LmnInstrVar srci, dsti;
      Vector *srcv, *dstv; /* 比較元、比較先 */
      Vector stack1, stack2;
      SimpleHashtbl map; /* 比較元→比較先 */
      LinkObj *start1, *start2;
      LMN_IMS_READ(LmnInstrVar, instr, srci);
      LMN_IMS_READ(LmnInstrVar, instr, dsti);

      srcv = (Vector *)wt[srci];
      dstv = (Vector *)wt[dsti];
      hashtbl_init(&map, 256);

      vec_init(&stack1, 16);
      vec_init(&stack2, 16);
      start1 = LinkObj_make((LmnWord)wt[vec_get(srcv, 0)], at[vec_get(srcv, 0)]);
      start2 = LinkObj_make((LmnWord)wt[vec_get(dstv, 0)], at[vec_get(dstv, 0)]);
      if (!LMN_ATTR_IS_DATA(start1->pos) && !LMN_ATTR_IS_DATA(start2->pos)) {
        vec_push(&stack1, (LmnWord)start1);
        vec_push(&stack2, (LmnWord)start2);
      }
      else { /* data atom は積まない */
        if(!lmn_eq_func(start1->ap, start1->pos, start2->ap, start2->pos)) ret_flag = FALSE;
        LMN_FREE(start1);
        LMN_FREE(start2);
      }
      while(stack1.num != 0) {
        LinkObj *l1 = (LinkObj *)vec_pop(&stack1);
        LinkObj *l2 = (LinkObj *)vec_pop(&stack2);
        BOOL contains1 = FALSE;
        BOOL contains2 = FALSE;

        for(i = 0; i < srcv->num; i++) {
          unsigned int index = vec_get(srcv, i);
          if (l1->ap == (LmnWord)LMN_ATOM_GET_LINK((LmnAtomPtr)wt[index], at[index])
              && l1->pos == LMN_ATOM_GET_LINK_ATTR((LmnAtomPtr)wt[index], at[index])) {
              contains1 = TRUE;
            break;
          }
        }
        for(j = 0; j < dstv->num; j++) {
          unsigned int index = vec_get(dstv, j);
          if (l2->ap == (LmnWord)LMN_ATOM_GET_LINK((LmnAtomPtr)wt[index], at[index])
              && l2->pos == LMN_ATOM_GET_LINK_ATTR((LmnAtomPtr)wt[index], at[index])) {
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
        if(l1->pos != l2->pos){ /* 引数 */
          LMN_FREE(l1); LMN_FREE(l2);
          ret_flag = FALSE;
          break;
        }
        if(LMN_ATOM_GET_FUNCTOR(l1->ap) != LMN_ATOM_GET_FUNCTOR(l2->ap)){ /* ファンクタ */
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
          if (!LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(l1->ap, i)) && !LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(l1->ap, i))) {
            n1 = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(l1->ap, i), LMN_ATTR_GET_VALUE(LMN_ATOM_GET_LINK_ATTR(l1->ap, i)));
            n2 = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(l2->ap, i), LMN_ATTR_GET_VALUE(LMN_ATOM_GET_LINK_ATTR(l2->ap, i)));
            vec_push(&stack1, (LmnWord)n1);
            vec_push(&stack2, (LmnWord)n2);
          }
          else { /* data atom は積まない */
            if(!lmn_eq_func(LMN_ATOM_GET_LINK(l1->ap, i), LMN_ATOM_GET_LINK_ATTR(l1->ap, i),
                  LMN_ATOM_GET_LINK(l2->ap, i), LMN_ATOM_GET_LINK_ATTR(l2->ap, i))) {
              LMN_FREE(l1); LMN_FREE(l2);
              ret_flag = FALSE;
              goto EQGROUND_NEQGROUND_BREAK;
            }
          }
        }
        LMN_FREE(l1); LMN_FREE(l2);
      }
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
      Vector *srcvec, *dstlovec, *retvec;
      SimpleHashtbl *atommap = LMN_MALLOC(SimpleHashtbl);
      Vector stack;
      LinkObj *start;
      LmnAtomPtr cpatom;
      LMN_IMS_READ(LmnInstrVar, instr, dstlist);
      LMN_IMS_READ(LmnInstrVar, instr, srclist);
      LMN_IMS_READ(LmnInstrVar, instr, memi);

      srcvec = (Vector *)wt[srclist];
      
      vec_init(&stack, 16);

      hashtbl_init(atommap, 256);
      /* atommapの初期設定：ループ内で親アトムを参照する必要があるため */
      start = LinkObj_make((LmnWord)wt[vec_get(srcvec, 0)], (LmnLinkAttr)at[vec_get(srcvec, 0)]);
      cpatom = (LmnAtomPtr)lmn_copy_atom(start->ap, start->pos);
      /* EFFIENCY: lmn_mem_push_symbol_atom とかで置き換える*/
      lmn_mem_push_atom((LmnMembrane *)wt[memi], (LmnWord)cpatom, start->pos);
      hashtbl_put(atommap, (HashKeyType)start->ap, (HashValueType)cpatom);
      if (!LMN_ATTR_IS_DATA(start->pos)) { /* data atom でない場合 */
        for(i = 0; i < LMN_ATOM_GET_ARITY(cpatom); i++) {
          if(start->pos == i)
            continue;
          else {
            LmnLinkAttr attr = LMN_ATOM_GET_LINK_ATTR(start->ap, i);
            if (!LMN_ATTR_IS_DATA(attr)) {
              LinkObj *next = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(start->ap, i), LMN_ATOM_GET_LINK_ATTR(start->ap, i));
              vec_push(&stack, (LmnWord)next);
            }
            else { /* data atom はスタックに積まない */
              LmnWord cpdata = lmn_copy_atom(LMN_ATOM_GET_LINK(start->ap, i), attr);
              /* EFFIENCY: lmn_mem_push_symbol_atom とかで置き換える*/
              lmn_mem_push_atom((LmnMembrane *)wt[memi], cpdata, attr);
              hashtbl_put(atommap, (HashKeyType)LMN_ATOM_GET_LINK(start->ap, i), (HashValueType)cpatom);
              LMN_ATOM_SET_LINK(cpatom, i, (LmnWord)cpdata);
              LMN_ATOM_SET_LINK_ATTR(cpatom, i, attr);
            }
          }
        }
      }
      /* atommapの初期設定：ここまで */

      while(stack.num != 0) {
        LinkObj *lo = (LinkObj *)vec_pop(&stack);
        for(i = 0; i < srcvec->num; i++){
          unsigned int index = vec_get(srcvec, i);
          if (lo->ap == (LmnWord)LMN_ATOM_GET_LINK((LmnAtomPtr)wt[index], at[index])
          && lo->pos == LMN_ATOM_GET_LINK_ATTR((LmnAtomPtr)wt[index], at[index])) {
            goto COPYGROUND_CONT;
	        }
        }

        if(!hashtbl_contains(atommap, (HashKeyType)lo->ap)) {
          /* 親アトム */
          LmnAtomPtr cpbuddy = (LmnAtomPtr)hashtbl_get(atommap, (HashKeyType)(LmnAtomPtr)LMN_ATOM_GET_LINK(lo->ap, lo->pos));
          cpatom = (LmnAtomPtr)lmn_copy_atom(lo->ap, lo->pos);
          /* EFFIENCY: lmn_mem_push_symbol_atom とかで置き換える*/
          lmn_mem_push_atom((LmnMembrane *)wt[memi], (LmnWord)cpatom, lo->pos);

          hashtbl_put(atommap, (HashKeyType)lo->ap, (HashValueType)cpatom);
          LMN_ATOM_SET_LINK(cpbuddy, LMN_ATOM_GET_LINK_ATTR(lo->ap, lo->pos), (LmnWord)cpatom);
          LMN_ATOM_SET_LINK_ATTR(cpbuddy, LMN_ATOM_GET_LINK_ATTR(lo->ap, lo->pos), lo->pos);
          for(i = 0; i < LMN_ATOM_GET_ARITY(cpatom); i++) {
            if (!LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(lo->ap, i))) {
              LinkObj *next = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATOM_GET_LINK_ATTR(lo->ap, i));
              vec_push(&stack, (LmnWord)next);
            }
            else { /* data atom はスタックに積まない */
              LmnAtomPtr cpdata = (LmnAtomPtr)lmn_copy_atom(LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATOM_GET_LINK_ATTR(lo->ap, i));
              hashtbl_put(atommap, (HashKeyType)LMN_ATOM_GET_LINK(lo->ap, i), (HashValueType)cpatom);
              LMN_ATOM_SET_LINK(cpatom, i, (LmnWord)cpdata);
              LMN_ATOM_SET_LINK_ATTR(cpatom, i, LMN_ATOM_GET_LINK_ATTR(lo->ap, i));
            }
          }
        }
        else {
          /* 親アトム */
          LmnAtomPtr cpbuddy = (LmnAtomPtr)hashtbl_get(atommap, (HashKeyType)(LmnAtomPtr)LMN_ATOM_GET_LINK(lo->ap, lo->pos));
          cpatom = (LmnAtomPtr)hashtbl_get(atommap, (HashKeyType)lo->ap);
          LMN_ATOM_SET_LINK(cpbuddy, LMN_ATOM_GET_LINK_ATTR(lo->ap, lo->pos), (LmnWord)cpatom);
          LMN_ATOM_SET_LINK_ATTR(cpbuddy, LMN_ATOM_GET_LINK_ATTR(lo->ap, lo->pos), lo->pos);
        }
COPYGROUND_CONT:
        LMN_FREE(lo);
      }
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
        /* この作業も必要なのか… */
        while(dstlovec->num) {
          LMN_FREE(vec_get(dstlovec, dstlovec->num-1));
          dstlovec->num--;
        }
        vec_free(dstlovec);
        /* TODO: 要確認. freeはdeleteconnectorsで行うので大丈夫? */
        /* hashtbl_free(atommap); */
        vec_free(retvec);
        return TRUE;
      }
      else assert(0);
      break;
    }
    case INSTR_REMOVEGROUND:
    case INSTR_FREEGROUND:
    {
      /* メモリ解放 */
      unsigned int i;
      LmnInstrVar listi, memi;
      Vector *srcvec;
      HashSet visited_atoms;
      Vector stack;
      LinkObj *start;
      LMN_IMS_READ(LmnInstrVar, instr, listi);
      if (INSTR_REMOVEGROUND == op) {
        LMN_IMS_READ(LmnInstrVar, instr, memi);
      }
      srcvec = (Vector *)wt[listi];
      
      vec_init(&stack, 16);
      start = LinkObj_make((LmnWord)wt[vec_get(srcvec, 0)], at[vec_get(srcvec, 0)]);
      if(!LMN_ATTR_IS_DATA(start->pos)) {
        vec_push(&stack, (LmnWord)start);
      }
      else { /* data atom は積まない */
        switch (op) {
          case INSTR_REMOVEGROUND:
            lmn_mem_remove_atom((LmnMembrane*)wt[memi], start->ap, start->pos);
            break;
          case INSTR_FREEGROUND:
            /* data atomは接続されたsymbol atomがfreeされると一緒にfreeされるので放置 */
            /*lmn_free_atom(start->ap, start->pos);*/
            break;
        }
      }

      hashset_init(&visited_atoms, 256);

      while(stack.num != 0) {
        LinkObj *lo = (LinkObj *)vec_pop(&stack);

        if(hashset_contains(&visited_atoms, (HashKeyType)lo->ap))
          continue;
        hashset_add(&visited_atoms, (LmnWord)lo->ap);
        for(i = 0; i < srcvec->num; i++) {
          unsigned int index = vec_get(srcvec, i);
          if (lo->ap == (LmnWord)LMN_ATOM_GET_LINK((LmnAtomPtr)wt[index], at[index])
              && lo->pos == LMN_ATOM_GET_LINK_ATTR((LmnAtomPtr)wt[index], at[index])) {
            goto REMOVE_FREE_GROUND_CONT;
          }
        }

        for(i = 0; i < LMN_ATOM_GET_ARITY(lo->ap); i++) {
          LinkObj *next;
          if(i == lo->pos)
            continue;
          if(!LMN_ATTR_IS_DATA(LMN_ATOM_GET_LINK_ATTR(lo->ap, i))) {
            next = LinkObj_make((LmnWord)LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATTR_GET_VALUE(LMN_ATOM_GET_LINK_ATTR(lo->ap, i)));
            vec_push(&stack, (LmnWord)next); 
          }
          else { /* data atom は積まない */
            switch (op) {
              case INSTR_REMOVEGROUND:
                lmn_mem_remove_atom((LmnMembrane*)wt[memi], (LmnWord)LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATOM_GET_LINK_ATTR(lo->ap, i));
                break;
              case INSTR_FREEGROUND:
                /* data atomは接続されたsymbol atomがfreeされると一緒にfreeされるので放置 */
                /*lmn_free_atom((LmnWord)LMN_ATOM_GET_LINK(lo->ap, i), LMN_ATOM_GET_LINK_ATTR(lo->ap, i));*/
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
      }
      vec_destroy(&stack);
      hashset_destroy(&visited_atoms);
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
      wt[atom1] = lmn_copy_atom(wt[atom2], at[atom2]);
      lmn_mem_push_atom((LmnMembrane *)wt[memi], wt[atom1], at[atom1]);
      break;
    }
    case INSTR_EQATOM:
    {
      LmnInstrVar atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

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
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);
 
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
      Vector *listvec = vec_make_default();
      LMN_IMS_READ(LmnInstrVar, instr, listi);
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
      LMN_IMS_READ(LmnInstrVar, instr, listi);
      LMN_IMS_READ(LmnInstrVar, instr, srci);
      vec_push((Vector *)wt[listi], srci);
      break;
    }
    case INSTR_GETFROMLIST:
    {
      LmnInstrVar dsti, listi, posi;
      LMN_IMS_READ(LmnInstrVar, instr, dsti);
      LMN_IMS_READ(LmnInstrVar, instr, listi);
      LMN_IMS_READ(LmnInstrVar, instr, posi);

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
    case INSTR_INEG:
    {
      LmnInstrVar dstatom, atomi;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      wt[dstatom] = (LmnWord)-(int)atomi;
      at[dstatom] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_IMOD:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] % (int)wt[atom2]);
      at[dstatom] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_INOT:
    {
      LmnInstrVar dstatom, atomi;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      wt[dstatom] = (LmnWord)~(int)atomi;
      at[dstatom] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_IAND:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] & (int)wt[atom2]);
      at[dstatom] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_IOR:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] | (int)wt[atom2]);
      at[dstatom] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_IXOR:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)((int)wt[atom1] ^ (int)wt[atom2]);
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
    case  INSTR_FADD:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)LMN_MALLOC(double);
      *(double *)wt[dstatom] = *(double *)wt[atom1] + *(double *)wt[atom2];
      at[dstatom] = LMN_ATOM_DBL_ATTR;
      break;
    }
    case  INSTR_FSUB:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)LMN_MALLOC(double);
      *(double *)wt[dstatom] = *(double *)wt[atom1] - *(double *)wt[atom2];
      at[dstatom] = LMN_ATOM_DBL_ATTR;
      break;
    }
    case  INSTR_FMUL:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)LMN_MALLOC(double);
      *(double *)wt[dstatom] = *(double *)wt[atom1] * *(double *)wt[atom2];
      at[dstatom] = LMN_ATOM_DBL_ATTR;
      break;
    }
    case  INSTR_FDIV:
    {
      LmnInstrVar dstatom, atom1, atom2;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      wt[dstatom] = (LmnWord)LMN_MALLOC(double);
      *(double *)wt[dstatom] = *(double *)wt[atom1] / *(double *)wt[atom2];
      at[dstatom] = LMN_ATOM_DBL_ATTR;
      break;
    }
    case  INSTR_FNEG:
    {
      LmnInstrVar dstatom, atomi;
      LMN_IMS_READ(LmnInstrVar, instr, dstatom);
      LMN_IMS_READ(LmnInstrVar, instr, atomi);

      wt[dstatom] = (LmnWord)LMN_MALLOC(double);
      *(double *)wt[dstatom] = -*(double *)wt[atomi];
      at[dstatom] = LMN_ATOM_DBL_ATTR;
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
        exit(EXIT_FAILURE);
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

      LMN_IMS_READ(LmnInstrVar, instr, atom1);
      LMN_IMS_READ(LmnInstrVar, instr, atom2);

      if (!lmn_eq_func(wt[atom1], at[atom1], wt[atom2], at[atom2]))
        return FALSE;
      break;
    }
    case INSTR_GETFUNC:
    {
      LmnInstrVar funci, atomi;

      LMN_IMS_READ(LmnFunctor, instr, funci);
      LMN_IMS_READ(LmnInstrVar, instr, atomi);

      if(LMN_ATTR_IS_DATA(at[atomi])) {
        /* TODO: 要確認. ここで得るファンクタはガード命令中で一時的に使われるだけなので
           double はポインタのコピーで十分なはず */
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
    case INSTR_DELETECONNECTORS:
    {
      LmnInstrVar srcset, srcmap;
      HashSet *delset;
      SimpleHashtbl *delmap;
      HashSetIterator it;
      LMN_IMS_READ(LmnInstrVar, instr, srcset);
      LMN_IMS_READ(LmnInstrVar, instr, srcmap);

      delset = (HashSet *)wt[srcset];
      delmap = (SimpleHashtbl *)wt[srcmap];

      for(it = hashset_iterator(delset); !hashsetiter_isend(&it); hashset_it_next(&it)) {
        LmnAtomPtr orig = (LmnAtomPtr)hashsetiter_entry(&it);
        LmnAtomPtr copy = (LmnAtomPtr)hashtbl_get(delmap, (HashKeyType)orig);
        lmn_mem_unify_symbol_atom_args(copy, 0, copy, 1);
        /* mem がないので仕方なく直接アトムリストをつなぎ変える
           UNIFYアトムはnatomに含まれないので大丈夫 */
        LMN_ATOM_SET_PREV(LMN_ATOM_GET_NEXT(copy), LMN_ATOM_GET_PREV(copy));
        LMN_ATOM_SET_NEXT(LMN_ATOM_GET_PREV(copy), LMN_ATOM_GET_NEXT(copy));

        lmn_delete_atom(orig);
      }

      /* TODO: 要確認. freeはdeleteconnectorsで行うので大丈夫? */
      hashtbl_free(delmap);
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
      lmn_mem_push_atom((LmnMembrane *)wt[memi], wt[atomi], at[atomi]);
      break;
    }
    case INSTR_MOVECELLS:
    {
      LmnInstrVar destmemi, srcmemi;

      LMN_IMS_READ(LmnInstrVar, instr, destmemi);
      LMN_IMS_READ(LmnInstrVar, instr, srcmemi);
      LMN_ASSERT(wt[destmemi] != wt[srcmemi]);
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
    case INSTR_LOOKUPLINK:
    {
      LmnInstrVar destlinki, tbli, srclinki;
      
      LMN_IMS_READ(LmnInstrVar, instr, destlinki);
      LMN_IMS_READ(LmnInstrVar, instr, tbli);
      LMN_IMS_READ(LmnInstrVar, instr, srclinki);

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

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      vec_clear(&((LmnMembrane *)wt[memi])->rulesets);
      break;
    }
    case INSTR_DROPMEM:
    {
      LmnInstrVar memi;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      lmn_mem_drop((LmnMembrane *)wt[memi]);
      break;
    }
    case INSTR_TESTMEM:
    {
      LmnInstrVar memi, atomi;

      LMN_IMS_READ(LmnInstrVar, instr, memi);
      LMN_IMS_READ(LmnInstrVar, instr, atomi);
      LMN_ASSERT(!LMN_ATTR_IS_DATA(at[atomi]));
      LMN_ASSERT(LMN_IS_PROXY_FUNCTOR(LMN_ATOM_GET_FUNCTOR(wt[atomi])));

      if (LMN_PROXY_GET_MEM(wt[atomi]) != (LmnMembrane *)wt[memi]) return FALSE;
      break;
    }
    case INSTR_IADDFUNC:
    {
      LmnInstrVar desti, i0, i1;

      LMN_IMS_READ(LmnInstrVar, instr, desti);
      LMN_IMS_READ(LmnInstrVar, instr, i0);
      LMN_IMS_READ(LmnInstrVar, instr, i1);
      LMN_ASSERT(at[i0] == LMN_ATOM_INT_ATTR);
      LMN_ASSERT(at[i1] == LMN_ATOM_INT_ATTR);
      wt[desti] = wt[i0] + wt[i1];
      at[desti] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_ISUBFUNC:
    {
      LmnInstrVar desti, i0, i1;

      LMN_IMS_READ(LmnInstrVar, instr, desti);
      LMN_IMS_READ(LmnInstrVar, instr, i0);
      LMN_IMS_READ(LmnInstrVar, instr, i1);
      LMN_ASSERT(at[i0] == LMN_ATOM_INT_ATTR);
      LMN_ASSERT(at[i1] == LMN_ATOM_INT_ATTR);
      wt[desti] = wt[i0] - wt[i1];
      at[desti] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_IMULFUNC:
    {
      LmnInstrVar desti, i0, i1;

      LMN_IMS_READ(LmnInstrVar, instr, desti);
      LMN_IMS_READ(LmnInstrVar, instr, i0);
      LMN_IMS_READ(LmnInstrVar, instr, i1);
      LMN_ASSERT(at[i0] == LMN_ATOM_INT_ATTR);
      LMN_ASSERT(at[i1] == LMN_ATOM_INT_ATTR);
      wt[desti] = wt[i0] * wt[i1];
      at[desti] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_IDIVFUNC:
    {
      LmnInstrVar desti, i0, i1;

      LMN_IMS_READ(LmnInstrVar, instr, desti);
      LMN_IMS_READ(LmnInstrVar, instr, i0);
      LMN_IMS_READ(LmnInstrVar, instr, i1);
      LMN_ASSERT(at[i0] == LMN_ATOM_INT_ATTR);
      LMN_ASSERT(at[i1] == LMN_ATOM_INT_ATTR);
      wt[desti] = wt[i0] / wt[i1];
      at[desti] = LMN_ATOM_INT_ATTR;
      break;
    }
    case INSTR_IMODFUNC:
    {
      LmnInstrVar desti, i0, i1;

      LMN_IMS_READ(LmnInstrVar, instr, desti);
      LMN_IMS_READ(LmnInstrVar, instr, i0);
      LMN_IMS_READ(LmnInstrVar, instr, i1);
      LMN_ASSERT(at[i0] == LMN_ATOM_INT_ATTR);
      LMN_ASSERT(at[i1] == LMN_ATOM_INT_ATTR);
      wt[desti] = wt[i0] % wt[i1];
      at[desti] = LMN_ATOM_INT_ATTR;
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
/*     lmn_mem_dump((LmnMembrane *)wt[0]); */
/*     print_wt(); */

    #ifdef DEBUG
/*     print_wt(); */
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
