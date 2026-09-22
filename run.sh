#!/bin/bash

cd "$(dirname "$0")"
source scripts/build.conf

set -e # stop on error

./scripts/launch_source.sh