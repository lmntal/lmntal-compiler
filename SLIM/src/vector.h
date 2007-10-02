/* 
 * Vector <LmnWord>
 * キーをアドレス値とすることで汎用的なデータ構造を扱うことができる。
 */

#ifndef LMN_VECTOR_H
#define LMN_VECTOR_H

#include "lmntal.h"

typedef struct Vector {
  LmnWord* tbl;
  unsigned int num, cap;
} Vector;

LMN_EXTERN Vector *vec_init(Vector *vec, unsigned int init_size);
LMN_EXTERN Vector* vec_make(unsigned int init_size);
LMN_EXTERN Vector* vec_make_default(void);
LMN_EXTERN void vec_push(Vector* vec, LmnWord keyp);
LMN_EXTERN LmnWord vec_pop(Vector *vec);
LMN_EXTERN void vec_set(Vector* vec, unsigned int index, LmnWord keyp);
LMN_EXTERN LmnWord vec_get(Vector *vec, unsigned int index);
LMN_EXTERN int vec_indexof(Vector *vec, LmnWord keyp);
LMN_EXTERN void vec_destroy(Vector *vec);
LMN_EXTERN void vec_free(Vector *vec);

#endif /* LMN_VECTOR_H */
