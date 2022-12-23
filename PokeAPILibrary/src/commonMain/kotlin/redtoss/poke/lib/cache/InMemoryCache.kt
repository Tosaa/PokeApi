package redtoss.poke.lib.cache

import redtoss.poke.lib.Pokemon

internal open class InMemoryCache : PokemonCache {
    private val nameCache = HashMap<String, Pokemon?>()
    private val idCache = HashMap<Int, String?>()
    override fun addPokemon(key: String, result: Pokemon?) {
        val name = if (key.isUrl()) {
            key.urlToPokemonName()
        } else {
            key
        }
        nameCache[name] = result
    }

    override fun removePokemon(key: String) {
        nameCache.remove(key)
    }

    override fun editPokemon(key: String, editFunction: (Pokemon) -> Pokemon) {
        val name = if (key.isUrl()) {
            key.urlToPokemonName()
        } else {
            key
        }
        nameCache[name]?.let { oldPokemon ->
            val newPokemon = editFunction.invoke(oldPokemon)
            nameCache.put(key, newPokemon)
        }
    }

    override fun getPokemonByURL(key: String): Pokemon? {
        val name = if (key.isUrl()) {
            key.urlToPokemonName()
        } else {
            key
        }
        return nameCache[name]
    }

    override fun getPokemonByID(key: Int): Pokemon? {
        return idCache[key]?.let { name ->
            nameCache[name]
        }
    }

    private fun String.isUrl(): Boolean = this.contains("/v2/")
    private fun String.urlToPokemonName(): String = this.split("/v2/").last()
}