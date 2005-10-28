#!/bin/bash

LMNTAL_HOME=`dirname $0`/..

CLASSPATH=$LMNTAL_HOME/classes
#CLASSPATH=$LMNTAL_HOME/bin/lmntal.jar
for f in $LMNTAL_HOME/lib/*.jar; do CLASSPATH=$CLASSPATH:$f; done

export CLASSPATH
