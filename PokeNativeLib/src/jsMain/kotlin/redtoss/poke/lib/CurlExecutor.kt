package redtoss.poke.lib

actual class CurlExecutor {
    actual fun invoke(request: String): String? {
        return request
    }
}
