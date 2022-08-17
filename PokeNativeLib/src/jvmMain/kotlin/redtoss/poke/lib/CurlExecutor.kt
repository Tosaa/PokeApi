package redtoss.poke.lib

actual open class CurlExecutor {

    actual open suspend fun invoke(request: String): String? {

        return request
    }

}
