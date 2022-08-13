package redtoss.app.poke.ui.common

actual class PokeViewModel {
    private val pokeApi = PokeApi()
    actual fun findPokemon(name: String): Result<Pokemon> {
        return pokeApi.findPokemonByName(name)
    }

}
