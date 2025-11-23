#!/bin/bash
cd "$(dirname "$0")"

if [ -d "bin" ]; then
    echo "directory: bin exists, skipping compilation"
else
    echo "directory: bin missing, compiling"
    ./compile.command
fi

echo complete launch

java -XstartOnFirstThread -classpath library/*:bin/ boxPush3d/Main

exit
