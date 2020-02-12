#!/bin/bash

export LMNTAL_HOME="$(cd "$(dirname "$0")"/.. && pwd)"

CLASSPATH="$LMNTAL_HOME/classes"
for f in "$LMNTAL_HOME"/lib/*.jar; do CLASSPATH="$CLASSPATH:$f"; done

export CLASSPATH
