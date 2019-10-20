package leapcore

import java.math.BigInteger

actual class BigInt(val bi: BigInteger) {
    actual fun toByteArray(): ByteArray = TODO()

    actual companion object {
        actual fun fromBytesArray(binaryData: ByteArray): BigInt = TODO()
        actual fun fromString(data: String): BigInt = BigInt(BigInteger(data))

    }
}

//actual class BytesArray(val ba: ByteArray) {
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
//        actual fun fromNumber(data: UByte): BytesArray = TODO()
//        actual fun fromNumber(data: UShort): BytesArray = TODO()
//        actual fun fromNumber(data: UInt): BytesArray = TODO()
//        actual fun fromNumber(data: BigInt): BytesArray = TODO()
//        actual fun fromHexString(data: String): BytesArray = TODO()
//        actual fun concat(arrays: List<BytesArray>): BytesArray = TODO()
//    }
//}

actual fun hash(data: ByteArray): ByteArray = TODO()