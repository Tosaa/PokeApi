cmake_minimum_required(VERSION 3.23)
project(PokeNativeC C)

set(CMAKE_C_STANDARD 99)

# add_subdirectory(libs)
add_executable(PokeNativeC src/main.c)

find_library(PokeNativeLibDebugLibrary libpokeapi.so ./libs/native/debugShared)
find_path(PokeNativeLibDebugHeader libpokeapi_api.h ./libs/native/debugShared)
include_directories("${PokeNativeLibDebugHeader}")

find_package(CURL REQUIRED)

# target_link_directories(PokeNativeC PUBLIC "${PROJECT_SOURCE_DIR}/libs/debugShared")
target_link_libraries(PokeNativeC "${PokeNativeLibDebugLibrary}" "${CURL_LIBRARIES}")
# target_include_directories(PokeNativeC PUBLIC "${PROJECT_SOURCE_DIR}/libs/debugShared")