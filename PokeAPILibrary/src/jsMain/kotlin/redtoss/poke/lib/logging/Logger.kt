package redtoss.poke.lib.logging

actual class Logger : ILogger {
    actual override fun d(log: () -> String) {
        logOnConsole(ILogger.Level.DEBUG, log, null)
    }

    actual override fun i(log: () -> String) {
        logOnConsole(ILogger.Level.INFO, log, null)
    }

    actual override fun v(log: () -> String) {
        logOnConsole(ILogger.Level.VERBOSE, log, null)
    }

    actual override fun e(error: Throwable, log: () -> String) {
        logOnConsole(ILogger.Level.ERROR, log, error)
    }

    actual override fun w(error: Throwable, log: () -> String) {
        logOnConsole(ILogger.Level.WARNING, log, error)
    }

    private fun logOnConsole(level: ILogger.Level, log: () -> String, throwable: Throwable?) {
        console.log("${level.shortcut} ${log()} ${throwable?.stackTraceToString()}")
    }
}