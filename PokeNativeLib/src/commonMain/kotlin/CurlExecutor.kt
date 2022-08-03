/**
 * Class to execute CURL-Requests and to return its response
 */
expect class CurlExecutor {
    fun invoke(request: String): String?
}
