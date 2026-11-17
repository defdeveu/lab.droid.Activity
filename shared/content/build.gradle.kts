plugins {
    id("com.android.library")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "defdev.labdroid.activity.content"
    compileSdk = 37
    defaultConfig { minSdk = 30 }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures { compose = true }
}

kotlin { compilerOptions { allWarningsAsErrors.set(true) } }
tasks.withType<JavaCompile>().configureEach { options.compilerArgs.add("-Werror") }

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.foundation)
    api(libs.androidx.activity.compose)
    implementation(libs.androidx.core.ktx)
}
