import io.ktor.client.HttpClient
import io.ktor.client.engine.curl.Curl
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.cinterop.MemScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import platform.posix.sleep
import redtoss.poke.lib.CurlExecutor
import redtoss.poke.lib.Logger
import redtoss.poke.lib.PokeApi

const val LOG_PREFIX = "Kotlin/Native:"
fun main() {
    var isDone = false
    val memscope = MemScope()
    println("$LOG_PREFIX Type in a Pokemon name like Pikachu, bulbasaur, charmander, squirtle or type 'exit' to stop the program")
    val api = PokeApi()
    val curlExecutor = CurlExecutor()
    val client = HttpClient(Curl)

    // Kotlin Functions
    val kCurlExecutorFunction: (suspend (String) -> String?) = { url: String ->

        println("$LOG_PREFIX URL: $url")
        val response = client.get(url)
        println("$LOG_PREFIX status: ${response.status}")
        if (response.status.isSuccess()) {
            response.bodyAsText()
        } else {
            ""
        }
    }
    val kLoggingFunction = { message: String -> println("$LOG_PREFIX $message") }

    curlExecutor.kfunction = kCurlExecutorFunction
    api.setCurlExecutor(curlExecutor)

    Logger.kLoggingFunction = kLoggingFunction

    while (!isDone) {
        sleep(1)
        val input = readLine()?.trim()
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


private fun searchForPokemon(api: PokeApi, pokemon: String) {
    println("$LOG_PREFIX search for: $pokemon")
    CoroutineScope(Dispatchers.Default).launch {
        val result = api.findPokemon(pokemon)
        println("$LOG_PREFIX $result")
    }
}
