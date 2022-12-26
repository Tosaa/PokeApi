import redtoss.poke.lib.logging.ILogger

/*
Logger to log messages in Kotlin/Native

Nice to know: In Kotlin/Native an Interface of the common sourceset of the PokeAPILibrary can be used.
Q: Why then provide a Logger impl in the nativeMain of the PokeAPILibrary?
A: Because inheritance is not suited for the C-language and therefore no inheritance of redtoss.poke.lib.logging.ILogger is possible.

 */
class Logger : ILogger {
    override fun d(log: () -> String) {
        println("$LOG_PREFIX D ${log()} ")
    }

    override fun e(error: Throwable, log: () -> String) {
        println("$LOG_PREFIX E ${log()} ${error.stackTraceToString()}")
    }

    override fun i(log: () -> String) {
        println("$LOG_PREFIX I ${log()} ")
    }

    override fun v(log: () -> String) {
        println("$LOG_PREFIX V ${log()} ")
    }

    override fun w(error: Throwable, log: () -> String) {
        println("$LOG_PREFIX W ${log()} ${error.stackTraceToString()}")
    }

}