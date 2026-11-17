# Activity

Android teaching app for the "Development security in Android" course.
The trainer provides the exercise instructions and expected outcomes.
The maintained `main` includes **Challenge** and **Solution**, selected through
the C/S navigation rail.

## Requirements

- Android Studio Rabbit (2026.2) or newer
- Android Studio's bundled JBR for Gradle; no separate JDK installation required
- Android SDK platform 37 and build-tools 37.0.0
- A device or emulator running Android 11 (API 30) or newer

The app targets Android 15 (API 35). Java/Kotlin compilation targets Java 17.
The exercise runs offline.

## Build and run

Open this directory in Android Studio, sync and run the app, or use:

```sh
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

See [MANUAL.md](MANUAL.md) for the workspace run procedure.

## Tests

```sh
./gradlew testDebugUnitTest
```

## Fonts

The bundled variable Noto fonts and their upstream licenses are listed in
`app/src/main/assets/licenses/fonts.txt`.
