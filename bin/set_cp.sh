#!/bin/bash

export LMNTAL_HOME=`dirname $0`/..
if [ -x /usr/bin/cygpath ]; then
	export LMNTAL_HOME=`cygpath -wp $LMNTAL_HOME`
fi

CLASSPATH=$LMNTAL_HOME/classes
for f in $LMNTAL_HOME/lib/*.jar; do CLASSPATH=$CLASSPATH:$f; done

export CLASSPATH
