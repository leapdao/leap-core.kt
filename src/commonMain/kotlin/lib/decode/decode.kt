package leapcore.lib.decode

fun byteArrayFromHexString(hexString: String): ByteArray {
//    val arrayOfBytes = hexString.subSequence(2, hexString.length).chunked(2).map { it.toUByte(16).toByte() }
    val arrayOfBytes = hexString.chunked(2).map { it.toUByte(16).toByte() }
    return ByteArray(arrayOfBytes.size) { arrayOfBytes[it] }
}

data class ByteArrayBuffer(val byteArray: ByteArray, val offset: Int);
fun ByteArray.toBuffer() = ByteArrayBuffer(this, 0)

typealias Decoder<A> = State<A, ByteArrayBuffer>
fun <A> Decoder<A>.fromByteArray(byteArray: ByteArray): A = eval(this, byteArray.toBuffer())
fun <A> Decoder<A>.fromHexString(hexString: String): A = this.fromByteArray(byteArrayFromHexString(hexString))

fun <A> retun(a: A): Decoder<A> = retrn<A, ByteArrayBuffer>(a)

fun getSlice(size: Int): Decoder<ByteArray> = object : Decoder<ByteArray> {
    override val runState: (s: ByteArrayBuffer) -> Pair<ByteArray, ByteArrayBuffer> = { (array, offset) ->
        Pair(array.sliceArray(IntRange(offset, offset + size - 1)), ByteArrayBuffer(array, offset + size))
    }
}

val GetUByte = object : Decoder<UByte> {
    override val runState: (s: ByteArrayBuffer) -> Pair<UByte, ByteArrayBuffer> = { (array, offset) ->
        Pair(array[offset].toUByte(), ByteArrayBuffer(array, offset + 1))
    }
}

val GetUShort: Decoder<UShort> =
    GetUByte bind { byte1 ->
    GetUByte bind { byte2 ->
        val short = (byte2.toUInt() shl 8) or byte1.toUInt()
        retun<UShort>(short.toUShort())
    } }

val GetUInt: Decoder<UInt> =
    GetUShort bind { short1 ->
    GetUShort bind { short2 ->
        val int = (short2.toUInt() shl 16) or short1.toUInt()
        retun<UInt>(int)
    } }