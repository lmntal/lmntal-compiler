#!/bin/bash

set_cp.sh
export CLASSPATH=$1:$CLASSPATH
shift
java Main $*
