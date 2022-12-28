package redtoss.poke.lib

import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import redtoss.poke.lib.logging.Logger
import redtoss.poke.lib.serializable.Wrapper

public data class Pokemon constructor(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<String> = emptyList(),
    val evolutions: List<Int> = emptyList()
) {
    companion object {
        internal val unknownPokemon = Pokemon(id = -1, name = "UNKNOWN", height = 0, weight = 0)

        internal fun byWrapper(wrapper: Wrapper.Pokemon): Pokemon {
            return Pokemon(
                wrapper.id,
                wrapper.name,
                wrapper.height,
                wrapper.weight,
                wrapper.types.map { it.type.name }.toList()
            )
        }

        internal fun byJson(json: String, logger: Logger? = null): Pokemon {
            val jsonDecoder = Json { ignoreUnknownKeys = true }

            return if (json.isNotEmpty()) {
                return try {
                    val wrappedPokemon = jsonDecoder.decodeFromString<Wrapper.Pokemon>(json)
                    byWrapper(wrappedPokemon)
                } catch (e: SerializationException) {
                    logger?.w(e) { "SerializationException" }
                    unknownPokemon
                } catch (e: IllegalArgumentException) {
                    logger?.w(e) { "IllegalArgumentException" }
                    unknownPokemon
                } catch (e: Exception) {
                    logger?.w(e) { "Unexpected Exception" }
                    unknownPokemon
                }
            } else {
                unknownPokemon
            }
        }
    }
}
