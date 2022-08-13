package redtoss.app.poke.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    val pokeViewModel = PokeViewModel()
    val searchResult: MutableState<Pokemon?> = mutableStateOf(Pokemon())
    Column(Modifier.fillMaxWidth()) {
        Searchbar(viewModel = pokeViewModel) { searchResult.value = it }
        Row {
            PokemonResult(searchResult, modifier = Modifier.weight(1f))
            Spacer(Modifier.weight(1f))
        }
    }
}

@Composable
fun PokemonResult(pokemon: MutableState<Pokemon?>, modifier: Modifier? = null) {
    Column(modifier ?: Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()) {
            Text("Pokemon:", Modifier.weight(1f))
            Text(pokemon.value?.name ?: "UNKNOWN", Modifier.weight(1f))
        }
        Divider()
        Row(Modifier.fillMaxWidth()) {
            Text("Height:", Modifier.weight(1f))
            Text(pokemon.value?.height?.toString() ?: "0", Modifier.weight(1f))
        }
        Divider()
        Row(Modifier.fillMaxWidth()) {
            Text("Weight:", Modifier.weight(1f))
            Text(pokemon.value?.weight?.toString() ?: "0", Modifier.weight(1f))
        }
        Divider()
        Text("Types:")
        Column(Modifier.padding(8.dp)) {
            pokemon.value?.types?.forEach {
                Text(it)
            }
        }
    }
}

@Composable
fun Searchbar(viewModel: PokeViewModel, onSearchResult: (Pokemon?) -> Unit) {
    val searchTextField: MutableState<String> = remember { mutableStateOf("") }
    Row(Modifier.fillMaxWidth()) {
        TextField(
            value = searchTextField.value,
            onValueChange = { pokemonName ->
                searchTextField.value = pokemonName
                if (pokemonName.contains("\n")) {
                    viewModel.findPokemon(pokemonName.trim()).let { pokemon ->
                        onSearchResult.invoke(pokemon)
                    }
                    searchTextField.value = ""
                }
            },
            modifier = Modifier.weight(1f)
        )
        Button(
            onClick = {
                viewModel.findPokemon(searchTextField.value).let { pokemon ->
                    onSearchResult.invoke(pokemon)
                }
                searchTextField.value = ""
            },
            modifier = Modifier.weight(1f)
        ) { Text("Search") }
    }
}
