package leapcore.lib.crypto

data class Signature(val r: ByteArray, val s: ByteArray, val v: Byte)

expect fun hash(data: ByteArray): ByteArray
expect fun sign(data: ByteArray, privateKey: ByteArray): Signature