package leapcore

import leapcore.lib.bigint.BigInt
import leapcore.lib.decode.byteArrayFromHexString
import leapcore.lib.encode.toHexString

fun marshallOutput(output: Output): LeapCore.Output {
    val (value, address, color) = output
    return LeapCore.Output("0x" + value.toByteArray().toHexString(), address.toHexString(), color.toInt())
}
fun unmarshallOutput(output: LeapCore.Output): Output {
    val value = BigInt(output.value)
    val address = byteArrayFromHexString(output.address.removePrefix("0x"))
    val color = output.color.toInt().toUShort()
    return Output(value, address, color)
}

fun marshallOutputWithData(outputWithData: OutputWithData): LeapCore.Output {
    val (output, data) = outputWithData
    val (value, address, color) = output
    return LeapCore.Output(
        "0x" + value.toByteArray().toHexString(),
        address.toHexString(),
        color.toInt(),
        data.toHexString()
    )
}
fun unmarshallOutputWithData(output: LeapCore.Output): OutputWithData {
    val out = unmarshallOutput(output)
    val data = byteArrayFromHexString(output.data.removePrefix("0x"))
    return OutputWithData(out, data)
}

fun marshallInput(input: Input): LeapCore.Outpoint {
    val (hash, id) = input
    return LeapCore.Outpoint(hash.toHexString(), id.toInt())
}
fun unmarshallInput(outpoint: LeapCore.Outpoint): Input {
    val txHash = byteArrayFromHexString(outpoint.hash.toString("hex"))
    val id = outpoint.index.toInt().toUByte()
    return Input(txHash, id)
}

fun marshallSignedInput(signedInput: SignedInput): LeapCore.Input {
    val (input, sig) = signedInput
    val (r, s, v) = sig
    val outpoint = marshallInput(input)
    val leapCoreInput = LeapCore.Input(outpoint)
    leapCoreInput.setSig(r.toBuffer(), s.toBuffer(), v.toInt(), exampleAddress)
    return leapCoreInput
}
fun unmarshallSignedInput(input: LeapCore.Input): SignedInput {
    val outpoint = input.prevout
    val r = byteArrayFromHexString(input.r.toString("hex"))
    val s = byteArrayFromHexString(input.s.toString("hex"))
    val v = input.v.toByte()
    return SignedInput(unmarshallInput(outpoint), Signature(r, s, v))
}

fun marshallSpendingConditionInput(spendingConditionInput: SpendingConditionInput): LeapCore.Input {
    val (input, msgData, script) = spendingConditionInput
    val spendingConditionInputOptions: LeapCore.SpendingConditionInputOptions = js("{}") as LeapCore.SpendingConditionInputOptions
    spendingConditionInputOptions.apply {
        this.script = script.toBuffer()
        this.msgData = msgData.toBuffer()
        this.prevout = marshallInput(input)
    }
    return LeapCore.Input(spendingConditionInputOptions)
}
fun unmarshallSpendingConditionInput(spendInput: LeapCore.Input): SpendingConditionInput {
    val input = unmarshallInput(spendInput.prevout)
    val msgData = spendInput.msgData.toByteArray()
    val script = spendInput.script.toByteArray()
    return SpendingConditionInput(input, msgData, script)
}

fun marshallUnsignedTransfer(unsignedTransfer: UnsignedTransfer): LeapCore.Tx {
    val (inputs, outputs) = unsignedTransfer
    return LeapCore.Tx.transfer(inputs.map { LeapCore.Input(marshallInput(it)) } .toTypedArray(), outputs.map { marshallOutput(it) } .toTypedArray())
}
fun unmarshallUnsignedTransfer(transaction: LeapCore.Tx): UnsignedTransfer {
    val inputs = transaction.inputs.map { unmarshallSignedInput(it).input }
    val outputs = transaction.outputs.map { unmarshallOutput(it) }
    return UnsignedTransfer(inputs, outputs)
}

fun marshallTransfer(transfer: Transfer): LeapCore.Tx {
    val (inputs, outputs) = transfer
    return LeapCore.Tx.transfer(inputs.map { marshallSignedInput(it) } .toTypedArray(), outputs.map { marshallOutput(it) } .toTypedArray())
}
fun unmarshallTransfer(tx: LeapCore.Tx): Transfer {
    val inputs = tx.inputs.map { unmarshallSignedInput(it) }
    val outputs = tx.outputs.map { unmarshallOutput(it)}
    return Transfer(inputs, outputs)
}

fun marshallSpendingCondition(spendingCondition: SpendingCondition): LeapCore.Tx {
    val (specialInput, inputs, outputs) = spendingCondition
    val ins = (listOf(marshallSpendingConditionInput(specialInput)) + inputs.map { marshallSignedInput(it) }).toTypedArray()
    val outs = outputs.map { marshallOutput(it) }.toTypedArray()
    return LeapCore.Tx.spendCond(ins, outs)
}
fun unmarshallSpendingConidtion(tx: LeapCore.Tx): SpendingCondition {
    val specialInput = unmarshallSpendingConditionInput(tx.inputs[0])
    val inputs = tx.inputs.slice(IntRange(1, tx.inputs.size-1)).map { unmarshallSignedInput(it) }
    val outputs = tx.outputs.map { unmarshallOutput(it)}
    return SpendingCondition(specialInput, inputs, outputs)
}

