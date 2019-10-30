package leapcore

import leapcore.lib.bigint.BigInt
import leapcore.lib.decode.*
import leapcore.lib.encode.*

typealias Bytes20 = ByteArray
typealias Bytes32 = ByteArray

val GetBytes20 = getSlice(20)
val GetBytes32 = getSlice(32)
val GetBigInt = GetBytes32.map { bytes32 -> BigInt.fromByteArray(bytes32) }

// TODO find nice way do deal with equality

data class Output(val value: BigInt, val address: Bytes20, val color: UShort) : Serializable {
    override fun toByteArray(): ByteArray = value.toByteArray() + color.toByteArray() + address

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Output -> other.toHexString() == this.toHexString()
            else -> super.equals(other)
        }
    }

    companion object : Decoder<Output> by
            GetBigInt  bind {value ->
            GetUShort  bind {color ->
            GetBytes20 bind {address ->
                retun<Output>(Output(value, address, color))
            } } }
}

data class OutputWithData(val output: Output, val data: Bytes32) : Serializable {
    override fun toByteArray(): ByteArray = output.toByteArray() + data

    companion object : Decoder<OutputWithData> by
            Output     bind {output ->
            GetBytes32 bind {data ->
                retun<OutputWithData>(OutputWithData(output, data))
            }}
}

data class Input(val txHash: Bytes32, val id: UByte) : Serializable {

    fun utxoId(): Bytes32 = TODO() //16 zeros, 4 bit id, 12 bit end of txHash

    override fun toByteArray(): ByteArray = txHash + id.toByteArray()

    companion object : Decoder<Input> by
            GetBytes32 bind {txHash ->
            GetUByte   bind {id ->
                retun<Input>(Input(txHash, id))
            } }
}

data class Signature(val r: ByteArray, val s: ByteArray, val v: UByte) : Serializable {
    override fun toByteArray(): ByteArray = r + s + v.toByteArray()

    companion object : Decoder<Signature> by
            TODO()
}





