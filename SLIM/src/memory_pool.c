#include <stdio.h>
#include <stdlib.h>
#include "memory_pool.h"

#define REF_CAST(T,X) (*(T*)&(X))

struct memory_pool_
{
  int sizeof_element;
  void *block_head;
  void *free_head;
};

memory_pool *memory_pool_new(int s)
{
  memory_pool *res = malloc(sizeof(memory_pool));

  /* align in long (but ok because sizeof(long)>sizeof(void*)) */
  res->sizeof_element = ((s+sizeof(long)-1)/sizeof(long))*sizeof(long);
  res->block_head = 0;
  res->free_head = 0;

  /* fprintf(stderr, "this memory_pool allocate %d, aligned as %d\n", s, res->sizeof_element); */

  return res;
}

void *memory_pool_malloc(memory_pool *p)
{
  void *res;
  
  if(p->free_head == 0){
    const int blocksize = 8;
    char *rawblock;
    int i;
    
    /* fprintf(stderr, "no more free space, so allocate new block\n"); */

    /* top of block is used as pointer to head of next block */
    /* it uses sizeof(void*) and be alloced sizeof(long) */
    rawblock = malloc(sizeof(long) + p->sizeof_element * blocksize);
    *(void**)rawblock = p->block_head;
    p->block_head = rawblock;

    /* rest is used as space for elements */
    /* skip size is NOT sizeof(void*) but sizoef(long). see above */
    rawblock = (char*)((long*)rawblock + 1);
    p->free_head = rawblock;

    for(i=0; i<blocksize-1; ++i){
      /* top of each empty element are used as pointer to next empty element */
      REF_CAST(void*, rawblock[p->sizeof_element*i]) = &rawblock[p->sizeof_element*(i+1)];
    }
    REF_CAST(void*, rawblock[p->sizeof_element*(blocksize-1)]) = 0;
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
  void *blockhead = p->block_head;
  
  while(blockhead){
    void *next_blockhead = *(void**)blockhead;
    free(blockhead);
    blockhead = next_blockhead;
  }

  free(p);
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
