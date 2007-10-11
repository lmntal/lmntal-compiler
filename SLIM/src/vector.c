/*
 * Vector<LmnWord>
 */

#include "vector.h"

/* init */
Vector *vec_init(Vector *vec, unsigned int init_size) {
  vec->tbl = LMN_NALLOC(LmnWord, init_size);
  vec->num = 0;
  vec->cap = init_size;
  return vec;
}

/* make */
Vector *vec_make(unsigned int init_size) {
  Vector* vec = LMN_MALLOC(Vector);
  return vec_init(vec, init_size);
}

/* extend (static) */
static inline void vec_extend(Vector *vec) {
  vec->cap *= 2;
  vec->tbl = LMN_REALLOC(LmnWord, vec->tbl, vec->cap);
}

/* push */
void vec_push(Vector *vec, LmnWord keyp) {
  if(vec->num == vec->cap) {
    vec_extend(vec);
  }
  (vec->tbl)[vec->num] = keyp;
  vec->num++;
}

/* reduce (static) */
static inline void vec_reduce(Vector *vec) {
  vec->cap /= 2;
  vec->tbl = LMN_REALLOC(LmnWord, vec->tbl, vec->cap);
}

/* pop */
LmnWord vec_pop(Vector *vec) {
  LmnWord ret;
  if(vec->num <= vec->cap/2) {
    vec_reduce(vec);
  }
  ret = vec_get(vec, (vec->num-1));
  vec->num--;
  return ret;
}

/* set */
void vec_set(Vector *vec, unsigned int index, LmnWord keyp) {
  assert(index < vec->num);
  (vec->tbl)[index] = keyp;
}

/* get */
LmnWord vec_get(Vector *vec, unsigned int index) {
  assert(index < vec->num);
  return(vec->tbl[index]);
}

/* pop all elements from vec */
void vec_clear(Vector *vec) {
  vec->num = 0;
}

/* destroy */
void vec_destroy(Vector *vec) {
  free(vec->tbl);
}

/* free */
void vec_free(Vector *vec) {
  free(vec->tbl);
  free(vec);
}

