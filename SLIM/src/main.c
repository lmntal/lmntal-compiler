/*
 * main.c - main
 */

#include <stdio.h>
#include <unistd.h>
#include <getopt.h>
#include "lmntal.h"

/* global environment */
struct LmnEnv  lmn_env;

static void usage(void)
{
  fprintf(stderr,
          "Usage: slim [OPTION]... [file]\n"
          "options:\n"
          "  --showproxy     Show proxy atoms\n"
          "  --hideruleset   Hide ruleset from result\n"
          "  --dot           Output result in dot language\n"
          "  --version       Prints version and exits.\n"
          "  --help          This Help.\n"
          );
  exit(1);
}

static void version(void)
{
  printf("The Slim LMNtal Implementation, version %s\n", SLIM_VERSION);
  exit(1);
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
}

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

  finalize();
  return 0;
}
