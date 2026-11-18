# Activity

Android teaching app for the "Development security in Android" course.
The trainer provides the exercise instructions and expected outcomes.

## Requirements

- Android Studio Rabbit 2 (2026.2.2) or newer
- Android Studio's bundled JetBrains Runtime 25; no separate JDK installation required
- Android SDK platform 37 and build-tools 37.0.0
- A device or emulator running Android 11 (API 30) or newer

The app targets Android 17 (API 37). Gradle uses the daemon JVM criteria in
`gradle/gradle-daemon-jvm.properties`; Java/Kotlin compilation targets Java 25,
with Kotlin 2.4.21.
The exercise runs offline.

## Build and run

Open this directory in Android Studio, sync and run the app, or use:

```sh
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

The maintained branch is `main`. See [MANUAL.md](MANUAL.md) for the project run steps.

## Tests

```sh
./gradlew testDebugUnitTest
```

## Fonts

The bundled variable Noto fonts and their upstream licenses are listed in
`app/src/main/assets/licenses/fonts.txt`.
