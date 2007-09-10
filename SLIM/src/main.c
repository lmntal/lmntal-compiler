/*
 * main.c - main
 */

#include <stdio.h>
#include "lmntal.h"

int main(int argc, char *argv[]) {
  LmnWord atom[] = {0x12345678, 0x87654321, 0x11223344, 0x55667788, 0x11446688, 0x00447788};
  
/*   printf("%X\n", (unsigned)LMN_ATOM_PREV(atom)); */
/*   printf("%X\n", (unsigned)LMN_ATOM_NEXT(atom)); */
/*   printf("%X\n", (unsigned)LMN_ATOM_FUNCTOR(atom)); */
/*   printf("%X\n", (unsigned)LMN_ATOM_LINK_ATTR(atom, 0)); */
/*   printf("%X\n", (unsigned)LMN_ATOM_LINK_ATTR(atom, 1)); */
/* #define LMN_ATOM_ARITY(ATOM) 0 */
/*   printf("%X\n", (unsigned)LMN_ATOM_LINK(atom, 0)); */
/* /\* #undef LMN_ATOM_ARITY *\/ */
/* /\* #define LMN_ATOM_ARITY(ATOM) 3 *\/ */
/*   printf("%X\n", (unsigned)LMN_ATOM_LINK(atom, 0)); */
/* /\* #undef LMN_ATOM_ARITY *\/ */

/*   LMN_ATOM_SET_PREV(atom, 0x00112233); */
/*   LMN_ATOM_SET_NEXT(atom, 0x12312312); */
/*   LMN_ATOM_SET_LINK_ATTR2(atom, 0, 1); */
/*   LMN_ATOM_SET_LINK_ATTR2(atom, 1, 2); */
/*   LMN_ATOM_SET_LINK(atom, 0, 10); */
/*   LMN_ATOM_SET_LINK(atom, 1, 0x12345678); */

/*   printf("--- after ---\n"); */
/*   printf("%X\n", (unsigned)LMN_ATOM_PREV(atom)); */
/*   printf("%X\n", (unsigned)LMN_ATOM_NEXT(atom)); */
/*   printf("%X\n", (unsigned)LMN_ATOM_FUNCTOR(atom)); */
/*   printf("%X\n", (unsigned)LMN_ATOM_LINK_ATTR(atom, 0)); */
/*   printf("%X\n", (unsigned)LMN_ATOM_LINK_ATTR(atom, 1)); */
/* /\* #define LMN_ATOM_ARITY(ATOM) 0 *\/ */
/*   printf("%X\n", (unsigned)LMN_ATOM_LINK(atom, 0)); */
/* /\* #undef LMN_ATOM_ARITY *\/ */
/* /\* #define LMN_ATOM_ARITY(ATOM) 3 *\/ */
/*   printf("%X\n", (unsigned)LMN_ATOM_LINK(atom, 1)); */
/* /\* #undef LMN_ATOM_ARITY *\/ */

/*   printf("---- ----\n"); */
/*   { */
/*     unsigned int i; */
/*     for (i=0;i<sizeof(atom)/sizeof(atom[0]);i++) { */
/*       printf("%X\n", (unsigned)atom[i]); */
/*     } */
/*   } */

  {
    int i;
    int N = 2000000000;
    for (i=0;i<N;i++) {
      LMN_ATOM_SET_LINK_ATTR(atom, 0, i);
    }
    printf("%X\n", (unsigned)LMN_ATOM_GET_LINK_ATTR(atom, 0));
  }
    
  return 0;
}
