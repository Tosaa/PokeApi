package redtoss.app.poke.ui.common

actual class PokeViewModel {
    private val pokeApi = PokeApi()
    actual fun findPokemon(name: String): Pokemon? {
        return pokeApi.findPokemonByName(name)
    }

}
