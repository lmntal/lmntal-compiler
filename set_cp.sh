#!/bin/bash

LMNTAL_HOME=`dirname $0`

CLASSPATH=$LMNTAL_HOME/bin
#CLASSPATH=$LMNTAL_HOME/dist/lmntal.jar
for f in $LMNTAL_HOME/lmntal_lib/*.jar; do CLASSPATH=$CLASSPATH:$f; done

export CLASSPATH
