package redtoss.poke.lib

import kotlinx.cinterop.ByteVar
import kotlinx.cinterop.CFunction
import kotlinx.cinterop.CPointer
import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.cstr
import kotlinx.cinterop.invoke

@ThreadLocal
actual object Logger {
    var cLoggingFunction: CPointer<CFunction<(CValuesRef<ByteVar>) -> Unit>>? = null
    var kLoggingFunction: ((String) -> Unit)? = null

    actual fun d(log: () -> String) {
        val printedLog = log.invoke()
        cLoggingFunction?.invoke(printedLog.cstr)
        kLoggingFunction?.invoke(printedLog)
    }

}
