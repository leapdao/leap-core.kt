package leapcore

expect class BigInt {
    fun toByteArray(): ByteArray

    companion object {
        fun fromBytesArray(binaryData: ByteArray): BigInt
        fun fromString(data: String): BigInt
    }
}

//expect class BytesArray {
//    // set offset to 0
//    fun clearOffset(): Unit
//
//    // getters read data and move offset
//    fun getUByte(): UByte
//    fun getUShort(): UShort
//    fun getUInt(): UInt
//    fun getBytes20(): Bytes20
//    fun getBytes32(): Bytes32
//
//    fun toHexString(): String
//
//    companion object {
//        fun fromNumber(data: UByte): BytesArray
//        fun fromNumber(data: UShort): BytesArray
//        fun fromNumber(data: UInt): BytesArray
//        fun fromNumber(data: BigInt): BytesArray
//        fun fromHexString(data: String): BytesArray
//        fun concat(arrays: List<BytesArray>): BytesArray
//    }
//}

expect fun hash(data: ByteArray): ByteArray