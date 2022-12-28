package redtoss.poke.lib

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

typealias PokemonResultFunction = (Pokemon?) -> Unit

// Utility function to call findPokemon from C, since in C suspend functions cannot be called
fun PokeApiClient.findPokemonOnCurrentThread(name: String, onResult: PokemonResultFunction) {
    CoroutineScope(Dispatchers.Unconfined).launch {
        onResult.invoke(findPokemon(name))
    }
}

// Utility function to call findPokemon from C, but without invoking any function afterwards
fun PokeApiClient.findPokemonOnCurrentThread(name: String) = findPokemonOnCurrentThread(name) { _ -> }

fun createDefaultPokeApiClient(): PokeApiClient = PokeApiClient()
