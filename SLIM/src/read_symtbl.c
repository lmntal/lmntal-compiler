/*
 * read_symtbl.c
 */

#include "lmntal.h"
#include "read_symtbl.h"

struct LmnSymbolTable lmn_symbol_table;
struct LmnFunctorTable lmn_functor_table;

void read_int(FILE* file, unsigned int *n)
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
void read_quoted_str(FILE* file, char *buf, size_t *buf_size, unsigned int *str_len)
{
  char c;
  unsigned int i;

  i = 0;
  do {
    c = fgetc(file);
  } while (c == ' ' || c == '\t');

  if (c != '\'' || c == EOF) goto ERROR;

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
    case '\'':
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


void load_symbol(char *file_name)
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
