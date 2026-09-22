#!/bin/bash

cd "$(dirname "$0")"
source build.conf
cd ..

set -e # stop on error

./scripts/clean.sh

mkdir -p dist

./scripts/build_source.sh

cp -r bin dist/

cp -r library dist/

mkdir -p dist/scripts
cp -r scripts/launch_source.sh dist/scripts/
cp -r scripts/build.conf dist/scripts/

cp -r run.sh dist/

cp -r resources dist/