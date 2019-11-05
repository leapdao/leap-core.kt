package leapcore.lib.crypto

interface Signature {
    val r: ByteArray
    val s: ByteArray
    val v: UByte
}

expect fun hash(data: ByteArray): ByteArray
expect fun sign(data: ByteArray): Signature