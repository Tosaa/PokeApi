package redtoss.poke.lib.cache

import redtoss.poke.lib.Pokemon
import redtoss.poke.lib.logging.ILogger

internal interface PokemonCache {
    fun addPokemon(key: String, result: Pokemon?)
    fun removePokemon(key: String)
    fun editPokemon(key: String, editFunction: (Pokemon) -> Pokemon)
    fun getPokemonByURL(key: String): Pokemon?
    fun getPokemonByID(key: Int): Pokemon?
}