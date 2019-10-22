package leapcore

import java.math.BigInteger

actual class BigInt(val bi: BigInteger) {
    actual fun toByteArray(): ByteArray {
        val ba = bi.toByteArray()
        val pad = ByteArray(32 - ba.size) { 0.toByte() }
        return pad + ba
    }

    actual companion object {
        actual fun fromBytesArray(binaryData: ByteArray): BigInt = BigInt(BigInteger(binaryData))
        actual fun fromString(data: String): BigInt = BigInt(BigInteger(data))
    }
}

actual fun hash(data: ByteArray): ByteArray = TODO()