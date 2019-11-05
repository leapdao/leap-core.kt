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