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

actual fun getCurlExecutor(): CurlExecutor {
    val logger = Logger
    val curlExecutor = object : CurlExecutor() {
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
                logger.d { "Could not openConnection() for url: Exception : $e" }
                null
            }
        }
    }
    return curlExecutor
}

actual fun initPlatform() {
    val androidLogger = logging("PokeApiLib")
    Logger.loggingFunction = { message -> androidLogger.d { "Android(jvm): $message" } }
}
