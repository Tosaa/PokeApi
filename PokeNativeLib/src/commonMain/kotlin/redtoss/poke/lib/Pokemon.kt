package redtoss.poke.lib

import redtoss.poke.lib.serializable.Wrapper

public data class Pokemon constructor(
    val name: String,
    val height: Int,
    val weight: Int,
    val types: List<String>,
) {
    companion object {
        internal fun byWrapper(wrapper: Wrapper.Pokemon): Pokemon {
            return Pokemon(wrapper.name, wrapper.height, wrapper.weight, wrapper.types.map { it.type.name }.toList())
        }
    }
}
