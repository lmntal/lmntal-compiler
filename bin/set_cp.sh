#!/bin/bash

if [ -L "$0" ]; then
    CWD="$(dirname $(readlink "$0"))"
else
    CWD="$(dirname "$0")"
fi
export LMNTAL_HOME="$(cd "$CWD"/.. && pwd)"

CLASSPATH="$LMNTAL_HOME/classes"
for f in "$LMNTAL_HOME"/lib/*.jar; do CLASSPATH="$CLASSPATH:$f"; done

export CLASSPATH
