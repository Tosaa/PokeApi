@kotlinx.serialization.Serializable
data class Pokemon(val name: String,
                   val height: Int,
                   val weight: Int,
    /*val types: List<TypeWrapper> = emptyList()*/
) {

    @kotlinx.serialization.Serializable
    data class TypeWrapper(val slot: Int, val type: Type)

    @kotlinx.serialization.Serializable
    data class Type(val name: String, val url: String)
}
