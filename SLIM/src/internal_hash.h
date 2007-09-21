/*
 * intrnal_hash.h
 */


#ifndef INTERNAL_HASH_H
#define INTERNAL_HASH_H

typedef unsigned int HashKeyType;
typedef unsigned int HashValueType;

typedef struct HashEntry {
  HashKeyType key;
  HashValueType data;
} HashEntry;

typedef struct SimpleHashtbl {
  struct HashEntry *tbl;
  unsigned int cap, num;
} SimpleHashtbl;

typedef struct HashIterator {
  SimpleHashtbl *ht;
  unsigned int i;
} HashIterator;


void hashtbl_init(SimpleHashtbl *ht, unsigned int init_size);
HashValueType hashtbl_get(SimpleHashtbl *ht, HashKeyType key);
HashValueType hashtbl_get_default(SimpleHashtbl *ht,
                                  HashKeyType key,
                                  HashValueType default_value);
int hashtbl_contains(SimpleHashtbl *ht, HashKeyType key);
void hashtbl_put(SimpleHashtbl *ht, HashKeyType key, HashValueType val);
void hashtbl_destroy(SimpleHashtbl *ht);
#define hashtbl_num(HT) (HT)->num

HashIterator hashtbl_iterator(SimpleHashtbl *ht);
void hashiter_next(HashIterator *iter);
#define hashiter_entry(I) ((I)->ht->tbl[(I)->i])
#define hashiter_isend(I) ((I)->i >= (I)->ht->cap)

#endif /*INTERNAL_HASH_H*/
