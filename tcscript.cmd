emulator -avd Android4x86 -verbose -no-boot-anim -no-window
if %errorlevel% GTR 0 exit 1
cscript //nologo sleep.js 100000
gradlew.bat connectedCheck