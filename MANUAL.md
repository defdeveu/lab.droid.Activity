# Activity lab — run steps

Use the maintained `main` branch. Open this directory as the Gradle project.

## Setup and build

Use Android Studio Rabbit (2026.2) or newer, Android SDK platform 37 and
build-tools 37.0.0. Select Studio's bundled JBR as the Gradle JDK; no separate
JDK installation is needed. The app requires Android 11 (API 30), targets
Android 15 (API 35), and runs offline.

For a shell build, select your installed JDK through `JAVA_HOME` if needed:

```sh
./gradlew assembleDebug
```

## Install and run

```sh
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n defdev.labdroid.activity/.LoginActivity
```

Launch **Activity Lab** and follow the trainer's exercise instructions.
If Android displays its first-use full-screen message, dismiss it with **Got it**.
After updating the source, sync, rebuild and reinstall before using the app.
