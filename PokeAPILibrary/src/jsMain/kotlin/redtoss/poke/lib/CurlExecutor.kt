package redtoss.poke.lib

actual class CurlExecutor {
    actual suspend fun invoke(request: String): String? {
        return request
    }
}
