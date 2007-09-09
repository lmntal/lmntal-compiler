/*
 * error.c - error handling
 */

#include <stdarg.h>
#include <stdlib.h>
#include "lmntal.h"

void lmn_fatal(const char *msg, ...)
{
  va_list args;
  va_start(args, msg);
  /* use raw port */
  vfprintf(stderr, msg, args);
  va_end(args);
  fputc('\n', stderr);
  fflush(stderr);
  _exit(1);
  /* no treatment */
}
