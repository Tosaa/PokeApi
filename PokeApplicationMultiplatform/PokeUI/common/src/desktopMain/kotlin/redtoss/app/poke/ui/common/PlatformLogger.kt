package redtoss.app.poke.ui.common

import redtoss.poke.lib.logging.ILogger

actual class PlatformLogger : ILogger {
    private fun logOnConsole(level: ILogger.Level, message: String) {
        println("desktopMain(jvm): ${level.shortcut} $message")
    }

    override fun d(log: () -> String) {
        logOnConsole(ILogger.Level.DEBUG, log())
    }

    override fun e(error: Throwable, log: () -> String) {
        logOnConsole(ILogger.Level.ERROR, log() + error.stackTraceToString())
    }

    override fun i(log: () -> String) {
        logOnConsole(ILogger.Level.INFO, log())
    }

    override fun v(log: () -> String) {
        logOnConsole(ILogger.Level.VERBOSE, log())
    }

    override fun w(error: Throwable, log: () -> String) {
        logOnConsole(ILogger.Level.WARNING, log() + error.stackTraceToString())
    }
}