package redtoss.poke.lib

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import redtoss.poke.lib.serializable.Wrapper

public class PokeApi {
    private val jsonDecoder = Json { ignoreUnknownKeys = true }
    private var curlExecutor: CurlExecutor? = null
    private val unknownPokemon = Pokemon("UNKNOWN", 0, 0, emptyList())

    public fun setCurlExecutor(executor: CurlExecutor) {
        Logger.d { "setCurlExecutor(): $executor" }
        curlExecutor = executor
    }

    // Making findPokemon a `suspend fun`, gives the callee the force
    // to select the coroutine on which the request should be made.
    // suspend functions are not compiled for native-Code.
    public suspend fun findPokemon(name: String): Pokemon? {
        val validName = name.lowercase().replace("/","").replace(".","").replace("*","")
        Logger.d { "findPokemon(): name: '$validName'" }
        val url = "https://pokeapi.co/api/v2/pokemon/${validName}"
        return curlExecutor?.invoke(url)?.let { jsonToPokemon(it) }
    }

    private fun jsonToPokemon(json: String): Pokemon {
        return if (json.isNotEmpty()) {
            Logger.d { "stringToPokemon(): ${json.take(3)}...${json.takeLast(3)}" }
            return try {
                val wrappedPokemon = jsonDecoder.decodeFromString<Wrapper.Pokemon>(json)
                Pokemon.byWrapper(wrappedPokemon)
            } catch (e: SerializationException) {
                Logger.d { "SerializationException: \n${e.printStackTrace()}" }
                unknownPokemon
            } catch (e: IllegalArgumentException) {
                Logger.d { "IllegalArgumentException: \n${e.printStackTrace()}" }
                unknownPokemon
            } catch (e: Exception) {
                Logger.d { "Unexpected Exception: \n${e.printStackTrace()}" }
                unknownPokemon
            }
        } else {
            unknownPokemon
        }
    }
}
