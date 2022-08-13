package redtoss.app.poke.ui.common

expect class PokeViewModel() {
    fun findPokemon(name: String): Pokemon?
}
