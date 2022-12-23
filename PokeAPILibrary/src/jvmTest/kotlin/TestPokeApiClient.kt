import redtoss.poke.lib.PokeApiClient
import redtoss.poke.lib.Pokemon
import redtoss.poke.lib.cache.InMemoryCache
import redtoss.poke.lib.logging.Logger

internal class TestCustomCache(
    var addPokemonCounter: Int = 0,
    var removePokemonCounter: Int = 0,
    var editPokemonCounter: Int = 0,
    var getPokemonByURLCounter: Int = 0,
    var getPokemonByIDCounter: Int = 0
) : InMemoryCache() {


    override fun addPokemon(key: String, result: Pokemon?) {
        addPokemonCounter += 1
        super.addPokemon(key, result)
    }

    override fun removePokemon(key: String) {
        removePokemonCounter += 1
        super.removePokemon(key)
    }

    override fun editPokemon(key: String, editFunction: (Pokemon) -> Pokemon) {
        editPokemonCounter += 1
        super.editPokemon(key, editFunction)
    }

    override fun getPokemonByURL(key: String): Pokemon? {
        getPokemonByURLCounter += 1
        return super.getPokemonByURL(key)
    }

    override fun getPokemonByID(key: Int): Pokemon? {
        getPokemonByIDCounter += 1
        return super.getPokemonByID(key)
    }
}