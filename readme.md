# PokeApiClient
This Project contains a Library, which can be used to access the public API: https://pokeapi.co/

The Library is written in `Kotlin` and is compatible to several platforms through `Kotlin Multiplatform`  
The Library can be found in [PokeAPILibrary](./PokeAPILibrary)  

**Notice**: The API is not completed yet accessible by this library. This means, not every Request can be made. 
Actually only `.../v2/pokemon/{name}` request can be made

The Library is used by several applications:
- PokeApplicationC: A Main function written in C, which uses the Library (currently incomplete)
- PokeApplicationKotlinNative: A Main function written in Kotlin/Native, which uses the Library
- PokeApplicationCpp: A Main function written in C++, which uses the Library (not implemented yet)
- PokeApplicationDesktop: A Main function written in Kotlin, which uses the Library
- PokeApplicationAndroid: An Application written in Kotlin, which uses the Library
- PokeApplicationWeb: A Web-application (website) written in Javascript, which uses the Library (not implemented yet)

## PokeAPILibrary
_A library that can be used to fetch Information of Pokemon from the public API: https://pokeapi.co/_
- [Code](./PokeAPILibrary)
- [readme](./PokeAPILibrary/readme.md)

## C-Application
_A Main function written in C, which provides a CLI to make use of the Library._  
- [Code](./PokeApplicationC)
- [readme](./PokeApplicationC/readme.md)

## Kotlin/Native-Application
_A Main function written in Kotlin/Native, which provides a CLI to make use of the Library._
- [Code](./PokeApplicationKotlinNative)
- [readme](./PokeApplicationKotlinNative/readme.md)

## C++-Application (not implemented yet)
_A Main function written in C++, which provides a CLI to make use of the Library._
- [Code](./PokeApplicationCpp)
- [readme](./PokeApplicationCpp/readme.md)

## Desktop-Application (Kotlin Multiplatform based)
_An Application that is written in Kotlin using the Compose Multiplatform framework to provide a Desktop App_
- [Code](./PokeApplicationMultiplatform)
- [readme](./PokeApplicationMultiplatform/readme.md)

## Android-Application (Kotlin Multiplatform based)
_An Application that is written in Kotlin using the Compose Multiplatform framework to provide an Android App_
- [Code](./PokeApplicationMultiplatform)
- [readme](./PokeApplicationMultiplatform/readme.md)

## Web-Application (not implemented yet)
_A Website, that can be hosted which enables the User to search for Pokemon._
- [Code](./PokeApplicationWeb)
- [readme](./PokeApplicationWeb/readme.md)
