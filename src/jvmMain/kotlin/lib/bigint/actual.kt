package leapcore.lib.bigint

import java.math.BigInteger

actual class BigInt(val bi: BigInteger) {
    actual fun toByteArray(): ByteArray {
        val ba = bi.toByteArray()
        val pad = ByteArray(32 - ba.size) { 0.toByte() }
        return pad + ba
    }

    actual companion object {
        actual fun fromByteArray(binaryData: ByteArray): BigInt = BigInt(BigInteger(binaryData))
        actual fun fromString(string: String): BigInt = BigInt(BigInteger(string))
        actual fun fromInt(int: Int): BigInt = BigInt(BigInteger(int.toString()))
    }
}