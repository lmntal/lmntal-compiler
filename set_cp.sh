#!/bin/bash

CLASSPATH=$LMNTAL_HOME/bin
for f in $LMNTAL_HOME/lmntal_lib/*.jar; do CLASSPATH=$CLASSPATH:$f; done

export CLASSPATH
