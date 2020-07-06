
SET BUILD_TYPE=release
SET APK_NAME=%2
SET SIGNING_KEYSTORE=%3
SET KEY_ALIAS=%4
SET RENAME_APP_TARGET=%5
SET ANDROID_KEYSTORE_PASSCODE=%6


if "%1" EQU "release" (
ECHO RELEASE
    SET BUILD_TYPE=release
)
if "%1" EQU "debug" (
ECHO DEBUG
    SET BUILD_TYPE=debug
)

ECHO %BUILD_TYPE%

if "%KEY_ALIAS%" EQU "" (
    SET KEY_ALIAS=androiddebugkey
)

java  -cp ..\ccj-3.0.1.jar -jar ..\fips-signer.jar build\outputs\apk\%APK_NAME%

"C:\Program Files\Java\jdk1.8.0_191\bin\jarsigner" -verbose -keystore %SIGNING_KEYSTORE% build/outputs/apk/%APK_NAME% %KEY_ALIAS% -storepass %ANDROID_KEYSTORE_PASSCODE%

if "%RENAME_APP_TARGET%" NEQ "" (
    if "%RENAME_APP_TARGET%" NEQ "%APK_NAME%" (
        copy build\outputs\apk\%APK_NAME% build\outputs\apk\%RENAME_APP_TARGET%
        del build\outputs\apk\%APK_NAME%
    )
)

