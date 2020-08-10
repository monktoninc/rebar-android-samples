# Rebar for Android

This repository will have more information as time goes on.

## Compatibility 

Monkton only tests and develops with Android Studio (Current version 3.2).

Rebar only supports Android 8.0 (API level 26) and newer.
We will only officially support two major version behind at this time (Android 9.x - Android 10.x).

## Cloning from Github

Clone this sample repository to your development machine.
Add the `rebar-<version>.aar` to the `rebar-core` module under this project.
This is not included in the git repo.

To clone run the command `git clone https://github.com/monktoninc/rebar-android-samples.git`

## Fast build and deploy

This project can be downloaded from Github, attributes remapped with a single command line function: 

`git clone https://github.com/monktoninc/rebar-android-samples.git;rm -rf rebar-android-samples/.git;cd rebar-android-samples;chmod +x setup-project.sh;./setup-project.sh YOUR-APP-IDENTIFIER;open .`

## Configuration

After cloning the project you will need to:

* Download the `app-config.json` for your app from the Rebar Hub and place it in the `development/assets` folder
* add your app-signing.jks file
* add the current `rebar.<version>.aar` to the `rebar-core` project
* customize the `local.properties` file to include settings for signing the app
* copy the `ccj-3.0.1.jar` and `fips-signer.jar` files to the root directory
* if you are using Firebase for push, un-comment out the lines for Firebase and GCM
* add `libs/ccj-3.0.1.jar` to the `rebarsample` project

## Renaming / Refactoring

You will want to customize the package path for this project by doing refactoring in Android Studio.

## Licensing

This sample project is released under the MIT License for maximum compatibility in projects built off of it.

## SWID TAG

In NIAP Application Protection Profile 1.3, SWID Tags are required. Monkton has automated the process
of updating the SWID Tags automatically during build time, based off the versionName in the build
gradle file.

Edit the `rebarsample/src/main/assets/app.swidtag` file and add your organizations descriptive information.

