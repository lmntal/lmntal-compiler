#!/bin/bash

export LMNTAL_HOME=`dirname $0`/..

CLASSPATH=$LMNTAL_HOME/classes
for f in $LMNTAL_HOME/lib/*.jar; do CLASSPATH=$CLASSPATH:$f; done
for f in $LMNTAL_HOME/tools/Retrotranslator-1.0.8-bin/*.jar; do CLASSPATH=$CLASSPATH:$f; done

export CLASSPATH
