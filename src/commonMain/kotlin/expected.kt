package leapcore

expect class BigInt {
    fun toByteArray(): ByteArray

    companion object {
        fun fromBytesArray(binaryData: ByteArray): BigInt
        fun fromString(data: String): BigInt
    }
}

expect fun hash(data: ByteArray): ByteArray