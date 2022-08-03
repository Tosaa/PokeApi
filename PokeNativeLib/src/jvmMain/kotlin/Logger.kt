actual object Logger {
    actual fun d(log: () -> String) {
        println(log.invoke())
    }
}
