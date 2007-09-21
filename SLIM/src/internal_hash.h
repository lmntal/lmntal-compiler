/*
 * intrnal_hash.h
 */


#ifndef INTERNAL_HASH_H
#define INTERNAL_HASH_H

#include "lmntal.h"

typedef LmnWord HashKeyType;
typedef LmnWord HashValueType;

struct HashEntry {
  HashKeyType key;
  LmnWord data;
};

typedef struct SimpleHashtbl {
  struct HashEntry *tbl;
  unsigned int cap, num;
} SimpleHashtbl;

typedef struct HashIterator {
  SimpleHashtbl *ht;
  unsigned int i;
} HashIterator;


void hashtbl_init(SimpleHashtbl *ht, unsigned int init_size);
LmnWord hashtbl_find(SimpleHashtbl *ht, HashKeyType key);
BOOL hashtbl_contains(SimpleHashtbl *ht, HashKeyType key);
void hashtbl_put(SimpleHashtbl *ht, HashKeyType key, LmnWord val);
void hashtbl_destroy(SimpleHashtbl *ht);
#define hashtbl_num(HT) (HT)->num

HashIterator hashtbl_iterator(SimpleHashtbl *ht);
void hashiter_next(HashIterator *iter);
#define hashiter_entry(I) ((I)->ht->tbl[(I)->i])
#define hashiter_isend(I) ((I)->i >= (I)->ht->cap)

#endif /*INTERNAL_HASH_H*/
