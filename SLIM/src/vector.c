#include "vector.h"

/* init */
Vector *vec_init(Vector *vec, unsigned int init_size) {
  vec->tbl = LMN_NALLOC(LmnWord, init_size);
  vec->num = 0;
  vec->cap = init_size;
  return vec;
}

/* make */
Vector* vec_make(unsigned int init_size) {
  Vector* vec = LMN_MALLOC(Vector);
  return vec_init(vec, init_size);
}

Vector* vec_make_default() {
  return vec_make(16);
}

/* extend */
static void vec_extend(Vector* vec) {
  vec->cap *= 2;
  vec->tbl = LMN_REALLOC(LmnWord, vec->tbl, vec->cap);
}

/* push */
void vec_push(Vector* vec, LmnWord keyp) {
  if(vec->num == vec->cap) {
    vec_extend(vec);
  }
  (vec->tbl)[vec->num] = keyp;
  vec->num++;
}

/* pop */
LmnWord vec_pop(Vector *vec) {
  LmnWord ret;
  if(vec->num <= vec->cap/2) {
    vec->cap /= 2;
    vec->tbl = LMN_REALLOC(LmnWord, vec->tbl, vec->cap);
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

/* indexof */
int vec_indexof(Vector *vec, LmnWord keyp) {
  unsigned int i;
  for(i = 0; i < vec->num; i++) {
    if(vec->tbl[i] == keyp)
      return (int)i;
  }
  return -1;
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

