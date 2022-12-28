plugins {
    kotlin("multiplatform") version "1.7.21"
    kotlin("plugin.serialization") version "1.7.21"
    id("maven-publish")
}

group = "redtoss.libraries.native.pokemon"
version = "1.0.0-alpha"
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
                cssSupport {
                    enabled = true
                }
            }
        }
    }


    val hostOs = System.getProperty("os.name")
    val isMingwX64 = hostOs.startsWith("Windows")
    val isMacOS = hostOs == "Mac OS X"
    val isLinux = hostOs == "Linux"
    val nativeTarget = when {
        isMacOS -> macosX64("native")
        isLinux -> linuxX64("native")
        isMingwX64 -> mingwX64("native")
        else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
    }

    sourceSets.all {
        languageSettings {
            optIn("kotlin.Experimental")
            optIn("kotlin.ExperimentalMultiplatform")
            optIn("kotlin.ExperimentalStdlibApi")
        }
    }
    val ktorVersion = "2.2.1"
    val coroutinesCoreVersion = "1.6.4"
    val serializationJsonVersion = "1.4.1"
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$serializationJsonVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVersion")

                implementation("io.ktor:ktor-client-core:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesCoreVersion")
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }
        val jvmTest by getting {
            dependencies {
                implementation("io.ktor:ktor-client-okhttp:$ktorVersion")
            }
        }
        val jsMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation("io.ktor:ktor-client-js:$ktorVersion")
            }
        }
        val nativeMain by getting {
            dependencies {
                when {
                    isMacOS -> {
                        // Darwin is required for .dylib libraries (MacOS)
                        implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                    }

                    isLinux -> {
                        // Curl is an option for .so libraries (Linux)
                        implementation("io.ktor:ktor-client-curl:$ktorVersion")
                    }

                    isMingwX64 -> {

                    }

                    else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
                }
            }
        }
        val nativeTest by getting {
            dependencies {
                when {
                    isMacOS -> {
                        // Darwin is required for .dylib libraries (MacOS)
                        implementation("io.ktor:ktor-client-darwin:$ktorVersion")
                    }

                    isLinux -> {
                        // Curl is an option for .so libraries (Linux)
                        implementation("io.ktor:ktor-client-curl:$ktorVersion")
                    }

                    isMingwX64 -> {

                    }

                    else -> throw GradleException("Host OS is not supported in Kotlin/Native.")
                }
            }
        }
    }

    nativeTarget.apply {
        binaries {
            sharedLib {
                baseName = "pokeapi"
            }
        }
    }
}
