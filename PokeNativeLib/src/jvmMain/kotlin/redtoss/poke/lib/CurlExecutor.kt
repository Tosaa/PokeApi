package redtoss.poke.lib

actual open class CurlExecutor {
    actual open fun invoke(request: String): String? {
        return request
    }
}
