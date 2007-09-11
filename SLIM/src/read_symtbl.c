/*
 * main.c - main
 */

#include "lmntal.h"

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
