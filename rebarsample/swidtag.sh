#!/bin/sh
set -e

# Get the version from the gradle file
VERSION=$(grep "versionName" ./build.gradle | awk '{print $2}')

# Format the version to remove quotes
NEW_TAG=${VERSION//\"/}

# Update the file
sed -i  "" "s|version\=.*$|version\=\"${NEW_TAG}\"|" src/main/assets/app.swidtag