#include <stdio.h>
#include <stdlib.h>

#define SIZE 10000000

int main(void)
{
  unsigned int i;
  char *str;
  for(i=0; i<SIZE; i++) {
    str = (char *)malloc(2048);
    free(str);
  }
  fprintf(stdout, "bye-bye ﾉｼ\n");
  return 0;
}

