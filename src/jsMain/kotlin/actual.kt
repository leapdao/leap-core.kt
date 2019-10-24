package leapcore

external class JSBigInt {
    fun toString(radix: Int): String
}

@JsModule("jsbi")
@JsNonModule
external class JSBI {

    companion object {
        fun BigInt(string: String): JSBigInt
    }
}

actual class BigInt(val bi: JSBigInt) {
    actual fun toByteArray(): ByteArray  {
        val ba = byteArrayFomHexString("0x" + bi.toString(16))
        val pad = ByteArray(32 - ba.size) { 0.toByte() }
        return pad + ba
    }

    actual companion object {
        actual fun fromBytesArray(binaryData: ByteArray): BigInt = BigInt(JSBI.BigInt(binaryData.toHexString()))
        actual fun fromString(string: String): BigInt = BigInt(JSBI.BigInt(string))
    }
}

actual fun hash(data: ByteArray): ByteArray = TODO()