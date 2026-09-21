#!/bin/bash

cd "$(dirname "$0")"

java -XX:+UseG1GC -XstartOnFirstThread -cp "library/*:bin/" "boxPush3d.Main"