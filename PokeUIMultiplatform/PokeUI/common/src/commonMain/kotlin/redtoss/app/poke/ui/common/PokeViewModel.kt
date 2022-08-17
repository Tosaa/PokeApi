package redtoss.app.poke.ui.common

import androidx.compose.runtime.MutableState
import kotlinx.coroutines.CoroutineScope

expect class PokeViewModel() {
    val pokeApi: PokeApi
    val latestSearchedPokemon: MutableState<Pokemon?>
    val coroutineScope: CoroutineScope
    fun findPokemon(name: String)
}
