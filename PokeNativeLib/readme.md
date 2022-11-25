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
#### Serializing/Deserializing
To serialize and deserialize the [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) plugin is used.  
Thankfully it has complete multiplatform support. :+1:

#### Logging
Since Logging is very platform dependent, a base Interface can be implemented by the user/platform itself and be injected to the library.  
The ross.poke.lib.Logger object will use the provided Logging function and logs any information within the Library through it.

The Logger is created with the runtime, since it is a singleton. The loggers logging function can be set during runtime by the user of this library.
How the logging function can be set is platform dependent. Here is the example for the JVM [Logger](/src/jvmMain/kotlin/redtoss/poke/lib/Logger.kt):

```
Logger.loggingFunction = {message->
    print(message)
}
```

#### Fetching Data (using the ross.poke.lib.CurlExecutor)
For executing Curl Requests, the ross.poke.lib.CurlExecutor is used. It provides a function that accepts a `URL` (as String / Char Array for KotlinNative) and returns the Response (String/CharArray).
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
val pokeApi = PokeApi()
pokeApi.setCurlExecutor(curlExecutor)
```

#### Caching (not yet implemented)
To enable any user to cache the results of requests, a caching implementation can be passed to the Library.

### Kotlin Native
To replace the [Loggers log](https://github.com/Tosaa/PokeApi/blob/master/PokeNativeLib/src/nativeMain/kotlin/ross.poke.lib.Logger.kt) function and the function [to curl information](https://github.com/Tosaa/PokeApi/blob/master/PokeNativeLib/src/nativeMain/kotlin/ross.poke.lib.CurlExecutor.kt), KotlinNatives `Pointer<CFunction<...>>` is used.
