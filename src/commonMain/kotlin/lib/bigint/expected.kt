package leapcore.lib.bigint

expect class BigInt {
    fun toByteArray(): ByteArray

    companion object {
        fun fromByteArray(binaryData: ByteArray): BigInt
        fun fromString(string: String): BigInt
        fun fromInt(int: Int): BigInt
    }
}