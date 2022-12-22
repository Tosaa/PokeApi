# PokeLib
This Project contains a Library, which can be used to access the public API: https://pokeapi.co/  
The Library is written in `Kotlin` and is compatible to several platforms through `Kotlin Multiplatform`  
The Library can be found in /PokeNativeLib  

**!Notice**: The API is not complete accessible by this library.

The Library is used by several applications:
- PokeNativeC: A Main function written in C, which uses the Library (currently incomplete)
- PokeKotlinNative: A Main function written in Kotlin/Native, which uses the Library
- PokeNativeCPP: A Main function written in C++, which uses the Library (not implemented yet)
- PokeDesktop: A Main function written in Kotlin, which uses the Library
- PokeAndroid: An Application written in Kotlin, which uses the Library
- PokeWeb: A Web-application (website) written in Javascript, which uses the Library (not implemented yet)

## PokeNativeLib
For details see [PokeNativeLib by Multiplatform](/PokeNativeLib/readme.md).

## PokeNativeC
_A Main function written in C, which uses the Library._
- A Logging function is passed to the Library.
- A Curl function is passed to the Library

## PokeNativeCPP (not implemented yet)
_A Main function written in C++, which uses the Library._
- A Logging function is passed to the Library.
- A Curl function is passed to the Library

## PokeKotlinNative
_A Main function written in Kotlin/Native, which uses the Library._
- A Logging function is passed to the Library.
- A Curl function is passed to the Library

## PokeDesktop & PokeAndroid
 _An App that is written in Kotlin using the Compose Multiplatform framework to provide Desktop App + Android App_
 - Compose-Desktop and Android rely on JVM, so they can share code
 - Compose-Multiplatform contains shared code in its commonMain module which is using the Library

## PokeWeb (not implemented yet)
_A Website, that can be hosted which enables the User to search for Pokemon._
- T.b.d.