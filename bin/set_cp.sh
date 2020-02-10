#!/bin/bash

if [ -L "$0" ]; then
    export LMNTAL_HOME="$(dirname $(readlink "$0"))/.."
else 
    export LMNTAL_HOME="$(dirname "$0")/.."
fi

CLASSPATH="$LMNTAL_HOME/classes"
for f in "$LMNTAL_HOME"/lib/*.jar; do CLASSPATH="$CLASSPATH:$f"; done

export CLASSPATH
