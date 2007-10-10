/*
 * main.c - main
 */

#include <stdio.h>
#include <unistd.h>
#include <getopt.h>
#include "lmntal.h"
#include "read_instr.h"

#ifdef DEBUG
#include <mcheck.h>
#endif

struct LmnEnv  lmn_env;

static void usage(void)
{
  fprintf(stderr,
          "Usage: slim [file]\n"
          "options:\n"
          "  --showproxy     Show proxy atoms\n"
          "  --hideruleset   Hide ruleset from result\n"
          "  --dot           Output result dot format graph\n"
          "  --version       Prints version and exits.\n"
          "  --help          This Help.\n"
          );
  exit(1);
}

inline static void version(void)
{
  printf("The Slim LMNtal Implementation, version %s\n", SLIM_VERSION);
}

static int parse_options(int argc, char *argv[])
{
  int c, option_index;

  struct option long_options[] = {
    {"version", 0, 0, 1000},
    {"help",    0, 0, 1001},
    {"showproxy",  0, 0, 1002},
    {"hideruleset",  0, 0, 1003},
    {"dot", 0, 0, 1004},
    {0, 0, 0, 0}
  };

  while ((c = getopt_long(argc, argv, "+d", long_options, &option_index)) != -1) {
    switch (c) {
    case 0:
      printf("log_options entries must have positive 4th member.\n");
      exit(1);
      break;
    case 'd': /* 開発用. dumpの表示を開発用にする */
      lmn_env.dev_dump = TRUE;
      break;
    case 1000: version(); break;
    case 1001: /* help */ /*FALLTHROUGH*/
    case '?': usage(); break;
    case 1002:
      lmn_env.show_proxy = TRUE;
      break;
    case 1003:
      lmn_env.show_ruleset = FALSE;
      break;
    case 1004:
      lmn_env.output_format = DOT;
      break;
    default:
      printf("?? getopt returned character code 0x%x ??\n", c);
      exit(1);
      break;
    }
  }
  
  return optind;
}

static void init_env(void)
{
  lmn_env.dev_dump = FALSE;
  lmn_env.show_proxy = FALSE;
  lmn_env.show_ruleset = TRUE;
  lmn_env.output_format = DEFAULT;
}
    
static void init_internal(void)
{
  init_env();
}

static void finalize(void)
{
  unread_il();
/*   if (lmn_functor_table.size > 0) LMN_FREE(lmn_functor_table.entry); */
/*   if (lmn_symbol_table.size > 0) { */
/*     unsigned int i; */
/*     for (i = 0; i < lmn_symbol_table.size; i++) { */
/*       LMN_FREE(lmn_symbol_table.entry[i]); */
/*     } */
/*     LMN_FREE(lmn_symbol_table.entry); */
/*   } */
}

#ifdef DEBUG
static void test(void)
{
}

#endif

int main(int argc, char *argv[])
{
  int optid;
  
  init_internal();
  
  optid = parse_options(argc, argv);
  if (optid < argc) {
    read_il(argv[optid]);
  } else {
    fprintf(stderr, "no input file\n");
    exit(1);
  }

  run();

#ifdef DEBUG
  test();
#endif
  

  finalize();



/*   unsigned int instr_a[] = {0x12345678, 0x11223344}; */
/*   BYTE* instr; */
/*   BYTE b; */
/*   int i; */
/*   short s; */
/*   double d; */

/*   instr = (BYTE*)instr_a; */

/*   LMN_IMS_READ_UINT8(b,instr); */
/*   LMN_IMS_READ_UINT32(i,instr); */
/*   LMN_IMS_READ_UINT16(s,instr); */
/*   LMN_IMS_READ_DOUBLE(d,instr); */
  
/*   printf("%X\n", (unsigned)b); */
/*   printf("%X\n", (unsigned)i); */
/*   printf("%X\n", (unsigned)s); */
/*   printf("%X\n", (unsigned)d); */
  
/*   LmnWord atom[] = {0x12345678, 0x87654321, 0x11223344, 0x55667788, 0x11446688, 0x00447788}; */
  
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

  return 0;
}
