package leapcore

expect class BigInt {
    fun toByteArray(): ByteArray

    companion object {
        fun fromBytesArray(binaryData: ByteArray): BigInt
        fun fromString(string: String): BigInt
//        fun fromInt(num: Int): BigInt
    }
}

expect fun hash(data: ByteArray): ByteArray