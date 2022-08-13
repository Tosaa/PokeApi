package redtoss.app.poke.ui.common

data class Pokemon(
    val name: String = "UNKNOWN",
    val height: Int = 0,
    val weight: Int = 0,
    val types: List<String> = emptyList()
) {
}
