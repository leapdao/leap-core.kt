package leapcore.state

//interface State<A, S> {
//    fun runState(s: S): Pair<A, S>
//}
//
//infix fun <A, S, B> State<A, S>.bind(f: (A) -> State<B, S>): State<B, S> = object : State<B,S> {
//    override fun runState(s: S): Pair<B, S> {
//        val (a, ns) = this@bind.runState(s)
//        return f(a).runState(ns);
//    }
//}
//
//fun <A, S> make(a: A): State<A, S> = object : State<A, S> {
//    override fun runState(s: S): Pair<A, S> = Pair(a, s)
//}
//
//data class ByteArrayReader(val byteArray: ByteArray, val offset: Int);
//
//class GetUByte: State<UByte, ByteArrayReader> {
//    override fun runState(s: ByteArrayReader): Pair<UByte, ByteArrayReader> {
//        return Pair(s.byteArray[s.offset].toUByte(), ByteArrayReader(s.byteArray, s.offset+1))
//    }
//}

interface State<A, S> {
    val runState: (s: S) -> Pair<A, S>
}

infix fun <A, S, B> State<A, S>.bind(f: (A) -> State<B, S>): State<B, S> = object : State<B,S> {
    override val runState: (s: S) -> Pair<B, S> = {s ->
        val (a, ns) = this@bind.runState(s)
        f(a).runState(ns)
    }
}

fun <A, S> make(a: A): State<A, S> = object : State<A, S> {
    override val runState: (s: S) -> Pair<A, S> = {s -> Pair(a, s) }
}

data class ByteArrayReader(val byteArray: ByteArray, val offset: Int);

class GetUByteClass: State<UByte, ByteArrayReader> {
    override val runState: (s: ByteArrayReader) -> Pair<UByte, ByteArrayReader> = {(array, offset) ->
        Pair(array[offset].toUByte(), ByteArrayReader(array, offset + 1))
    }
}

fun sliceArray(size: Int): State<ByteArray, ByteArrayReader> {
    return object : State<ByteArray, ByteArrayReader> {
        override val runState: (s: ByteArrayReader) -> Pair<ByteArray, ByteArrayReader> = {(array, offset) ->
            Pair(array.sliceArray(IntRange(offset, offset + size - 1)), ByteArrayReader(array, offset + size))
        }
    }
}

val GetUByte = GetUByteClass()

val GetUShort: State<UShort, ByteArrayReader> = GetUByte bind { byte2 ->
    GetUByte bind { byte1 ->
        val short = (byte2.toUInt() shl 8) or byte1.toUInt()
        make(short.toUShort())
    }
}

val GetUInt: State<UInt, ByteArrayReader> = GetUShort bind { short2 ->
    GetUShort bind { short1 ->
        val int = (short2.toUInt() shl 16) or short1.toUInt()
        make(int)
    }
}

val GetBytes20 = sliceArray(20)
val GetBytes32 = sliceArray(32)


