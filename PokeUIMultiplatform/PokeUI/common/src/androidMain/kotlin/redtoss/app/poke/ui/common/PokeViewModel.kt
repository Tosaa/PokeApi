package redtoss.app.poke.ui.common

import androidx.lifecycle.ViewModel

actual class PokeViewModel : ViewModel() {
    private val pokeApi = PokeApi()
    actual fun findPokemon(name: String) : Result<Pokemon> {
        return pokeApi.findPokemonByName(name)
    }
}
