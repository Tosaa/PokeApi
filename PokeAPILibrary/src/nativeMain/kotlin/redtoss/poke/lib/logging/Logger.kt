package redtoss.poke.lib.logging

import redtoss.poke.lib.logging.ILogger.Level

public actual class Logger : ILogger {
    public var loggingFunction: ((String) -> Unit)? = null

    actual override fun d(log: () -> String) {
        useInjectedLoggingFunction(Level.DEBUG, log, null)
    }

    actual override fun i(log: () -> String) {
        useInjectedLoggingFunction(Level.INFO, log, null)
    }

    actual override fun v(log: () -> String) {
        useInjectedLoggingFunction(Level.VERBOSE, log, null)
    }

    actual override fun e(error: Throwable, log: () -> String) {
        useInjectedLoggingFunction(Level.ERROR, log, error)
    }

    actual override fun w(error: Throwable, log: () -> String) {
        useInjectedLoggingFunction(Level.WARNING, log, error)
    }

    private fun useInjectedLoggingFunction(level: Level, log: () -> String, throwable: Throwable?) {
        loggingFunction?.invoke("${level.shortcut} ${log()} ${throwable?.stackTraceToString()}")
    }
}