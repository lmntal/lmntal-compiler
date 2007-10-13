/*
 * dumper.h
 */

#ifndef LMN_DUMPER_H
#define LMN_DUMPER_H

#include "lmntal.h"

LMN_EXTERN void lmn_dump_mem(LmnMembrane *mem);
LMN_EXTERN void lmn_dump_cell(LmnMembrane *mem);
LMN_EXTERN void lmn_dump_dot(LmnMembrane *mem);
#endif /* LMN_DUMPER_H */
