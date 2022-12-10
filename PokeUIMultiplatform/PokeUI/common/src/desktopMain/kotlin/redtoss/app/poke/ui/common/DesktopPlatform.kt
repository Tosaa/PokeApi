package redtoss.app.poke.ui.common

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import redtoss.poke.lib.CurlExecutor
import redtoss.poke.lib.Logger
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

actual fun getCurlExecutor(): CurlExecutor {
    val client = HttpClient.newBuilder().build();
    val curlExecutor = object : CurlExecutor() {
        override suspend fun invoke(request: String): String? {
            val httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(request.trim()))
                .build()

            val response = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            return if (response.statusCode().toString().startsWith("2")) {
                response.body()
            } else {
                null
            }
        }
    }

    return curlExecutor
}

actual fun initPlatform() {
    Logger.loggingFunction = { message -> println("desktopMain(jvm): $message") }
}
