package redtoss.poke.lib

/**
 * Class to execute CURL-Requests and to return its response
 */
public expect class CurlExecutor {
    fun invoke(request: String): String?
}
