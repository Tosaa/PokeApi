import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import platform.posix.sleep
import redtoss.poke.lib.PokeApiClient

const val LOG_PREFIX = "Kotlin/Native:"
fun main() {
    var isDone = false
    println("$LOG_PREFIX Type in a Pokemon name like Pikachu, bulbasaur, charmander, squirtle or type 'exit' to stop the program")
    val api = PokeApiClient(logger = Logger())

    while (!isDone) {
        sleep(1)
        val input = readlnOrNull()?.trim()
        when {
            input == "exit" ->
                isDone = true

            !input.isNullOrEmpty() ->
                searchForPokemon(api, input)

            else ->
                Unit
        }
    }
    println("$LOG_PREFIX Done")
}


private fun searchForPokemon(api: PokeApiClient, pokemonName: String) {
    println("$LOG_PREFIX search for: $pokemonName")
    CoroutineScope(Dispatchers.Default).launch {
        val result = api.findPokemon(pokemonName)
        println("$LOG_PREFIX $result")
    }
}
