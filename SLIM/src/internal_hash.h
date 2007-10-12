/*
 * intrnal_hash.h
 */


#ifndef INTERNAL_HASH_H
#define INTERNAL_HASH_H

/* HashMap */
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
SimpleHashtbl *hashtbl_make(unsigned int init_size);
HashValueType hashtbl_get(SimpleHashtbl *ht, HashKeyType key);
HashValueType hashtbl_get_default(SimpleHashtbl *ht,
                                  HashKeyType key,
                                  HashValueType default_value);
int hashtbl_contains(SimpleHashtbl *ht, HashKeyType key);
void hashtbl_put(SimpleHashtbl *ht, HashKeyType key, HashValueType val);
void hashtbl_destroy(SimpleHashtbl *ht);
void hashtbl_free(SimpleHashtbl *ht);
#define hashtbl_num(HT) (HT)->num

HashIterator hashtbl_iterator(SimpleHashtbl *ht);
void hashtbliter_next(HashIterator *iter);
#define hashtbliter_entry(I) (&((I)->ht->tbl[(I)->i]))
#define hashtbliter_isend(I) ((I)->i >= (I)->ht->cap)

/* HashSet */
typedef struct HashSet {
  HashKeyType* tbl;
  unsigned int cap, num;
} HashSet;

typedef struct HashSetItrator {
  HashSet *set;
  unsigned int i;
} HashSetIterator;

HashSet *hashset_make(unsigned int init_size);
void hashset_init(HashSet *set, unsigned int init_size);
int hashset_contains(HashSet *set, HashKeyType key);
void hashset_add(HashSet *set, HashKeyType key);
void hashset_free(HashSet *set);
void hashset_destroy(HashSet *set);
HashSetIterator hashset_iterator(HashSet *set);
void hashsetiter_next(HashSetIterator *it);
#define hashsetiter_entry(I) ((I)->set->tbl[(I)->i])
#define hashsetiter_isend(I) ((I)->i >= (I)->set->cap)

#endif /*INTERNAL_HASH_H*/
