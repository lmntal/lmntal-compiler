#!/bin/sh

export LMNTAL_HOME=`dirname $0`/../..
export RETROTRANSLATOR=$LMNTAL_HOME/tools/Retrotranslator-1.0.8-bin

for f in $RETROTRANSLATOR/*.jar; do CLASSPATH=$CLASSPATH:$f; done
export CLASSPATH

java -jar $RETROTRANSLATOR/retrotranslator-transformer-1.0.8.jar -srcdir $LMNTAL_HOME/classes
