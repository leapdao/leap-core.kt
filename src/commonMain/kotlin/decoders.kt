package leapcore

// Generic state type
interface State<A, S> {
    val runState: (s: S) -> Pair<A, S>
}

// Functor instance
fun <A, S, B> State<A, S>.map(f: (A) -> B): State<B, S> = object : State<B, S> {
    override val runState: (s: S) -> Pair<B, S> =  {s ->
        val (a, ns) = this@map.runState(s)
        Pair(f(a), ns)
    }
}

// Monad instance
infix fun <A, S, B> State<A, S>.bind(f: (A) -> State<B, S>): State<B, S> = object : State<B,S> {
    override val runState: (s: S) -> Pair<B, S> = {s ->
        val (a, ns) = this@bind.runState(s)
        f(a).runState(ns)
    }
}

fun <A, S> make(a: A): State<A, S> = object : State<A, S> {
    override val runState: (s: S) -> Pair<A, S> = {s -> Pair(a, s) }
}

fun <A, S> eval(st: State<A, S>, s: S): A = st.runState(s).first

////////////////////////////////


///////////////////////////////


// ByteArray decoder
data class ByteArrayBuffer(val byteArray: ByteArray, val offset: Int);

fun ByteArray.toBuffer() = ByteArrayBuffer(this, 0)

fun sliceArray(size: Int): State<ByteArray, ByteArrayBuffer> {
    return object : State<ByteArray, ByteArrayBuffer> {
        override val runState: (s: ByteArrayBuffer) -> Pair<ByteArray, ByteArrayBuffer> = { (array, offset) ->
            Pair(array.sliceArray(IntRange(offset, offset + size - 1)), ByteArrayBuffer(array, offset + size))
        }
    }
}

val GetUByte = object : State<UByte, ByteArrayBuffer> {
    override val runState: (s: ByteArrayBuffer) -> Pair<UByte, ByteArrayBuffer> = { (array, offset) ->
        Pair(array[offset].toUByte(), ByteArrayBuffer(array, offset + 1))
    }
}

val GetUShort: State<UShort, ByteArrayBuffer> =
    GetUByte bind { byte1 ->
    GetUByte bind { byte2 ->
        val short = (byte2.toUInt() shl 8) or byte1.toUInt()
        make<UShort, ByteArrayBuffer>(short.toUShort())
    }
}

val GetUInt: State<UInt, ByteArrayBuffer> =
    GetUShort bind { short1 ->
    GetUShort bind { short2 ->
        val int = (short2.toUInt() shl 16) or short1.toUInt()
        make<UInt, ByteArrayBuffer>(int)
    }
}

val GetBytes20 = sliceArray(20)
val GetBytes32 = sliceArray(32)
val GetBigInt = GetBytes32.map { bytes32 -> BigInt.fromBytesArray(bytes32) }


