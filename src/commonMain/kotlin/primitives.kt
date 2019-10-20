package leapcore

typealias Bytes32 = ByteArray
typealias Bytes20 = ByteArray
//typealias Bytes1 = UByte
//typealias Bytes2 = UShort
//typealias Bytes4 = UInt

// try using extension to put it on ByteArray
fun byteArrayFomHexString(hexString: String): ByteArray {
    val arrayOfBytes = hexString.subSequence(2, hexString.length-1).chunked(2).map { it.toByte(16) }
    return ByteArray(arrayOfBytes.size) { arrayOfBytes[it] }
}

fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }

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

    fun getBigInt(): BigInt = TODO()
    fun getBytes20(): ByteArray = TODO()
    fun getUShort(): UShort = TODO()
}

data class Output(val value: BigInt, val address: Bytes20, val color: UShort) : Serializable {
    override fun toBinary(): ByteArray = value.toByteArray() + address + ByteArray(1) { color.toUInt().shr((1-it) * 4).toByte() }

    override fun toHexString(): String = toBinary().toHexString()

    companion object : Deserializable<Output> {
        override fun fromBinary(binaryData: ByteArray): Output {
            val reader = ByteArrayReader(binaryData)
            val value = reader.getBigInt()
            val address = reader.getBytes20()
            val color = reader.getUShort()
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





