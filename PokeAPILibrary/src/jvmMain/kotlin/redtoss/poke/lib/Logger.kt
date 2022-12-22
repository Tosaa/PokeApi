package redtoss.poke.lib

actual object Logger {
    var loggingFunction: ((String) -> Unit)? = null
    actual fun d(log: () -> String) {
        loggingFunction?.invoke(log.invoke())
    }
}
