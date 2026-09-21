#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

echo ">> cleaning..."

rm -rf "$BIN_DIR" "$TEMP_DIR" dist

echo "Clean complete."