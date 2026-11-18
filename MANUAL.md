# Activity lab — run steps

Use the maintained `main` branch. Open this directory as the Gradle project.

## Setup and build

Use Android Studio Rabbit 2 (2026.2.2) or newer, Android SDK platform 37 and
build-tools 37.0.0. Use the project's Gradle daemon JVM criteria, which select
JetBrains Runtime 25 from Studio's bundled JBR; no separate JDK installation
is needed. The app requires Android 11 (API 30), targets
Android 17 (API 37), and runs offline. Java/Kotlin compilation targets Java 25.

For Android Studio Quail 4, change only your local checkout's `agp` value in
`gradle/libs.versions.toml` from `9.5.1` to `9.4.1` before sync, and use
JetBrains Runtime 25. Leave that compatibility edit uncommitted. The default
AGP 9.5.1 configuration requires Rabbit 2 or newer.

For a shell build, select your installed JDK through `JAVA_HOME` if needed:

```sh
./gradlew assembleDebug
```

## Install and run

```sh
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n defdev.labs.droid.activity/.LoginActivity
```

Launch **Activity Lab** and follow the trainer's exercise instructions.
If Android displays its first-use full-screen message, dismiss it with **Got it**.
After updating the source, sync, rebuild and reinstall before using the app.
