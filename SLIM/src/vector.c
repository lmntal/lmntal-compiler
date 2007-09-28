#include "vector.h"

/* init */
Vector* vec_make(unsigned int init_size) {
  Vector* ret;
  ret->tbl = LMN_NALLOC(LmnWord, init_size);
  ret->num = 0;
  ret->cap = init_size;
  return ret;
}

Vector* vec_make_default() {
  return vec_make(16);
}

/* extend */
static void vec_extend(Vector* vec) {
  vec->cap *= 2;
  vec->tbl = LMN_REALLOC(LmnWord, vec->tbl, vec->cap);
}

/* add */
void vec_add(Vector* vec, LmnWord keyp) {
  (vec->tbl)[vec->num] = keyp;
  vec->num++;
  if(vec->num == vec->cap) {
    vec_extend(vec);
  }
}

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
    if(vec->tbl[i] != keyp)
      return (int)i;
  }
  return -1;
}

/* free */
void vec_free(Vector *vec) {
  free(vec->tbl);
}

