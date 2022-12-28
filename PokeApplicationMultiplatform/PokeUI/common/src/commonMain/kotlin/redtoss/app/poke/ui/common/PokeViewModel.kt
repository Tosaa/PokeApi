package redtoss.app.poke.ui.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import redtoss.poke.lib.PokeApiClient
import redtoss.poke.lib.Pokemon

class PokeViewModel(val pokeApi: PokeApiClient) {
     val latestSearchedPokemon: MutableState<Pokemon?> = mutableStateOf(null)
     val coroutineScope = CoroutineScope(Dispatchers.Default)

     fun findPokemon(name: String) {
        coroutineScope.launch {
            latestSearchedPokemon.value = pokeApi.findPokemon(name)
        }
    }
}
