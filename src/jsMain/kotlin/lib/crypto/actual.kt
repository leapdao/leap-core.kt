package leapcore.lib.crypto

import leapcore.lib.decode.byteArrayFromHexString
import leapcore.lib.encode.toHexString

external class Buffer {
    fun toString(encoding: String): String

    companion object {
        fun from(string: String, encoding: String): Buffer
    }
}
fun ByteArray.toBuffer(): Buffer = Buffer.from(this.toHexString(), "hex")

@JsModule("ethereumjs-util")
@JsNonModule
external class Util {

    companion object {
        fun ecsign(msgHash: Buffer, privateKey: Buffer): ECDSASignature
        fun hashPersonalMessage(message: Buffer): Buffer
    }
}

external class ECDSASignature {
    var r: Buffer
    var s: Buffer
    var v: Number
}

actual fun hash(data: ByteArray): ByteArray {
    return byteArrayFromHexString(Util.hashPersonalMessage(data.toBuffer()).toString("hex"))
}
actual fun sign(data: ByteArray, privateKey: ByteArray): Signature {
    val jsSig = Util.ecsign(data.toBuffer(), privateKey.toBuffer())
    val r = byteArrayFromHexString(jsSig.r.toString("hex"))
    val s = byteArrayFromHexString(jsSig.s.toString("hex"))
    val v = jsSig.v.toByte()
    return Signature(r, s, v)
}