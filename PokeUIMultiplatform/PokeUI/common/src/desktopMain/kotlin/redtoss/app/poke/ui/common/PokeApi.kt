package redtoss.app.poke.ui.common

import redtoss.poke.lib.CurlExecutor
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse

actual class PokeApi {
    private val pokeApi = redtoss.poke.lib.PokeApi()
    private val client = HttpClient.newBuilder().build();

    private val curlExecutor = object : CurlExecutor() {
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

