package leapcore

import leapcore.lib.bigint.BigInt
import leapcore.lib.decode.byteArrayFromHexString

fun Output(value: Int, address: String, color: Int): Output = Output(
    BigInt.fromInt(value),
    byteArrayFromHexString(address.replace("0x", "")),
    color.toUShort())