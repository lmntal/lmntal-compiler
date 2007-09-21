dnl Process this file with autoconf to produce a configure script
#                                               -*- Autoconf -*-
# Process this file with autoconf to produce a configure script.

AC_PREREQ(2.61)
AC_INIT([SLIM], [0.0.1], [lang@ueda.info.waseda.ac.jp], [slim])
# AC_REVISION($Revision: 1.6 $)
# AC_COPYRIGHT (copyright-notice)
AC_CONFIG_SRCDIR([src/main.c])
AC_CONFIG_HEADER([src/config.h])
AM_INIT_AUTOMAKE

# ----------------------------------------------------------------------
# Checks for typedefs, structures, and compiler characteristics.
#

AC_PROG_CC
AC_C_INLINE
AC_C_CONST
AC_C_VOLATILE
AC_C_RESTRICT
AC_C_CHAR_UNSIGNED
#AC_C_VARARRAYS
#AC_C_TYPEOF
# AC_C_STRINGIZE

AC_TYPE_INT8_T
AC_TYPE_INT16_T
AC_TYPE_INT32_T
AC_TYPE_INT64_T
AC_TYPE_INTMAX_T
AC_TYPE_INTPTR_T
AC_TYPE_LONG_DOUBLE
# AC_TYPE_LONG_DOUBLE_WIDER
dnl AC_TYPE_LONG_LONG_INT
# AC_TYPE_MBSTATE_T
dnl AC_TYPE_SIZE_T
# AC_TYPE_SSIZE_T
AC_TYPE_UINT8_T
AC_TYPE_UINT16_T
AC_TYPE_UINT32_T
AC_TYPE_UINT64_T
AC_TYPE_UINTMAX_T
# AC_TYPE_UINTPTR_T
dnl AC_TYPE_UNSIGNED_LONG_LONG_INT

AC_CHECK_SIZEOF(long)
AC_CHECK_SIZEOF(void*)

# ----------------------------------------------------------------------
# Checks for programs.
# 

AC_PROG_CC_STDC
AC_PROG_CC_C89

AC_SUBST(CFLAGS)

if test "$GCC" = "yes"; then
  CFLAGS="-ggdb -std=c89 -pedantic -Wall -Wchar-subscripts -Wmissing-declarations -Wredundant-decls -Wmissing-prototypes -Wnested-externs -Wpointer-arith -Wcast-align -Wsign-compare -Wswitch-enum"
fi

AC_ARG_ENABLE(debug,
[  --enable-debug            trun on debugging [default no]])

if test "$enable_debug" = "yes"; then
  AC_MSG_RESULT(enable debug: yes)
  AC_DEFINE([DEBUG],,[elable debug])
else
  AC_MSG_RESULT(enable debug: no)
fi

AM_WITH_DMALLOC


# ----------------------------------------------------------------------
# for SLIM
# 

SLIM_VERSION=$PACKAGE_VERSION
AC_SUBST(SLIM_VERSION)
AC_DEFINE_UNQUOTED(SLIM_VERSION, "$SLIM_VERSION", [slim version])

# AC_PROG_INSTALL

# Checks for libraries.

# Checks for header files.
AC_HEADER_STDC
AC_HEADER_TIME
dnl AC_CHECK_HEADERS(sys/time.h sys/types.h glob.h dlfcn.h getopt.h sched.h unistd.h)
dnl AC_CHECK_HEADERS(stdint.h inttypes.h rpc/types.h)
dnl AC_CHECK_HEADERS(syslog.h crypt.h)
dnl AC_CHECK_HEADERS(pty.h util.h libutil.h sys/loadavg.h)

# Checks for library functions.
AC_CHECK_FUNC([strchr])

# output
AC_CONFIG_FILES([Makefile src/Makefile])

AC_OUTPUT
