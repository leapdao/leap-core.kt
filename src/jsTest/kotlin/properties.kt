package leapcore

import leapcore.lib.decode.fromHexString
import kotlin.test.assertTrue

fun <T> testProperty(gen: () -> T, property: (T) -> Boolean): Unit {
    for (x in 0..100) {
        assertTrue { property(gen()) }
    }
}

val outputRoundaboutIsIdentity: (Output) -> Boolean = { output ->
    Output.fromHexString(output.toHexString()) == output
}
val outputSerializesIdentically: (Output) -> Boolean = { output ->
    output.toHexString() == marshallOutput(output).toRaw().toString("hex")
}
val outputDeserializesIdentically: (String) -> Boolean = { hexString ->
    val original = LeapCore.Output.fromRaw(Buffer.from(hexString, "hex"))
    val new = Output.fromHexString(hexString)
    new == unmarshallOutput(original)
}

val outputWithDataRoundaboutIsIdentity: (OutputWithData) -> Boolean = { outputWithData ->
    OutputWithData.fromHexString(outputWithData.toHexString()) == outputWithData
}
val outputWithDataSerializesIdentiaclly: (OutputWithData) -> Boolean = { outputWithData ->
    outputWithData.toHexString() == marshallOutputWithData(outputWithData).toRaw().toString("hex")
}
val outputWithDataDesrializesIdentically: (String) -> Boolean = { hexString ->
    val original = LeapCore.Output.fromRaw(Buffer.from(hexString, "hex"))
    val new = OutputWithData.fromHexString(hexString)
    new == unmarshallOutputWithData(original)
}