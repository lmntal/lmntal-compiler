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
 *  LOAD_FACTOR then execute rehash.
 *  
 *  Keys must be positive integer, because hashtable use 0 as the Empty flag.
 */

#define LOAD_FACTOR 0.75
#define K 2654435761UL
/* maximum capacity */
#define MAX_CAP 0x80000000UL
/* 別に最大容量を制限する必要はないが、制限をしない場合は,
   Kが定数なのでインデックスの計算の時に、hash_valを32bitに
   畳み込む必要がある */

#if SIZEOF_LONG == 4
# define EMPTY_KEY 0xffffffffUL
#elif SIZEOF_LONG == 8
# define EMPTY_KEY 0xffffffffffffffffUL
#endif

#define INT_HASH(val)  ((val)*K)

static void hashtbl_extend(SimpleHashtbl *ht);
static struct HashEntry *hashtbl_get_p(SimpleHashtbl *ht, HashKeyType key);
static HashKeyType round2up(unsigned int n);
static HashKeyType* hashset_get_p(HashSet* set, HashKeyType key);

/* HashMap <HashKeyType, HashValueTypr> */
void hashtbl_init(SimpleHashtbl *ht, unsigned int init_size)
{
  ht->num = 0;
  ht->cap = round2up(init_size);
  ht->tbl = (HashEntry *)malloc(sizeof(struct HashEntry) * ht->cap);
  memset(ht->tbl, 0xffU, sizeof(struct HashEntry) * ht->cap);
}

SimpleHashtbl *hashtbl_make(unsigned int init_size)
{
  SimpleHashtbl *ht = (SimpleHashtbl *)malloc(sizeof(SimpleHashtbl));
  hashtbl_init(ht, init_size);
  return ht;
}

void hashtbl_destroy(SimpleHashtbl *ht)
{
  free(ht->tbl);
}

void hashtbl_free(SimpleHashtbl *ht)
{
  free(ht->tbl);
  free(ht);
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
  HashKeyType probe;
  HashKeyType increment = (key | 1) & (ht->cap-1);
  
  for (probe = INT_HASH(key) & (ht->cap-1);
       ht->tbl[probe].key != EMPTY_KEY && ht->tbl[probe].key != key;
       probe = (probe + increment) & (ht->cap-1)) {
  }

  return &ht->tbl[probe];
}

static void hashtbl_extend(SimpleHashtbl *ht)
{
  struct HashEntry *tbl, *e;
  unsigned int i, cap;

  if (ht->cap == MAX_CAP) {
    fprintf(stderr, "hashtable capacity overflow\n");
    exit(1);
  }
  
  cap = ht->cap;
  tbl = ht->tbl;
  ht->cap <<= 1;
  ht->tbl = (HashEntry *)malloc(sizeof(struct HashEntry) *  ht->cap);
  memset(ht->tbl, 0xffU, sizeof(struct HashEntry) * ht->cap);

  for (i = 0; i < cap; i++) {
    if (tbl[i].key != EMPTY_KEY) {
      e = hashtbl_get_p(ht, tbl[i].key);
      e->key = tbl[i].key;
      e->data = tbl[i].data;
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

/* HashSet <HashKeyType> */
void hashset_init(HashSet *set, unsigned int init_size)
{
  set->num = 0;
  set->cap = round2up(init_size);
  set->tbl = (HashKeyType *)malloc(sizeof(HashKeyType) * set->cap);
  memset(set->tbl, 0xffU, sizeof(HashKeyType) * set->cap);
}

HashSet *hashset_make(unsigned int init_size)
{
  HashSet *hs = (HashSet *)malloc(sizeof(HashSet));
  hashset_init(hs, init_size);
  return hs;
}

void hashset_destroy(HashSet *set)
{
  free(set->tbl);
}

void hashset_free(HashSet *set)
{
  free(set->tbl);
  free(set);
}

int hashset_contains(HashSet *set, HashKeyType key)
{
  return *hashset_get_p(set, key) != EMPTY_KEY;
}

static void hashset_extend(HashSet *set)
{
  HashKeyType *tbl, *entry;
  unsigned int i, cap;

  if (set->cap == MAX_CAP) {
    fprintf(stderr, "hashset capacity overflow\n");
    exit(1);
  }
  
  cap = set->cap;
  tbl = set->tbl;
  set->cap <<= 1;
  set->tbl = (HashKeyType *)malloc(sizeof(HashKeyType) *  set->cap);
  memset(set->tbl, 0xffU, sizeof(HashKeyType) * set->cap);

  for(i = 0; i < cap; i++) {
    if(tbl[i] != EMPTY_KEY) {
      entry = hashset_get_p(set, tbl[i]); /* 新しいindex */
      *entry = tbl[i];
    }
  }
  free(tbl);
}

void hashset_add(HashSet* set, HashKeyType key) {
  HashKeyType* entry;
#ifdef DEBUG
  assert(key != EMPTY_KEY);
#endif  
  entry = hashset_get_p(set, key);
  if(*entry == EMPTY_KEY) {
    set->num++;
    *entry = key;
  }
  if(set->num > set->cap * LOAD_FACTOR) {
    hashset_extend(set);
  }
}

HashSetIterator hashset_iterator(HashSet *set) {
  HashSetIterator it;
  it.i = 0;
  it.set = set;
  if(set->cap > 0 && set->tbl[it.i] == EMPTY_KEY) {
    hashsetiter_next(&it);
  }
  return it;
}

void hashsetiter_next(HashSetIterator *it) {
  while (++it->i < it->set->cap && it->set->tbl[it->i] == EMPTY_KEY);
}

HashKeyType* hashset_get_p(HashSet* set, HashKeyType key)
{
  HashKeyType probe;
  HashKeyType increment = (key | 1) & (set->cap-1);
  
  for (probe = INT_HASH(key) & (set->cap-1);
       set->tbl[probe] != EMPTY_KEY && set->tbl[probe] != key;
       probe = (probe + increment) & (set->cap-1)) {
  }
  return &set->tbl[probe];
}
