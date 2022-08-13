package redtoss.app.poke.ui.common

expect class PokeApi() {
    fun findPokemonByName(name: String): Result<Pokemon>
}
