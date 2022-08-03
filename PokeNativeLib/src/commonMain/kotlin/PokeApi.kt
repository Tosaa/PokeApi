import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.decodeFromString

/**
 * Class to execute CURL-Requests and to return its response
 */
expect class CurlExecutor {
    fun invoke(request: String): String?
}

@kotlinx.serialization.Serializable
data class Pokemon(val name: String)

class PokeApi {
    private val jsonDecoder = Json { ignoreUnknownKeys = true }
    private var curlExecutor: CurlExecutor? = null

    public fun setCurlExecutor(executor: CurlExecutor) {
        Logger.d { "setCurlExecutor()" }
        curlExecutor = executor
    }

    public fun findPokemon(name: String): Pokemon? {
        Logger.d { "findPokemon(): $name" }
        val url = "https://pokeapi.co/api/v2/pokemon/$name"

        return curlExecutor?.invoke(url)?.let { jsonToPokemon(it) }
    }

    private fun jsonToPokemon(json: String): Pokemon {
        Logger.d { "stringToPokemon(): ${json.replaceRange(10, json.length - 10, "...")}" }
        return try {
            jsonDecoder.decodeFromString(json)
        } catch (e: Exception) {
            Logger.d { "Exception: \n${e.printStackTrace()}" }
            Pokemon("UNKNOWN")
        }
    }
}
