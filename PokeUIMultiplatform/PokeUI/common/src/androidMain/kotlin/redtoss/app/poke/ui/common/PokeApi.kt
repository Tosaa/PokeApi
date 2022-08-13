package redtoss.app.poke.ui.common

import redtoss.poke.lib.CurlExecutor
import kotlin.Result

actual class PokeApi {
    private val pokeApi = redtoss.poke.lib.PokeApi()
    private val curlExecutor = object : CurlExecutor() {
        override fun invoke(request: String): String? {
            return "TODO"
        }
    }

    init {
        pokeApi.setCurlExecutor(curlExecutor)
    }

    actual fun findPokemonByName(name: String): Result<Pokemon> {
        val fetchedPokemon = pokeApi.findPokemon(name) ?: return Result.failure(Exception("API found no result for Pokemon: '$name'"))
        return Result.success(Pokemon(fetchedPokemon.name, fetchedPokemon.height, fetchedPokemon.weight, fetchedPokemon.types))
    }
}
