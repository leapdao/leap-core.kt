package leapcore

import leapcore.lib.decode.fromHexString
import kotlin.test.assertTrue

fun <T> testProperty(gen: () -> T, property: (T) -> Boolean): Unit {
    for (x in 0..100) {
        assertTrue { property(gen()) }
    }
}

val outputPathIsIdentity: (Output) -> Boolean = {output ->
    Output.fromHexString(output.toHexString()) == output
}

val compareOutputSerialization: (Output) -> Boolean = {output ->
    output.toHexString() == marshallOutput(output).toRaw().toString("hex")
}

val compareOutputParsing: (hexString: String) -> Boolean = {hexString ->
    val original = LeapCore.Output.fromRaw(Buffer.from(hexString, "hex"))
    val new = Output.fromHexString(hexString)
    new == unmarshallOutput(original)
}