package leapcore

typealias Bytes32 = ByteArray
typealias Bytes20 = ByteArray
//typealias Bytes1 = UByte
//typealias Bytes2 = UShort
//typealias Bytes4 = UInt

// try using extension to put it on ByteArray
fun byteArrayFomHexString(hexString: String): ByteArray {
    val arrayOfBytes = hexString.subSequence(2, hexString.length).chunked(2).map { it.toUByte(16).toByte() }
    return ByteArray(arrayOfBytes.size) { arrayOfBytes[it] }
}

fun ByteArray.toHexString() = "0x" + asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }

interface Serializable {
    fun toBinary(): ByteArray
    fun toHexString(): String
}

interface Deserializable<T> {
    fun fromBinary(binaryData: ByteArray): T
    fun fromHexString(string: String): T
}

class ByteArrayReader(val byteArray: ByteArray) {
    var offset = 0

    fun getBigInt(): BigInt  {
        val bi = BigInt.fromBytesArray(byteArray.sliceArray(IntRange(offset, offset + 31)))
        offset += 32
        return bi
    }
    fun getBytes20(): ByteArray {
        val b20 = byteArray.sliceArray(IntRange(offset, offset+19))
        offset += 20
        return b20;
    }
    fun getUShort(): UShort {
        val int = (byteArray[offset].toUInt() shl 4) or byteArray[offset+1].toUInt()
        offset += 2
        return int.toUShort()
    }
}

data class Output(val value: BigInt, val address: Bytes20, val color: UShort) : Serializable {
    override fun toBinary(): ByteArray = value.toByteArray() + ByteArray(2) { color.toUInt().shr((1-it) * 4).toByte() } + address

    override fun toHexString(): String = toBinary().toHexString()

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Output -> other.toHexString() == this.toHexString()
            else -> super.equals(other)
        }
    }

    companion object : Deserializable<Output> {
        override fun fromBinary(binaryData: ByteArray): Output {
            val reader = ByteArrayReader(binaryData)
            val value = reader.getBigInt()
            val color = reader.getUShort()
            val address = reader.getBytes20()
            return Output(value, address, color)
        }

        override fun fromHexString(string: String): Output = fromBinary(byteArrayFomHexString(string))
    }
}

data class OutputWithData(val output: Output, val data: Bytes32) : Serializable {
    override fun toBinary(): ByteArray = TODO()

    override fun toHexString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object : Deserializable<OutputWithData> {
        override fun fromBinary(binaryData: ByteArray): OutputWithData = TODO()

        override fun fromHexString(string: String): OutputWithData {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}

data class Input(val txHash: Bytes32, val id: UByte) : Serializable {

    fun utxoId(): Bytes32 = TODO() //16 zeros, 4 bit id, 12 bit end of txHash

    override fun toBinary(): ByteArray = TODO()

    override fun toHexString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object : Deserializable<Input> {
        override fun fromBinary(binaryData: ByteArray): Input = TODO()

        override fun fromHexString(string: String): Input {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}

data class Signature(val r: ByteArray, val s: ByteArray, val v: Byte) : Serializable {
    override fun toBinary(): ByteArray = TODO()

    override fun toHexString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object : Deserializable<Signature> {
        override fun fromBinary(binaryData: ByteArray): Signature = TODO()

        override fun fromHexString(string: String): Signature {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}





