# PokeApplicationC
_A Command line interface written in C to use the **PokeAPILibrary**._

### folder setup
The libs folder is symlinked to the [build/bin/](../PokeAPILibrary/build/bin/) folder of the PokeAPILibrary module.
This is required to receive the .so/.dylib files which are used in the [CMakeList.txt](CMakeLists.txt) file.

Be aware that curl needs to be available on the system.

### build
`mkdir [build folder]`
`cd [build folder]`
`cmake ..`
`make`

### run
The program should be a CLI, but currently its just searching for 2 Pokemon and exits then again.

`./PokeNativeC`


### Caution:
This application is currently not usable. 
Right now its unclear to me how certain variables and methods can be passed.
e.g. The Logger inherits from ILogger. In the C there is nothing like inheritance.
Thats why the assumption is made, to reassign the `ILogger.pinned` variable.
But this is not working.

Actually I could not find any documentation how a Kotlin/Native library can be called from C.
One last option is to create a custom .def file which defines some typealias for functions.