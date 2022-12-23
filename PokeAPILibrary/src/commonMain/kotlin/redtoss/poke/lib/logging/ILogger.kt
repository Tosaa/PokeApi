package redtoss.poke.lib.logging

interface ILogger {
    fun d(log: () -> String)
    fun i(log: () -> String)
    fun v(log: () -> String)
    fun e(error: Throwable, log: () -> String)
    fun w(error: Throwable, log: () -> String)

    enum class Level(val shortcut: Char) {
        DEBUG('D'), INFO('I'), VERBOSE('V'), ERROR('E'), WARNING('W')
    }
}