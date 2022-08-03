# PokeLib
This Project contains a Library, which can be used to access the public API: https://pokeapi.co/  
The Library is written in `Kotlin` and is compatible to several platforms through `Kotlin Multiplatform`  
The Library can be found in /PokeNativeLib  

**!Notice**: The API is not complete accessible by this library.

The Library is used by several applications:
- PokeNativeC: A Main function written in C, which uses the Library
- PokeNativeCPP: A Main function written in C++, which uses the Library (not implemented yet)
- PokeDesktop: A Main function written in Kotlin, which uses the Library (not implemented yet)
- Poke

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

## PokeDesktop (not implemented yet)
 _(Plan is to use Compose Desktop to provide a UI)_  
 Since JVM is used to created a Compose-Desktop application, the integration of the PokeNativeLib should be rather simple.
