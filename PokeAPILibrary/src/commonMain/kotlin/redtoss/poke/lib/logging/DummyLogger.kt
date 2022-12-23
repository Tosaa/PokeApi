package redtoss.poke.lib.logging

internal class DummyLogger : ILogger {
    override fun d(log: () -> String) {}

    override fun i(log: () -> String) {}

    override fun v(log: () -> String) {}

    override fun e(error: Throwable, log: () -> String) {}

    override fun w(error: Throwable, log: () -> String) {}
}