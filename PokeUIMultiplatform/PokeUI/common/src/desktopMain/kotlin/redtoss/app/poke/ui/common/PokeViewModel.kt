package redtoss.app.poke.ui.common

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

actual class PokeViewModel {
    actual val pokeApi = PokeApi()
    actual val latestSearchedPokemon: MutableState<Pokemon?> = mutableStateOf(null)
    actual val coroutineScope = CoroutineScope(Dispatchers.IO)

    actual fun findPokemon(name: String) {
        coroutineScope.launch {
            latestSearchedPokemon.value = pokeApi.findPokemonByName(name).getOrElse {
                null
            }
        }
    }
}
