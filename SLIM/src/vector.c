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

/* free */
void vec_free(Vector *vec) {
  free(vec->tbl);
}

