/*
 * intrnal_hash.h
 */


#ifndef INTERNAL_HASH_H
#define INTERNAL_HASH_H

#include "lmntal.h"

typedef LmnWord HashKeyType;

struct HashEntry {
  HashKeyType key;
  LmnWord data;
};

struct SimpleHashtbl {
  struct HashEntry *tbl;
  unsigned int cap, num;
};

void hashtbl_init(struct SimpleHashtbl *ht, unsigned int init_size);
LmnWord hashtbl_find(struct SimpleHashtbl *ht, HashKeyType key);
BOOL hashtbl_contains(struct SimpleHashtbl *ht, HashKeyType key);
void hashtbl_put(struct SimpleHashtbl *ht, HashKeyType key, LmnWord val);
void hashtbl_free(struct SimpleHashtbl *ht);
  
#endif /*INTERNAL_HASH_H*/
