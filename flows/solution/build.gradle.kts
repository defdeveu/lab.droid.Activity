plugins {
    id("com.android.library")
}

android {
    namespace = "defdev.labdroid.activity.flows.solution"
    compileSdk = 37
    defaultConfig { minSdk = 30 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

kotlin { compilerOptions { allWarningsAsErrors.set(true) } }
tasks.withType<JavaCompile>().configureEach { options.compilerArgs.add("-Werror") }

dependencies {
    api(project(":shared:content"))
}
