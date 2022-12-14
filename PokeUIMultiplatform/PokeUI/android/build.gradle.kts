plugins {
    id("org.jetbrains.compose")
    id("com.android.application")
    kotlin("android")
}

group "redtoss.app.poke.ui"
version "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":common"))
    implementation("androidx.activity:activity-compose:1.5.1")
}

android {
    compileSdkVersion(31)
    defaultConfig {
        applicationId = "redtoss.app.poke.ui.android"
        minSdkVersion(24)
        targetSdkVersion(31)
        versionCode = 1
        versionName = "1.0-SNAPSHOT"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
}
