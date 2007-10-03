/*
 * system_ruleset.h
 */

#ifndef LMN_SYSTEM_RULESET_H
#define LMN_SYSTEM_RULESET_H

#include "membrane.h"

BOOL delete_redundant_outproxies(LmnMembrane *mem);
BOOL delete_redundant_inproxies(LmnMembrane *mem);

#endif
