package leapcore

import leapcore.lib.bigint.BigInt
import leapcore.lib.decode.byteArrayFromHexString
import leapcore.lib.encode.toHexString

fun ByteArray.toBuffer(): Buffer = Buffer.from(this.toHexString(), "hex")

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

fun marshallUnsignedTransfer(unsignedTransfer: UnsignedTransfer): LeapCore.Tx {
    val (inputs, outputs) = unsignedTransfer
    return LeapCore.Tx.transfer(inputs.map { LeapCore.Input(marshallInput(it)) } .toTypedArray(), outputs.map { marshallOutput(it) } .toTypedArray())
}
fun unmarshallUnsignedTransfer(transaction: LeapCore.Tx): UnsignedTransfer {
    val inputs = transaction.inputs.map { unmarshallSignedInput(it).input }
    val outputs = transaction.outputs.map { unmarshallOutput(it) }
    return UnsignedTransfer(inputs, outputs)
}

