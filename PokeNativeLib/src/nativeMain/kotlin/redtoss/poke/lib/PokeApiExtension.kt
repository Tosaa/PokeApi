package redtoss.poke.lib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias PokemonResultFunction = (Pokemon?) -> Unit

// Utility function to call findPokemon from C, since in C suspend functions cannot be called
fun PokeApi.cFindPokemon(name: String, onResult: PokemonResultFunction) {
    CoroutineScope(Dispatchers.Unconfined).launch {
        val pokemon = this@cFindPokemon.findPokemon(name)
        // onResult(pokemon)
    }
}
