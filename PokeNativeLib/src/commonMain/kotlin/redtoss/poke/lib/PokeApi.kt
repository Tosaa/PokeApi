package redtoss.poke.lib

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import redtoss.poke.lib.serializable.Wrapper

public class PokeApi {
    private val jsonDecoder = Json { ignoreUnknownKeys = true }
    private var curlExecutor: CurlExecutor? = null

    public fun setCurlExecutor(executor: CurlExecutor) {
        Logger.d { "setCurlExecutor(): $executor" }
        curlExecutor = executor
    }

    // Making findPokemon a `suspend fun`, gives the callee the force
    // to select the coroutine on which the request should be made.
    // suspend functions are not compiled for native-Code.
    public suspend fun findPokemon(name: String): Pokemon? {
        Logger.d { "findPokemon(): name: '$name'" }
        val url = "https://pokeapi.co/api/v2/pokemon/$name"
        return curlExecutor?.invoke(url)?.let { jsonToPokemon(it) }
    }

    private fun jsonToPokemon(json: String): Pokemon {
        Logger.d { "stringToPokemon(): ${json.replaceRange(10, json.length - 10, "...").replace("\n", "").trim()}" }
        return try {
            val wrappedPokemon = jsonDecoder.decodeFromString<Wrapper.Pokemon>(json)
            Pokemon.byWrapper(wrappedPokemon)
        } catch (e: SerializationException) {
            Logger.d { "SerializationException: \n${e.printStackTrace()}" }
            Pokemon("UNKNOWN", 0, 0, emptyList())
        } catch (e: IllegalArgumentException) {
            Logger.d { "IllegalArgumentException: \n${e.printStackTrace()}" }
            Pokemon("UNKNOWN", 0, 0, emptyList())
        } catch (e: Exception) {
            Logger.d { "Unexpected Exception: \n${e.printStackTrace()}" }
            Pokemon("UNKNOWN", 0, 0, emptyList())
        }
    }
}
