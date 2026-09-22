#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

set -e # stop on error

if [ ! -d "$BIN_DIR" ]; then
    ./scripts/build_source.sh || exit 1
fi

java $JVM_ARGS -cp "$LIB_DIR/*:$BIN_DIR/" "$MAIN_CLASS"