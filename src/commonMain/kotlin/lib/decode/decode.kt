package leapcore.lib.decode

fun byteArrayFromHexString(hexString: String): ByteArray {
    val arrayOfBytes = hexString.chunked(2).map { it.toUByte(16).toByte() }
    return ByteArray(arrayOfBytes.size) { arrayOfBytes[it] }
}

data class ByteArrayBuffer(val byteArray: ByteArray, val offset: Int);
fun ByteArray.toBuffer() = ByteArrayBuffer(this, 0)

typealias Decoder<A> = State<A, ByteArrayBuffer>
fun <A> Decoder<A>.fromByteArray(byteArray: ByteArray): A = eval(this, byteArray.toBuffer())
fun <A> Decoder<A>.fromHexString(hexString: String): A = this.fromByteArray(byteArrayFromHexString(hexString))

fun <A> pure(a: A): Decoder<A> = retrn<A, ByteArrayBuffer>(a)

fun getSlice(size: Int): Decoder<ByteArray> = object : Decoder<ByteArray> {
    override val runState: (s: ByteArrayBuffer) -> Pair<ByteArray, ByteArrayBuffer> = { (array, offset) ->
        Pair(array.sliceArray(IntRange(offset, offset + size - 1)), ByteArrayBuffer(array, offset + size))
    }
}

fun <A> mul(decoder: Decoder<A>, times: Int): Decoder<List<A>> =
    when(times) {
        0 -> pure<List<A>>(emptyList<A>())
        else -> decoder                     bind {a ->
                mul(decoder, times-1) bind {list ->
                    pure(listOf(a) + list)
                } }
    }

val GetByte = object : Decoder<Byte> {
    override val runState: (s: ByteArrayBuffer) -> Pair<Byte, ByteArrayBuffer> = { (array, offset) ->
        Pair(array[offset], ByteArrayBuffer(array, offset + 1))
    }
}

val GetUByte = GetByte.map { it.toUByte() }

val GetUShort: Decoder<UShort> =
    GetUByte bind { byte1 ->
    GetUByte bind { byte2 ->
        val short = (byte1.toUInt() shl 8) or byte2.toUInt()
        pure(short.toUShort())
    } }

val GetUInt: Decoder<UInt> =
    GetUShort bind { short1 ->
    GetUShort bind { short2 ->
        val int = (short1.toUInt() shl 16) or short2.toUInt()
        pure(int)
    } }