import gradle.kotlin.dsl.accessors._c65af4f798638475cce92e0c6691f51f.kotlin

plugins {
    id("com.android.library")
}

android {
    compileSdkVersion(30)

    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(30)
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        jvmToolchain {
            languageVersion.set(JavaLanguageVersion.of("17"))
        }
    }

    packagingOptions {
        pickFirst("lib/x86_64/libjsc.so")
        pickFirst("lib/arm64-v8a/libjsc.so")
    }

    lintOptions {
        isCheckReleaseBuilds = false
    }

    sourceSets {
        named("main") {
            manifest.srcFile("src/androidMain/AndroidManifest.xml")
            res.srcDirs("src/androidMain/res", "src/commonMain/resources")
        }
    }
}
