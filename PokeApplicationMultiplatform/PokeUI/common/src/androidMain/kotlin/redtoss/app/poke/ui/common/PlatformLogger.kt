package redtoss.app.poke.ui.common

import org.lighthousegames.logging.logging
import redtoss.poke.lib.logging.ILogger

actual class PlatformLogger : ILogger {

    private val androidLogger = logging("androidMain(jvm)")
    override fun d(log: () -> String) {
        androidLogger.d { log() }
    }

    override fun e(error: Throwable, log: () -> String) {
        androidLogger.e(error) { log() }
    }

    override fun i(log: () -> String) {
        androidLogger.i { log() }
    }

    override fun v(log: () -> String) {
        androidLogger.v { log() }
    }

    override fun w(error: Throwable, log: () -> String) {
        androidLogger.w(error) { log() }
    }

}