package redtoss.app.poke.ui.common

expect class PokeApi() {
    suspend fun findPokemonByName(name: String): Result<Pokemon>
}
