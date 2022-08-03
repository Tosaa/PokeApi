import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke
import kotlinx.cinterop.toKStringFromUtf8

actual class CurlExecutor {
    var function: CPointer<CFunction<(CValuesRef<ByteVar>) -> CPointer<ByteVar>?>>? = null
    actual fun invoke(request: String): String? {
        return function?.invoke(request.cstr)?.toKStringFromUtf8()
    }

}
