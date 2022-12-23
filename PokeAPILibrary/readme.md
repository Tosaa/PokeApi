# PokeNativeLib
## Where this Lib is used for
The public [PokeAPi](https://pokeapi.co/) provides several endpoints to fetch information about pokemon and items within the pokemon-universe.
The Api can be accessed by this library. Current functionality can be seen below.

### Functions:
- search for Pokemon in Api

## Technical Details
### Multiplatform
Thanks to Kotlin Multiplatform, the Code which is used to access the API can be written in Kotlin and can be accessible to following targets:
- KotlinNative (and therefore for native C/C++ applications)
- JavaScript
- JVM (and therefore for Backends, Android apps and Frontends written in Java/Kotlin)

The following classes are exposed in the library.

![class_diagram](doc/Class_overview.puml)

The following components are part of the library. The boxes within the package show which platform the components belong to.

![components](doc/Component.puml)

When the library is shipped, only the following components are shipped. The library can also be integrated to a 'common' module of another project.

![components_shipped](doc/Component_shipped.puml)


### Usage
Steps to use the API:
1. Add API Lib to dependencies
2. Create custom Logger
3. Create custom Curl execution function
4. Inject Logger & Curl execution function to Libraries `redtoss.poke.lib.PokeApi` object
5. Use `PokeApi.findPokemon(name:String)` to search for Pokemon
#### 1. Dependency

#### 2. Logging
Since Logging is very platform dependent, a base Interface can be implemented by the user/platform itself and be injected to the library.  
The `redtoss.poke.lib.logging.Logger` singleton will use the provided Logging function and logs any information within the Library through it.

Since the Logger class is a Singleton, realized by kotlins `object declaration`,
it is enough to set the `Logger.loggingFunction` at start of the runtime.
How the logging function can be set is platform dependent. 

##### JVM
Here is the example for the JVM [Logger](/src/jvmMain/kotlin/redtoss/poke/lib/Logger.kt):

```
fun log(message:String):Unit {message->
    print(message)
} 

Logger.loggingFunction = log
```


##### Kotlin Native & C
Kotlin Native and C try to use a similar approach.

But to avoid brainf*cks, the Logger provides 2 functions that can be used.  
One function to realise logging, triggered by C code. `Logger.cLoggingFunction`  
One function to realise logging, triggered by Kotlin/Native code. `Logger.kLoggingFunction`

#### 3. Curl execution function
For executing Curl Requests, the `redtoss.poke.lib.CurlExecutor` is used. It provides a function that accepts a `URL` (as String / Char Array for KotlinNative) and returns the received json Response (String/CharArray).
Since the targets do not share code for sending CURL-Requests, the functionality has to be injected.

In case of the JVM example the [CurlExecutor](src/jvmMain/kotlin/redtoss/poke/lib/CurlExecutor.kt) is an open class and can be inherited.

```
val curlExecutor = object : CurlExecutor{
    override suspend fun invoke(request: String): String? {
        val response = MagicCurlExecutor().getRequest(request)
        return response
    }
}
// The curExecutor needs to injected to the library/PokeApi
```

#### 4. Inject Curl execution function
The CurlExecutor can be set when the PokeApi is created.

```
val pokeApi = PokeApi()
pokeApi.setCurlExecutor(curlExecutor)
```

#### Serializing/Deserializing
Whenever a HTTP-Request is made, the response is received as json.
To serialize and deserialize the [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) plugin is used.  
Thankfully it has complete multiplatform support. :+1:

#### Caching (not yet implemented)
To enable any user to cache the results of requests, a caching implementation can be passed to the Library.

## Build
To build the Libraries, gradle is used.
`./gradlew clean build publishToMavenLocal`
The libraries are then in the [build/libs](build/libs) and [build/bin/native/](build/bin/native/) folder

| File      | Platform            |
|-----------|---------------------|
| PokeNativeLib-1.0-SNAPSHOT.jar | ?                   |
| PokeNativeLib-jsir-1.0-SNAPSHOT.klib | Kotlin/Native       |
| PokeNativeLib-jslegacy-1.0-SNAPSHOT.jar | ?                   |
| PokeNativeLib-jvm-1.0-SNAPSHOT.jar | JVM                 |
| PokeNativeLib-kotlin-1.0-SNAPSHOT-sources.jar | ?                   |
| PokeNativeLib-metadata-1.0-SNAPSHOT-all.jar | Libs with common    |
| PokeNativeLib-native-1.0-SNAPSHOT-metadata.jar | ?                   |
| libpokeapi.dylib                               | macos native binary |
| libpokeapi.so                                  | linux native binary |

### Version can be found/set in [gradle file](build.gradle.kts)