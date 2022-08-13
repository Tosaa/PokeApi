package redtoss.app.poke.ui.common

import redtoss.poke.lib.CurlExecutor

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

    actual fun findPokemonByName(name: String): Pokemon? {
        val fetchedPokemon = pokeApi.findPokemon(name) ?: return null
        return Pokemon(fetchedPokemon.name, fetchedPokemon.height, fetchedPokemon.weight, fetchedPokemon.types)
    }
}
