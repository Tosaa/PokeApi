#include <stdio.h>
#include <libpokeapi_api.h>
#include <curl/curl.h>
#include <stdlib.h>
#include <string.h>

CURL *curl;
struct MemoryStruct chunk;
/**
 * Struct that is required, to cache the curl-response
 */
struct MemoryStruct {
    char *memory;
    size_t size;
};

/**
 * Method that is used to process the curl-response
 */
static size_t
WriteMemoryCallback(void *contents, size_t size, size_t nmemb, void *userp) {
    size_t realsize = size * nmemb;
    struct MemoryStruct *mem = (struct MemoryStruct *) userp;

    char *ptr = realloc(mem->memory, mem->size + realsize + 1);
    if (!ptr) {
        /* out of memory! */
        printf("MAIN\t not enough memory (realloc returned NULL)\n");
        return 0;
    }

    mem->memory = ptr;
    memcpy(&(mem->memory[mem->size]), contents, realsize);
    mem->size += realsize;
    mem->memory[mem->size] = 0;

    return realsize;
}

/**
 * Method that is used by the libpokeapi to send curl-requests
 */
char *send_curl_request(char *url) {
    CURLcode res;
    chunk.memory = malloc(1);  /* will be grown as needed by the realloc above */
    chunk.size = 0;    /* no data at this point */
    if (curl) {
        curl_easy_setopt(curl, CURLOPT_URL, url);
        curl_easy_setopt(curl, CURLOPT_WRITEFUNCTION, WriteMemoryCallback);
        curl_easy_setopt(curl, CURLOPT_WRITEDATA, (void *) &chunk);

        /* Perform the request, res will get the return code */
        res = curl_easy_perform(curl);
        /* Check for errors */
        if (res != CURLE_OK) {
            fprintf(stderr, "curl_easy_perform() failed: %s\n", curl_easy_strerror(res));
        } else {
            printf("MAIN\t send_curl_request(): %lu bytes retrieved\n", (unsigned long) chunk.size);
        }
    }
    // printf("MAIN\t send_curl_request(): Response: %s\n", chunk.memory);
    char *returnValue = chunk.memory;
    return returnValue;
}

/**
 * Method that is used by the libpokeapi to log information
 */
void *logAPI(char *input) {
    printf("API\t\t %s\n", input);
}

/**
 * Method that is used to setup the libpokeapi
 */
libpokeapi_kref_PokeApi set_up_api(libpokeapi_ExportedSymbols *lib) {
    // Setup Logger
    lib->kotlin.root.Logger.set_loggingFunction(lib->kotlin.root.Logger._instance(), logAPI);
    // Create API instance
    libpokeapi_kref_PokeApi api = lib->kotlin.root.PokeApi.PokeApi();
    // Create Handler Instance and set Function
    printf("MAIN\t create CurlExecutor\n");
    libpokeapi_kref_CurlExecutor handler = lib->kotlin.root.CurlExecutor.CurlExecutor();
    lib->kotlin.root.CurlExecutor.set_function(handler, send_curl_request);
    // Set Handler to API
    lib->kotlin.root.PokeApi.setCurlExecutor(api, handler);

    return api;
}

/**
 * internal method to execute requests
 */
void *searchForPokemon(libpokeapi_ExportedSymbols *lib, libpokeapi_kref_PokeApi api, char *pokemon_name) {
    printf("MAIN\t find Pokemon: %s\n", pokemon_name);
    libpokeapi_kref_Pokemon pokemon_instance = lib->kotlin.root.PokeApi.findPokemon(api, pokemon_name);
    printf("MAIN\t get Name of pokemon: ");
    const char *name = lib->kotlin.root.Pokemon.get_name(pokemon_instance);
    printf("%s\n", name);
}


int main() {
    // setup Curl
    curl = curl_easy_init();
    //obtain reference for calling Kotlin/Native functions
    libpokeapi_ExportedSymbols *lib = libpokeapi_symbols();
    libpokeapi_kref_PokeApi api = set_up_api(lib);
    searchForPokemon(lib, api, "ditto");
    searchForPokemon(lib, api, "pikachu");

    free(chunk.memory);
    curl_easy_cleanup(curl);
    return 0;
}
