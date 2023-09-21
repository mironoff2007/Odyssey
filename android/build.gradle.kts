plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-parcelize")
    id("org.jetbrains.compose")
}

android {
    namespace = "ru.alexgladkov.odyssey_demo"
    compileSdkVersion(Dependencies.compileSdk)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(Dependencies.targetSdk)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    lintOptions {
        isCheckReleaseBuilds = false
    }

    packagingOptions {
        exclude("META-INF/*")
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("17"))
        }
    }
}

dependencies {
    implementation(project(":common:common-sample"))
    implementation(project(":odyssey:odyssey-compose"))
    implementation(project(":odyssey:odyssey-core"))
    implementation(compose.material)

    implementation(Dependencies.AndroidX.AppCompat.appCompat)
    implementation(Dependencies.AndroidX.Activity.activityCompose)
    implementation(Dependencies.Images.kamel)
}