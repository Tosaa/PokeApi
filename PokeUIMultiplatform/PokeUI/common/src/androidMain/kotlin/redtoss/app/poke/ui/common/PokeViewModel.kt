package redtoss.app.poke.ui.common

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.lighthousegames.logging.logging

actual class PokeViewModel : ViewModel() {
    actual val pokeApi = PokeApi()
    actual val coroutineScope = CoroutineScope(Dispatchers.IO)
    actual val latestSearchedPokemon: MutableState<Pokemon?> = mutableStateOf(null)
    actual fun findPokemon(name: String) {
        coroutineScope.launch {
            latestSearchedPokemon.value = pokeApi.findPokemonByName(name).getOrElse {
                null
            }
        }
    }
}
