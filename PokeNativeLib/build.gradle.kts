import java.awt.GridBagConstraints.BOTH

plugins {
    kotlin("multiplatform") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("maven-publish")
}

group = "redtoss.libraries.native.pokemon"
version = "1.0-SNAPSHOT"
repositories {
    mavenCentral()
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(BOTH) {
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }


    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val nativeTarget = when {
        hostOs == "Mac OS X" -> macosX64("native")
        hostOs == "Linux" -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets.all{
        languageSettings{
            optIn("kotlin.Experimental")
            optIn("kotlin.ExperimentalMultiplatform")
            optIn("kotlin.ExperimentalStdlibApi")
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                val serialization_version = "1.4.0-RC"
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serialization_version")
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0-native-mt")
            }
        }
        val jvmMain by getting
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
        val nativeMain by getting
        val nativeTest by getting
    }

    nativeTarget.apply {
        binaries {
            sharedLib {
                baseName = "pokeapi"
            }
        }
    }
}
