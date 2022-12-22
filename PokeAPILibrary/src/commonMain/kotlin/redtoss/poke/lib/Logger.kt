package redtoss.poke.lib

public expect object Logger {
    fun d(log: () -> String)
}
