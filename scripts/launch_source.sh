#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

java $JVM_ARGS -cp "$LIB_DIR/*:$BIN_DIR/" "$MAIN_CLASS"