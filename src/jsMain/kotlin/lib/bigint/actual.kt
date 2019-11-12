package leapcore.lib.bigint

import leapcore.lib.encode.toHexString
import leapcore.lib.decode.byteArrayFromHexString

external class JSBigInt {
    fun toString(radix: Int): String
}

@JsModule("jsbi")
@JsNonModule
external class JSBI {
    companion object {
        fun BigInt(string: String): JSBigInt
        fun BigInt(int: Int): JSBigInt
    }
}

actual class BigInt(val bi: JSBigInt) {
    actual fun toByteArray(): ByteArray  {
        val ba = byteArrayFromHexString(bi.toString(16))
        val pad = ByteArray(32 - ba.size) { 0.toByte() }
        return pad + ba
    }

    actual companion object {
        actual fun fromByteArray(binaryData: ByteArray): BigInt = BigInt(JSBI.BigInt("0x${binaryData.toHexString()}"))
        actual fun fromString(string: String): BigInt = BigInt(JSBI.BigInt("0x$string"))
        actual fun fromInt(int: Int): BigInt = BigInt(JSBI.BigInt(int))
    }
}