package redtoss.poke.lib.cache

import redtoss.poke.lib.Pokemon
import redtoss.poke.lib.logging.ILogger

internal class CacheLoggingDecorator(private val realCache: PokemonCache, private val logger: ILogger) : PokemonCache {
    override fun addPokemon(key: String, result: Pokemon?) {
        logger.v { "addPokemon(): key = $key, value = $result" }
        realCache.addPokemon(key, result)
    }

    override fun removePokemon(key: String) {
        logger.v { "removePokemon(): key = $key" }
        realCache.removePokemon(key)
    }

    override fun editPokemon(key: String, editFunction: (Pokemon) -> Pokemon) {
        logger.v { "editPokemon(): key = $key" }
        realCache.editPokemon(key, editFunction)
    }

    override fun getPokemonByURL(key: String): Pokemon? {
        logger.v { "getPokemonByURL(): key = $key" }
        return realCache.getPokemonByURL(key).also {
            logger.v { "getPokemonByURL(): found = $it" }
        }
    }

    override fun getPokemonByID(key: Int): Pokemon? {
        logger.v { "getPokemonByID(): key = $key" }
        return realCache.getPokemonByID(key).also {
            logger.v { "getPokemonByID(): found = $it" }
        }
    }
}

internal fun PokemonCache.withLogging(logger: ILogger): PokemonCache {
    return if (this !is CacheLoggingDecorator)
        CacheLoggingDecorator(this, logger)
    else
        this
}