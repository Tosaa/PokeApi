#include <libpokeapi_api.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
    printf("Hello World\n");
    //obtain reference for calling Kotlin/Native functions
    libpokeapi_ExportedSymbols *lib = libpokeapi_symbols();

    libpokeapi_kref_kotlin_Function1 function1;
    _Bool cachingEnabled = 1;
    libpokeapi_kref_redtoss_poke_lib_PokeApiClient apiClient = lib->kotlin.root.redtoss.poke.lib.createDefaultPokeApiClient();
    lib->kotlin.root.redtoss.poke.lib.findPokemonOnCurrentThread(apiClient, "Pikachu");
    return 0;
}