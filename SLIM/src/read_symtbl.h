/*
 * read_symtbl.h
 */

void read_quoted_str(FILE* file, char *buf, size_t *buf_size, unsigned int *str_len);
void read_int(FILE* file, unsigned int *n);
void load_symbol(char *file_name);
