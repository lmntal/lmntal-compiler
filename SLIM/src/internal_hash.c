/*
 * intrnal_hash.c - hash table for internal use
 */

#include "internal_hash.h"

/* Hashtable
 *  
 *  This hashtable uses 'open addressing with double hasing'.
 *  Once key&value is inserted to hashtable, it can not be deleted.
 *  The size of hashtable is always power of 2. If # of elements / table size >
 *  LOAD_FACTOR then rehash function will be called, and table size extended.
 *  
 *  Keys must be positive integer, because hashtable use 0 as the Empty flag.
 */

#define LOAD_FACTOR 0.75
#define K 2654435761U
/* maximum capacity */
#define MAX_CAP 0x80000000
/* 別に最大容量を制限する必要はないが、制限をしない場合は,
   Kが定数なのでインデックスの計算の時に、hash_valを32bitに
   畳み込む必要がある */

#define INT_HASH(val)  ((val)*K)

#define IS_EMPTY(Entry) (!(Entry).key)

static void hashtbl_extend(struct SimpleHashtbl *ht);
static struct HashEntry *hashtbl_find_p(struct SimpleHashtbl *ht, HashKeyType key);
static LmnWord round2up(unsigned int n);

void hashtbl_init(struct SimpleHashtbl *ht, unsigned int init_size)
{
  ht->num = 0;
  ht->cap = round2up(init_size);
  ht->tbl = LMN_NALLOC(struct HashEntry, ht->cap);
  memset(ht->tbl, 0, sizeof(struct HashEntry) * ht->cap);
}

void hashtbl_free(struct SimpleHashtbl *ht)
{
  LMN_FREE(ht->tbl);
}

LmnWord hashtbl_find(struct SimpleHashtbl *ht, HashKeyType key)
{
  return hashtbl_find_p(ht, key)->data;
}

BOOL hashtbl_contains(struct SimpleHashtbl *ht, HashKeyType key)
{
  return !IS_EMPTY(*hashtbl_find_p(ht, key));
}
 
void hashtbl_put(struct SimpleHashtbl *ht, HashKeyType key, LmnWord data)
{
  struct HashEntry e;
  e.key = key;
  e.data = data;
  *hashtbl_find_p(ht, key) = e;
  ht->num++;
  if (ht->num > ht->cap * LOAD_FACTOR) {
    hashtbl_extend(ht);
  }
}

/* findと存在検査を同時に行う必要がある場合には,
   <BOOL,data> の組を返すようにする。*/
static struct HashEntry *hashtbl_find_p(struct SimpleHashtbl *ht, HashKeyType key)
{
  HashKeyType hash_val = INT_HASH(key);
  HashKeyType probe;
  HashKeyType increment = (key | 1) & (ht->cap-1);
  

  LMN_ASSERT(key); /* key must be non 0 */
  for (probe = hash_val & (ht->cap-1);
       !IS_EMPTY(ht->tbl[probe]) && ht->tbl[probe].key != key;
       hash_val = (probe + increment)) {
  }

/*   printf("ret = %d\n", probe); */
  return &ht->tbl[probe];
}

static void hashtbl_extend(struct SimpleHashtbl *ht)
{
  struct HashEntry *tbl;
  unsigned int i, cap;

  if (ht->cap == MAX_CAP) {
    lmn_fatal("hashtable capacity overflow\n");
  }
  cap = ht->cap;
  tbl = ht->tbl;
  ht->cap <<= 1;
  ht->tbl = LMN_NALLOC(struct HashEntry, ht->cap);
  memset(ht->tbl, 0, sizeof(struct HashEntry) * ht->cap);

  for (i = 0; i < cap; i++) {
    if (!IS_EMPTY(tbl[i])) {
      hashtbl_put(ht, tbl[i].key, tbl[i].data);
    }
  }
  LMN_FREE(tbl);
}

static LmnWord round2up(unsigned int n)
{
  unsigned int v = 1;
  while (v && v < n) {
    v <<= 1;
  }
  if (v == 0) lmn_fatal("hashtbl init size too large\n");
  return v;
}
