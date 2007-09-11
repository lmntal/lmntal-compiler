#include "lmntal.h"
#include <limits.h>
#include <errno.h>
#include <stdio.h>

char *buf;
unsigned int buf_size;

enum FunctorType {SYMBOL, INT, DOUBLE};

struct Functor {
  enum FunctorType type;

  union {
    int int_value;
    double double_value;
    struct {
      lmn_interned_str symbol_id;
      LmnArity         arity;
    } sym_atom;
  } v;
};

lmn_interned_str get_symbol_id(char *st)
{
  return -1;
}

void skipws(FILE* file)
{
  char c;
  do {
    c = fgetc(file);
  } while (c == ' ' || c == '\t');

  ungetc(c, file);
}

BOOL str_to_int(char *buf, int *x)
{
  long int l;
  char *end;

  l = strtol(buf, &end, 10);
  if (end==0 || buf==end || *end!='\0') return FALSE;
  if (errno == ERANGE) return FALSE;
  if (errno == EINVAL) return FALSE;
  if (l > INT_MAX || l < INT_MIN) {
    fprintf(stderr, "Integer, out of range %s\n", buf);
  }
  *x = (int)l;
  return TRUE;
}

BOOL str_to_double(char *buf, double *v)
{
  char *end;

  *v = strtod(buf, &end);
  if (end==0 || buf==end || *end!='\0') return FALSE;
  if (errno == ERANGE) return FALSE;
  return TRUE;
}

struct Functor read_functor(FILE* file)
{
  char c;
  struct Functor functor;
  
  do {
    c = fgetc(file);
  } while (c == ' ' || c == '\t');

  if (c == '\'') {
    unsigned int str_len;

    functor.type = SYMBOL;
    ungetc(c, file);
    read_quoted_str(file, buf, &buf_size, &str_len);
    functor.v.sym_atom.symbol_id = get_symbol_id(buf);
    /* skip '_' */
    fgetc(file);
    fscanf(file, "%d", &functor.v.sym_atom.arity);
  }
  else {
    int i, dummy;
    /* read before '_'  */
    i = 0;
    while ((c = fgetc(file)) != '_') {
      buf[i++] = c;
    }
    buf[i] = '\0';

    if (str_to_int(buf, &functor.v.int_value)) {
        functor.type = INT;
    }
    else if (str_to_double(buf, &functor.v.double_value)) {
      functor.type = DOUBLE;
    } else {
      fprintf(stderr, "read_functor: invalid format %s\n", buf);
      exit(1);
    }

    fscanf(file, "%d", &dummy);
    assert(dummy == 1);
  }
  return functor;
}

int main(int argc, char* argv[])
{
  /* initialize */
  buf_size = 256;
  buf = malloc(buf_size);

  {
    struct Functor f;
    f = read_functor(stdin);
    switch (f.type) {
    case INT:
      printf("INT: %d\n", f.v.int_value);
      break;
    case DOUBLE:
      printf("DOBULE: %f\n", f.v.double_value);
      break;
    case SYMBOL:
      printf("SYMBOL: ID=%d, ARITY=%d\n", f.v.sym_atom.symbol_id,
             f.v.sym_atom.arity);
      break;
    default:
      printf("error\n");
      break;
    }
  } 
  return 0;
}
    
