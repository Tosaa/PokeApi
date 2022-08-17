package redtoss.app.poke.ui.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.lighthousegames.logging.logging
import redtoss.poke.lib.CurlExecutor
import redtoss.poke.lib.Logger
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.URL
import java.util.concurrent.Executor

actual class PokeApi {
    private val pokeApi = redtoss.poke.lib.PokeApi()
    private val apiLogger = logging("PokeApiLib")
    private val logger = logging()
    private lateinit var executor: Executor

    init {
        Logger.loggingFunction = { log ->
            apiLogger.d { log }
        }

    }

    private val curlExecutor = object : CurlExecutor() {
        override suspend fun invoke(request: String): String? {
            logger.d { "received Request: $request" }

            val urlRequest = URL(request)
            return try {
                val openURLConnection = urlRequest.openConnection()
                val inputStream: InputStream = BufferedInputStream(openURLConnection.getInputStream())
                val response = inputStream.readBytes().toString(Charsets.UTF_8)
                logger.d { "Received Response: $response" }
                response
            } catch (e: Exception) {
                logger.e(e) { "Could not openConnection() for url" }
                null
            }
        }
    }

    init {
        pokeApi.setCurlExecutor(curlExecutor)
    }

    actual suspend fun findPokemonByName(name: String): Result<Pokemon> {
        val fetchedPokemon =
            pokeApi.findPokemon(name) ?: return Result.failure(Exception("API found no result for Pokemon: '$name'"))
        return Result.success(
            Pokemon(
                fetchedPokemon.name,
                fetchedPokemon.height,
                fetchedPokemon.weight,
                fetchedPokemon.types
            )
        )
    }
}
