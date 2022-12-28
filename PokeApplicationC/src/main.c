#include <libpokeapi_api.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

void custom_print(char *str) {
    printf("Main: %s\n", str);
    return;
}

int main() {
    custom_print("Hello World");
    //obtain reference for calling Kotlin/Native functions
    libpokeapi_ExportedSymbols *lib = libpokeapi_symbols();

    libpokeapi_kref_kotlin_Function1 logging_function;
    logging_function.pinned = (void *) custom_print;

    libpokeapi_kref_redtoss_poke_lib_logging_Logger logger = lib->kotlin.root.redtoss.poke.lib.logging.Logger.Logger();
    lib->kotlin.root.redtoss.poke.lib.logging.Logger.set_loggingFunction(logger, custom_print);

    libpokeapi_kref_redtoss_poke_lib_logging_ILogger ilogger;
    ilogger.pinned = logger.pinned;

    _Bool cachingEnabled = 1;
    libpokeapi_kref_redtoss_poke_lib_PokeApiClient apiClient = lib->kotlin.root.redtoss.poke.lib.PokeApiClient.PokeApiClient(ilogger, cachingEnabled);
    lib->kotlin.root.redtoss.poke.lib.findPokemonOnCurrentThread(apiClient, "Pikachu");
    return 0;
}