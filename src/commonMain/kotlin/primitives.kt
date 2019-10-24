package leapcore

interface Serializable {
    fun toByteArray(): ByteArray
    fun toHexString(): String = toByteArray().toHexString()
}

interface Deserializable<T> {
    val decoder: State<T, ByteArrayBuffer>
    fun fromByteArray(binaryData: ByteArray): T = eval(decoder, binaryData.toBuffer())
    fun fromHexString(string: String): T = fromByteArray(byteArrayFomHexString(string))
}

data class Output(val value: BigInt, val address: Bytes20, val color: UShort) : Serializable {
    override fun toByteArray(): ByteArray = value.toByteArray() + ByteArray(2) { color.toUInt().shr((1-it) * 4).toByte() } + address

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Output -> other.toHexString() == this.toHexString()
            else -> super.equals(other)
        }
    }

    companion object : Deserializable<Output> {
        override val decoder: State<Output, ByteArrayBuffer> =
            GetBigInt bind {value ->
            GetUShort bind {color ->
            GetBytes20 bind {address ->
                make<Output, ByteArrayBuffer>(Output(value, address, color))
            } } }
    }
}

data class OutputWithData(val output: Output, val data: Bytes32) : Serializable {
    override fun toByteArray(): ByteArray = TODO()

    companion object : Deserializable<OutputWithData> {
        override val decoder: State<OutputWithData, ByteArrayBuffer> = TODO()
    }
}

data class Input(val txHash: Bytes32, val id: UByte) : Serializable {

    fun utxoId(): Bytes32 = TODO() //16 zeros, 4 bit id, 12 bit end of txHash

    override fun toByteArray(): ByteArray = TODO()

    companion object : Deserializable<Input> {
        override val decoder: State<Input, ByteArrayBuffer> = TODO()
    }
}

data class Signature(val r: ByteArray, val s: ByteArray, val v: Byte) : Serializable {
    override fun toByteArray(): ByteArray = TODO()

    companion object : Deserializable<Signature> {
        override val decoder: State<Signature, ByteArrayBuffer> = TODO()
    }
}





