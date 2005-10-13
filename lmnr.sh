#!/bin/bash

set_cp
export CLASSPATH=$1:$CLASSPATH
shift
java Main $*
