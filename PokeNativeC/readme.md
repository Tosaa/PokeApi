# PokeNativeC
The libs folder is symlinked to the [build/bin/](../PokeNativeLib/build/bin/) folder of the PokeNativeLib module.
This is required to receive the .so/.dylib files which are used in the [CMakeList.txt](CMakeLists.txt) file.

Be aware that curl needs to be available on the system.

### build
`mkdir [build folder]`
`cd [build folder]`
`cmake ..`
`make .`

### run
The program should be a CLI, but currently its just searching for 2 Pokemon and exits then again.

`./PokeNativeC`
