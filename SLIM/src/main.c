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
struct LmnSymbolTable lmn_symbol_table;
struct LmnFunctorInfo lmn_functor_table;

static void usage(void)
{
  fprintf(stderr,
          "Usage: slim [file]\n"
          "options:\n"
          "  --version       Prints version and exits.\n"
          "  --help          This Help.\n"
          );
  exit(1);
}

static void version(void)
{
  printf("The Slim LMNtla Implementation, version %s\n", SLIM_VERSION);
}

static int parse_options(int argc, char *argv[])
{
  int c, option_index;

  struct option long_options[] = {
    {"version", 0, 0, 1000},
    {"help",    0, 0, 1001},
    {0, 0, 0, 0}
  };

  while ((c = getopt_long(argc, argv, "+s:", long_options, &option_index)) != -1) {
    switch (c) {
    case 0:
      printf("log_options entries must have positive 4th member.\n");
      exit(1);
      break;
    case 's':
      lmn_env.symbol_file = optarg;
      
      break;
    case 1000: version(); break;
    case 1001: /* help */ /*FALLTHROUGH*/
    case '?': usage(); break;
    default:
      printf("?? getopt returned character code 0x%x ??\n", c);
      exit(1);
      break;
    }
  }
  
  return optind;
}

static void read_int(FILE* file, unsigned int *n)
{
  int ret;
  ret = fscanf(file, "%u", n);
  if (ret == EOF) {
    perror("load_symbol, read_int");
    exit(1);
  }
  if (ret < 1) {
    fprintf(stderr, "load_symbol, read_int: Invalid Format\n");
    exit(1);
  }
}

/* buf must be pointer to allocated space */
static void read_quoted_str(FILE* file, char *buf, size_t *buf_size, unsigned int *str_len)
{
  char c;
  unsigned int i;

  i = 0;
  do {
    c = fgetc(file);
  } while (c == ' ' || c == '\t');

  if (c != '"' || c == EOF) goto ERROR;

  while (TRUE) {
    /* extend buffer */
    if (i == *buf_size) {
      *buf_size = (*buf_size) * 2;
      buf = LMN_REALLOC(char, buf, *buf_size);
    }
    
    c = fgetc(file);
    switch (c) {
    case '\\':
      c = fgetc(file);
      buf[i++] = c;
      break;
    case '"':
      goto RETURN;
    case EOF:
      goto ERROR;
    default:
      buf[i++] = c;
      break;
    }
  }
  
 RETURN:
  buf[i] = '\0';
  *str_len = i;
  return;
 ERROR:
  fprintf(stderr, "load_symbol, read_quoted_str: Invalid Format\n");
  exit(1);
}

static void load_symbol(char *file_name)
{
  FILE *file;
  char *buf;
  unsigned int buf_size, num, i;
  
  buf_size = 64;
  buf = LMN_CALLOC(char, buf_size);

  if (!strcmp(file_name, "-")) file = stdin;
  else file = fopen(file_name, "r");

  if (!file) {
    perror("load_symbol");
    exit(1);
  }

  while (TRUE) {
    fscanf(file, "%s", buf);
    if (!strcmp(buf, "Functor")) {
      read_int(file, &num);
      if (num > 0) {
        lmn_functor_table.entry = LMN_CALLOC(LmnFunctorEntry, num);
        lmn_functor_table.size = num;
      
        for (i = 0; i < num; i++) {
          unsigned int id, name, arity;
          read_int(file, &id);
          read_int(file, &name);
          read_int(file, &arity);
          lmn_functor_table.entry[id].name = name;
          lmn_functor_table.entry[id].arity = arity;
        }
      }
    }
    else if (!strcmp(buf, "Symbol")) {
      read_int(file, &num);
      if (num > 0) {
        lmn_symbol_table.entry = LMN_CALLOC(char*, num);
        lmn_symbol_table.size = num;

        for (i = 0; i < num; i++) {
          unsigned int str_len, id;
          
          read_int(file, &id);
          read_quoted_str(file, buf, &buf_size, &str_len);
          lmn_symbol_table.entry[id] = LMN_CALLOC(char, str_len+1);
          strncpy(lmn_symbol_table.entry[id], buf, str_len+1);
        }
      }
    }
    else if (!strcmp(buf, "End")) {
      break;
    }
    else {
      fprintf(stderr, "load_symbol: Invalid Format %s\n", file_name);
      exit(1);
    }
  }

  if (file != stdin) fclose(file);
  LMN_FREE(buf);
}

static void init_internal(void)
{
}

static void finalize(void)
{
  if (lmn_functor_table.size > 0) LMN_FREE(lmn_functor_table.entry);
  if (lmn_symbol_table.size > 0) {
    int i;
    for (i = 0; i < lmn_symbol_table.size; i++) {
      LMN_FREE(lmn_symbol_table.entry[i]);
    }
    LMN_FREE(lmn_symbol_table.entry);
  }
}

int main(int argc, char *argv[])
{
  #ifdef DEBUG
  mtrace();
  #endif
  
  init_internal();
  parse_options(argc, argv);

  if (lmn_env.symbol_file) load_symbol(lmn_env.symbol_file);

  {
    int i;
    for (i = 0; i < lmn_symbol_table.size; i++) {
      printf("id: %u, str = %s\n", i, lmn_symbol_table.entry[i]);
    }
    for (i = 0; i< lmn_functor_table.size; i++) {
      printf("id: %u, str = %s, arity = %u\n", i, lmn_symbol_table.entry[lmn_functor_table.entry[i].name], lmn_functor_table.entry[i].arity);
    }
  }
  finalize();

#ifdef DEBUG
  muntrace();
#endif

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
