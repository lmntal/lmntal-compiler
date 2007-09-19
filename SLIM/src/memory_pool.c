#include <stdio.h>
#include <stdlib.h>
#include "memory_pool.h"

#define LHS_CAST(T,X) (*(T*)&(X))

struct memory_pool_
{
  int sizeof_element;

  void *free_head;
};

memory_pool *memory_pool_new(int s)
{
  memory_pool *res = malloc(sizeof(memory_pool));
  const int sizeof_p = sizeof(void*);
  
  res->sizeof_element = ((s+sizeof_p-1)/sizeof_p)*sizeof_p;
  res->free_head = 0;

  fprintf(stderr, "this memory_pool allocate %d, aligned as %d\n", s, res->sizeof_element);

  return res;
}

void *memory_pool_malloc(memory_pool *p)
{
  void *res;
  
  if(p->free_head == 0){
    const int blocksize = 8;
    char *rawblock;
    int i;
    
    fprintf(stderr, "no more free space, so allocate new block\n");

    p->free_head = malloc(p->sizeof_element * blocksize);
    rawblock = (char*)p->free_head;
    
    for(i=0; i<blocksize-1; ++i){
      LHS_CAST(void*, rawblock[p->sizeof_element*i]) = &rawblock[p->sizeof_element*(i+1)];
    }
    LHS_CAST(void*, rawblock[p->sizeof_element * (blocksize-1)]) = 0;
  }

  res = p->free_head;
  p->free_head = *(void**)p->free_head;
  
  return res;
}

void memory_pool_free(memory_pool *p, void *e)
{
  *(void**)e = p->free_head;
  p->free_head = e;
}

void memory_pool_delete(memory_pool *p)
{
  fprintf(stderr, "i dont know how to free what i memory_pooled\n");
}

/*
int main()
{
  memory_pool *p = memory_pool_new(5);

  int i;
  void *x[20];
  for(i=0; i<10; ++i){
    x[i] = memory_pool_malloc(p);
    printf("ok. allocate %dth element on %p\n", i, x[i]);
  }

  for(i=0; i<10; ++i){
    memory_pool_free(p, x[i]);
    printf("ok. free %dth element on %p\n", i, x[i]);
  }

  for(i=0; i<20; ++i){
    x[i] = memory_pool_malloc(p);
    printf("ok. allocate %dth element on %p\n", i, x[i]);
  }

  memory_pool_delete(p);
  return 0;
}
*/
