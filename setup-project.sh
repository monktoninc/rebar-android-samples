#!/bin/sh
set -e

HAS_ARGS=1
if [ $# -ne 1 ];
then
    HAS_ARGS=0
fi

bundle_identifier=''


if [ $HAS_ARGS = 1 ]; 
then
    bundle_identifier="$1"
else
    echo "Please enter bundle identifier: "
    read bundle_identifier
fi


# Replace the bundle identifier
find . -name 'build.gradle' -print0 | xargs -0 sed -i "" "s/io.monkton.rebarsample/$bundle_identifier/g"

printf '\e[32mFinished setting up %s\e[0m\n' "Sample App"

