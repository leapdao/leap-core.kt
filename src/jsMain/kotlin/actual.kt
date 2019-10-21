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
        actual fun fromString(data: String): BigInt = BigInt(JSBI.BigInt(data))
    }
}

//external class Buffer {
//
//    fun writeUInt8(value: Int): Unit
//
//    companion object {
//        fun alloc(size: Int): Buffer
//    }
//}

//actual class BytesArray(val buffer: Buffer) {
//
//    var offset = 0
//    // set offset to 0
//    actual fun clearOffset(): Unit = TODO()
//
//    // getters read data and move offset
//    actual fun getUByte(): UByte = TODO()
//    actual fun getUShort(): UShort = TODO()
//    actual fun getUInt(): UInt = TODO()
//    actual fun getBytes20(): Bytes20 = TODO()
//    actual fun getBytes32(): Bytes32 = TODO()
//
//    actual fun toHexString(): String = TODO()
//
//    actual companion object {
//        actual fun fromNumber(data: UByte): BytesArray {
//            val buffer: Buffer = Buffer.alloc(1)
//            buffer.writeUInt8(data.toInt())
//            return BytesArray(buffer)
//        }
//        actual fun fromNumber(data: UShort): BytesArray = TODO()
//        actual fun fromNumber(data: UInt): BytesArray = TODO()
//        actual fun fromNumber(data: BigInt): BytesArray = TODO()
//        actual fun fromHexString(data: String): BytesArray = TODO()
//        actual fun concat(arrays: List<BytesArray>): BytesArray = TODO()
//    }
//}

actual fun hash(data: ByteArray): ByteArray = TODO()