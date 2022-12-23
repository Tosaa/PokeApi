package redtoss.poke.lib

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import redtoss.poke.lib.cache.InMemoryCache
import redtoss.poke.lib.cache.PokemonCache
import redtoss.poke.lib.cache.withLogging
import redtoss.poke.lib.logging.DummyLogger
import redtoss.poke.lib.logging.ILogger

public class PokeApiClient internal constructor(
    private val logger: ILogger = DummyLogger(),
    private val isCachingEnabled: Boolean = true,
    private val cache: PokemonCache = InMemoryCache().withLogging(logger),
    private val httpClient: HttpClient = HttpClient()
) {
    public constructor(logger: ILogger = DummyLogger(), isCachingEnabled: Boolean = true) : this(
        logger = logger,
        isCachingEnabled = isCachingEnabled,
        cache = InMemoryCache().withLogging(logger),
        httpClient = HttpClient()
    )

    // Making findPokemon a `suspend fun`, gives the callee the force
    // to select the coroutine on which the request should be made.
    // suspend functions are not compiled for cinterop code!
    public suspend fun findPokemon(name: String): Pokemon {
        logger.i { "findPokemon(): name = $name" }
        val validName = name.lowercase().replace("/", "").replace(".", "").replace("*", "")
        val url = "https://pokeapi.co/api/v2/pokemon/${validName}"
        if (isCachingEnabled) {
            logger.d { "findPokemon(): caching is enabled -> look in cache for Request" }
            cache.getPokemonByURL(validName)?.let {
                logger.d { "findPokemon(): found Request in cache" }
                return it
            }
        }
        return findPokemonByHTTPRequest(url)
    }

    private suspend fun findPokemonByHTTPRequest(url: String): Pokemon {
        return httpClient.also {
            logger.d { "findPokemonByHTTPRequest(): execute get-Request with URL = $url" }
        }.get(url).bodyAsText().let { result ->
            Pokemon.byJson(result).also { pokemon ->
                if (isCachingEnabled) {
                    cache.addPokemon(pokemon.name, pokemon)
                }
            }
        }
    }

    /*
        public suspend fun findEvolutions(pokemon: Pokemon): List<Pokemon> {
            logger.i { "findEvolution(): pokemon = ${pokemon.name}" }
            val url = ""
            val evolutions: List<Pokemon> = if (isCachingEnabled) {
                logger.d { "findEvolution(): caching is enabled -> look in cache for Evolution" }
                cache.getPokemonByURL(pokemon.name)?.let { cachedPokemon ->
                    if (cachedPokemon.evolutions.isNotEmpty()) {
                        logger.d { "findEvolution(): found Evolution in cache" }
                        cachedPokemon.evolutions.mapNotNull { cache.getPokemonByID(it) }
                    } else {
                        emptyList()
                    }
                } ?: emptyList()
            } else {
                emptyList()
            }
            return evolutions.ifEmpty {
                findEvolutionsByHTTPRequest(url, pokemon.name)
            }
        }

        private suspend fun findEvolutionsByHTTPRequest(url: String, pokemonName: String): List<Pokemon> {
            return httpClient.also {
                logger.d { "findEvolutions(): execute get-Request with URL = $url" }
            }
                .get(url)
                .bodyAsText()
                .let<String, List<Pokemon>> { response ->
                    logger.v { "findEvolutions(): received Response = $response" }
                    emptyList() // todo: Map response to Pokemon list
                }.also { evolutions ->
                    cache.editPokemon(pokemonName) { oldPokemon ->
                        oldPokemon.copy(evolutions = evolutions.map { it.id })
                    }
                }
        }
    */
}
