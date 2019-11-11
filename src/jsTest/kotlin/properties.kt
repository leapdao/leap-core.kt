package leapcore

import leapcore.lib.decode.Decoder
import leapcore.lib.decode.GetUByte
import leapcore.lib.decode.bind
import leapcore.lib.decode.fromHexString
import leapcore.lib.encode.toHexString
import kotlin.test.assertTrue

fun <T> testProperty(gen: () -> T, property: (T) -> Boolean): Unit {
    for (x in 0..100) {
        assertTrue { property(gen()) }
    }
}
fun <T> testPropertyShort(gen: () -> T, property: (T) -> Boolean): Unit {
    for (x in 0..20) {
        assertTrue { property(gen()) }
    }
}

// Output
val outputRoundaboutIsIdentity: (Output) -> Boolean = { output ->
    Output.fromHexString(output.toHexString()) == output
}
val outputSerializesIdentically: (Output) -> Boolean = { output ->
    output.toHexString() == marshallOutput(output).toHexString()
}
val outputDeserializesIdentically: (String) -> Boolean = { hexString ->
    val original = LeapCore.Output.fromHexString(hexString)
    val new = Output.fromHexString(hexString)
    new == unmarshallOutput(original)
}

// OutputWithData
val outputWithDataRoundaboutIsIdentity: (OutputWithData) -> Boolean = { outputWithData ->
    OutputWithData.fromHexString(outputWithData.toHexString()) == outputWithData
}
val outputWithDataSerializesIdentiaclly: (OutputWithData) -> Boolean = { outputWithData ->
    outputWithData.toHexString() == marshallOutputWithData(outputWithData).toHexString()
}
val outputWithDataDesrializesIdentically: (String) -> Boolean = { hexString ->
    val original = LeapCore.Output.fromHexString(hexString)
    val new = OutputWithData.fromHexString(hexString)
    new == unmarshallOutputWithData(original)
}

// Input
val inputRoundaboutIsIdentity: (Input) -> Boolean = { input ->
    Input.fromHexString(input.toHexString()) == input
}
val inputSerializesIdentically: (Input) -> Boolean = {input ->
    input.toHexString() == marshallInput(input).toHexString()
}
val inputDeserializesIdentically: (String) -> Boolean = { hexString ->
    val original = LeapCore.Outpoint.fromHexString(hexString)
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
    signedInput.toHexString() == marshallSignedInput(signedInput).toHexString()
}
val signedInputDeserializesIdentically: (Pair<String, Bytes32>) -> Boolean = { (hexString, msgHash) ->
    val original = LeapCore.Input.fromHexString(hexString, msgHash)
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
    val original = LeapCore.Tx.fromHexString(hexString)
    val new = UnsignedTransfer.fromHexString(hexString)
    new == unmarshallUnsignedTransfer(original)
}

// SpendingConditionInput
val spendingConditionInputRoundaboutIsIdentity: (SpendingConditionInput) -> Boolean = {spendingConditionInput ->
    SpendingConditionInput.fromHexString(spendingConditionInput.toHexString()) == spendingConditionInput
}
val spendingConditionInputSerializesIdentically: (SpendingConditionInput) -> Boolean = {spendingConditionInput ->
    spendingConditionInput.toHexString() == marshallSpendingConditionInput(spendingConditionInput).toHexString()
}
val spendingConditionInputDeserialzesIdentically: (String) -> Boolean = {hexString ->
    val original = LeapCore.Input.fromHexString(hexString)
    val new = SpendingConditionInput.fromHexString(hexString)
    new == unmarshallSpendingConditionInput(original)
}

fun <A> eatType(dec: Decoder<A>): Decoder<A> = GetUByte bind { dec }

// Transfer
val transferRoundaboutIsIdentity: (Transfer) -> Boolean = {transfer ->
    eatType(Transfer).fromHexString(transfer.toHexString()) == transfer
}
val transferSerializesIdentically: (Transfer) -> Boolean = {transfer ->
    transfer.toHexString() == marshallTransfer(transfer).toHexString()
}
val transferDeserializesIdentiacally: (String) -> Boolean = {hexString ->
    val original = LeapCore.Tx.fromHexString(hexString)
    val new = eatType(Transfer).fromHexString(hexString)
    new == unmarshallTransfer(original)
}

// SpendingCondition
val spendingConditionRoundaboutIsIdentity: (SpendingCondition) -> Boolean = {spendingCondition ->
    eatType(SpendingCondition).fromHexString(spendingCondition.toHexString()) == spendingCondition
}
val spendingConditionSerializesIdentically: (SpendingCondition) -> Boolean = {spendingCondition ->
    spendingCondition.toHexString() == marshallSpendingCondition(spendingCondition).toHexString()
}
val spendingConditionDeserializesIdentically: (String) -> Boolean = {hexString ->
    val original = LeapCore.Tx.fromHexString(hexString)
    val new = eatType(SpendingCondition).fromHexString(hexString)
    new == unmarshallSpendingConidtion(original)
}

