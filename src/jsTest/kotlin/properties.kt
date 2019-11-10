package leapcore

import leapcore.lib.decode.fromHexString
import leapcore.lib.encode.toHexString
import kotlin.test.assertTrue

fun <T> testProperty(gen: () -> T, property: (T) -> Boolean): Unit {
    for (x in 0..100) {
        assertTrue { property(gen()) }
    }
}

// Remove repetition
// move hexString -> JSType and JSType -> hexString to Original as extension functions

// Output
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

// OutputWithData
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

// Input
val inputRoundaboutIsIdentity: (Input) -> Boolean = { input ->
    Input.fromHexString(input.toHexString()) == input
}
val inputSerializesIdentically: (Input) -> Boolean = {input ->
    input.toHexString() == marshallInput(input).toRaw().toString("hex")
}
val inputDeserializesIdentically: (String) -> Boolean = { hexString ->
    val original = LeapCore.Outpoint.fromRaw(Buffer.from(hexString, "hex"))
    val new = Input.fromHexString(hexString)
    new == unmarshallInput(original)
}
val utxoIdIsIdentiacal: (Input) -> Boolean = {input ->
    "0x" + input.utxoId().toHexString() == marshallInput(input).getUtxoId()
}

// Signed Input
val signedInputRoundaboutIsIdentity: (SignedInput) -> Boolean = { signedInput ->
    SignedInput.fromHexString(signedInput.toHexString()) == signedInput
}
val signedInputSerializesIdentiaclly: (SignedInput) -> Boolean = { signedInput ->
    signedInput.toHexString() == marshallSignedInput(signedInput).toRaw().toString("hex")
}
val signedInputDeserializesIdentically: (Pair<String, Bytes32>) -> Boolean = { (hexString, msgHash) ->
    val original = LeapCore.Input.fromRaw(Buffer.from(hexString, "hex"), 0, msgHash.toBuffer())
    val new = SignedInput.fromHexString(hexString)
    new == unmarshallSignedInput(original)
}

// UnsignedTransfer
val unsignedTransferRoundaboutIsIdentity: (UnsignedTransfer) -> Boolean = { unsignedTransfer ->
    UnsignedTransfer.fromHexString(unsignedTransfer.toHexString()) == unsignedTransfer
}
val unsignedTransferSerializesIdentiaclly: (UnsignedTransfer) -> Boolean = {unsignedTransfer ->
    unsignedTransfer.toHexString() == marshallUnsignedTransfer(unsignedTransfer).sigDataBuf().toString("hex")
}
val unsignedTransferDeserializesIdentiaclly: (String) -> Boolean = {hexString ->
    val original = LeapCore.Tx.fromRaw(Buffer.from(hexString, "hex"))
    console.log(original)
    val new = UnsignedTransfer.fromHexString(hexString)
    new == unmarshallUnsignedTransfer(original)
}