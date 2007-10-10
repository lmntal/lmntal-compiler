/*
 * dumper.h
 */

#ifndef LMN_DUMPER_H
#define LMN_DUMPER_H

#include "lmntal.h"

/* TODO: rename to lmn_dump_mem */
LMN_EXTERN void lmn_mem_dump(LmnMembrane *mem);
LMN_EXTERN void lmn_dump_cell(LmnMembrane *mem);
LMN_EXTERN void lmn_dump_dot(LmnMembrane *mem);
#endif /* LMN_DUMPER_H */
