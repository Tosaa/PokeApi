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

### Usage
#### Serializing/Deserializing
To serialize and deserialize the [kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization) plugin is used.  
Thankfully it has complete multiplatform support. :+1:

#### Logging
Since the targets do not share code for Logging, the Logging functionality has to be injected.  
The ross.poke.lib.Logger object will use the provided Logging function and logs any information within the Library through it.

#### Fetching Data (using the ross.poke.lib.CurlExecutor)
For executing Curl Requests, the ross.poke.lib.CurlExecutor is used. It provides a function that accepts a `URL` (as String / Char Array for KotlinNative) and returns the Response (String/CharArray).
Since the targets do not share code for sending CURL-Requests, the functionality has to be injected.

In a simple manner, one could say, the Library does only provide URLs' to use the PokeApi.

#### Caching (not yet implemented)
To enable any user to cache the results of requests, a caching implementation can be passed to the Library.

### Kotlin Native
To replace the [Loggers log](https://github.com/Tosaa/PokeApi/blob/master/PokeNativeLib/src/nativeMain/kotlin/ross.poke.lib.Logger.kt) function and the function [to curl information](https://github.com/Tosaa/PokeApi/blob/master/PokeNativeLib/src/nativeMain/kotlin/ross.poke.lib.CurlExecutor.kt), KotlinNatives `Pointer<CFunction<...>>` is used.
