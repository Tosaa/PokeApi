package redtoss.poke.lib.serializable

internal class Wrapper {
    @kotlinx.serialization.Serializable
    data class Pokemon(
        val name: String,
        val height: Int,
        val weight: Int,
        val types: List<TypeSlot>
    )

    @kotlinx.serialization.Serializable
    data class TypeSlot(val slot: Int, val type: Type)

    @kotlinx.serialization.Serializable
    data class Type(val name: String, val url: String)
}
