#ifndef LMN_MEMORY_POOL_H
#define LMN_MEMORY_POOL_H

struct memory_pool_;
typedef struct memory_pool_ memory_pool;

memory_pool *memory_pool_new(int s);
void *memory_pool_malloc(memory_pool *p);
void memory_pool_free(memory_pool *p, void *e);
void memory_pool_delete(memory_pool *p);

#endif /* LMN_MEMORY_POOL_H */

