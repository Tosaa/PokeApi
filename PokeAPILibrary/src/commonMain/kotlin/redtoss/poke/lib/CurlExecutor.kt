package redtoss.poke.lib

/**
 * Class to execute CURL-Requests and to return its response
 */
public expect class CurlExecutor {
    // Making invoke a `suspend fun`, gives the callee the force
    // to select the coroutine on which the request should be made.
    suspend fun invoke(request: String): String?
}
