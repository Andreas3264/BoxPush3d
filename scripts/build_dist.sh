#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

rm -rf dist
mkdir -p dist

./scripts/build_source.sh
cp -r bin dist/

cp -r library dist/

cp -r run.sh dist/

cp -r resources dist/