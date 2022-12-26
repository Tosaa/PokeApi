# PokeKotlin/Native
_A Command line interface written in Kotlin/Native to use the **PokeAPILibrary**._

Within this Project, Kotlin/Native is used to provide a command line interface. Moreover the native library of the PokeAPILibrary is added as dependency.
The dependency is in a Kotlin/Native-library (klib) format. So no Language translation is required to make use of the dependency.

In the [build.gradle.kts](./build.gradle.kts) file the library is added by:
```
sourceSets {
        val nativeMain by getting {
            dependencies {
                api("redtoss.libraries.native.pokemon:PokeNativeLib-native:1.0.0-alpha")
            }
        }
    }
```
This will add `redtoss.libraries.native.pokemon:PokeNativeLib-native:klib:1.0.0-alpha` to the external libraries.

## Build
Somehow `./gradlew build` is not working. Build needs to be triggered via IDE.

## run

Executable can be found [here](build/bin/native/debugExecutable) or simply be executed by the terminal  
`$ ./build/bin/native/debugExecutable/Pokemon.kexe`