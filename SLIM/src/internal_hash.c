/*
 * intrnal_hash.c - hash table for internal use
 */

#include "internal_hash.h"
#include "config.h"
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <stdio.h>

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

#if SIZEOF_LONG == 4
# define EMPTY_KEY 0xffffffffU
#elif SIZEOF_LONG == 8
# define EMPTY_KEY 0xffffffffffffffffU
#endif

#define INT_HASH(val)  ((val)*K)

static void hashtbl_extend(SimpleHashtbl *ht);
static struct HashEntry *hashtbl_get_p(SimpleHashtbl *ht, HashKeyType key);
static HashKeyType round2up(unsigned int n);

void hashtbl_init(SimpleHashtbl *ht, unsigned int init_size)
{
  ht->num = 0;
  ht->cap = round2up(init_size);
  ht->tbl = malloc(sizeof(struct HashEntry) * ht->cap);
  memset(ht->tbl, 0xff, sizeof(struct HashEntry) * ht->cap);
}

void hashtbl_destroy(SimpleHashtbl *ht)
{
  free(ht->tbl);
}

HashValueType hashtbl_get(SimpleHashtbl *ht, HashKeyType key)
{
  return hashtbl_get_p(ht, key)->data;
}

HashValueType hashtbl_get_default(SimpleHashtbl *ht,
                                  HashKeyType key,
                                  HashValueType default_value)
{
  HashEntry *e =  hashtbl_get_p(ht, key);
  if (e->key == EMPTY_KEY) return default_value;
  else return e->data;
}

int hashtbl_contains(SimpleHashtbl *ht, HashKeyType key)
{
  return hashtbl_get_p(ht, key)->key != EMPTY_KEY;
}
 
void hashtbl_put(SimpleHashtbl *ht, HashKeyType key, HashValueType data)
{
  struct HashEntry *e;
#ifdef DEBUG
  assert(key != EMPTY_KEY);
#endif
  e = hashtbl_get_p(ht, key);
  if (e->key == EMPTY_KEY) {
    ht->num++;
    e->key = key;
  }
  e->data = data;

  if (ht->num > ht->cap * LOAD_FACTOR) {
    hashtbl_extend(ht);
  }
}

static struct HashEntry *hashtbl_get_p(SimpleHashtbl *ht, HashKeyType key)
{
  HashKeyType hash_val = INT_HASH(key);
  HashKeyType probe;
  HashKeyType increment = (key | 1) & (ht->cap-1);
  
  for (probe = hash_val & (ht->cap-1);
       ht->tbl[probe].key != EMPTY_KEY && ht->tbl[probe].key != key;
       probe = (probe + increment) & (ht->cap-1)) {
  }

/*   printf("ret = %d\n", probe); */
  return &ht->tbl[probe];
}

static void hashtbl_extend(SimpleHashtbl *ht)
{
  struct HashEntry *tbl;
  unsigned int i, cap;

  if (ht->cap == MAX_CAP) {
    fprintf(stderr, "hashtable capacity overflow\n");
    exit(1);
  }
  
  cap = ht->cap;
  tbl = ht->tbl;
  ht->cap <<= 1;
  ht->tbl = malloc(sizeof(struct HashEntry) *  ht->cap);
  memset(ht->tbl, 0xff, sizeof(struct HashEntry) * ht->cap);

  for (i = 0; i < cap; i++) {
    if (tbl[i].key != EMPTY_KEY) {
      hashtbl_put(ht, tbl[i].key, tbl[i].data);
    }
  }
  free(tbl);
}

static HashKeyType round2up(unsigned int n)
{
  unsigned int v = 1;
  while (v && v < n) {
    v <<= 1;
  }
  if (v == 0) {
    fprintf(stderr, "hashtbl init size too large\n");
    exit(1);
  }
  return v;
}

HashIterator hashtbl_iterator(SimpleHashtbl *ht)
{
  HashIterator iter;
  iter.i = 0;
  iter.ht = ht;
  if (ht->cap > 0 && ht->tbl[iter.i].key == EMPTY_KEY) {
    hashiter_next(&iter);
  }
  return iter;
}

void hashiter_next(HashIterator *iter)
{
  while (++iter->i < iter->ht->cap &&
         iter->ht->tbl[iter->i].key == EMPTY_KEY) ;
}
