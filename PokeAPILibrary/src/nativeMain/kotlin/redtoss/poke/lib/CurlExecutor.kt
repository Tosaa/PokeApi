package redtoss.poke.lib

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.cinterop.toKString

actual class CurlExecutor {
    var cfunction: CPointer<CFunction<(CValuesRef<ByteVar>) -> CPointer<ByteVar>?>>? = null
    var kfunction: (suspend (String) -> String?)? = null
    actual suspend fun invoke(request: String): String? {
        return when{
            cfunction != null ->
                cfunction?.invoke(request.cstr)?.toKString()

            kfunction != null ->
                kfunction?.invoke(request)

            else -> null
        }
    }

}
