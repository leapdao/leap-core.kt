package leapcore

import leapcore.lib.bigint.BigInt
import leapcore.lib.decode.*
import leapcore.lib.encode.*

typealias Bytes20 = ByteArray
typealias Bytes32 = ByteArray

val GetBytes20 = getSlice(20)
val GetBytes32 = getSlice(32)
val GetBigInt = GetBytes32.map { bytes32 -> BigInt.fromByteArray(bytes32) }

fun lengthsHash(inLength: UByte, outLength: UByte): UByte {
    return (16u.toUByte() * inLength + outLength).toUByte()
}

fun parseLengths(lengthsHash: UByte): Pair<Int, Int> {
    val inputsLength = lengthsHash.toUInt().shr(4).toInt()
    val outputsLength = ( lengthsHash and 0x0fu ).toInt()
    return Pair(inputsLength, outputsLength)
}

// TODO find nice way do deal with equality
// TODO input validation
// TODO output colors - ERC, NFT, NST - seperate types?

data class Output(val value: BigInt, val address: Bytes20, val color: UShort) : Serializable {
    override fun toByteArray(): ByteArray = value.toByteArray() + color.toByteArray() + address

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Output -> other.toHexString() == this.toHexString()
            else  -> super.equals(other)
        }
    }

    companion object : Decoder<Output> by
            GetBigInt  bind {value ->
            GetUShort  bind {color ->
            GetBytes20 bind {address ->
                pure(Output(value, address, color))
            } } }
}

data class OutputWithData(val output: Output, val data: Bytes32) : Serializable {
    override fun toByteArray(): ByteArray = output.toByteArray() + data

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is OutputWithData -> other.toHexString() == this.toHexString()
            else -> super.equals(other)
        }
    }

    companion object : Decoder<OutputWithData> by
            Output     bind {output ->
            GetBytes32 bind {data ->
                pure(OutputWithData(output, data))
            }}
}

data class Input(val txHash: Bytes32, val id: UByte) : Serializable {

    fun utxoId(): Bytes32 {
        val padding = ByteArray(16){0.toByte()}
        val end = txHash.slice(IntRange(17, 31))
        return padding + id.toByte() + end
    }

    override fun toByteArray(): ByteArray = txHash + id.toByteArray()

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Input -> other.toHexString() == this.toHexString()
            else -> super.equals(other)
        }
    }

    companion object : Decoder<Input> by
            GetBytes32 bind {txHash ->
            GetUByte   bind {id ->
                pure(Input(txHash, id))
            } }
}

data class Signature(val r: Bytes32, val s: Bytes32, val v: Byte) : Serializable {
    override fun toByteArray(): ByteArray = r + s + v.toByteArray()

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Signature -> other.toHexString() == this.toHexString()
            else -> super.equals(other)
        }
    }

    companion object : Decoder<Signature> by
            GetBytes32 bind {r ->
            GetBytes32 bind {s ->
            GetByte bind  {v ->
                pure(Signature(r, s, v))
            } } }
    {
        val EMPTY: Signature = Signature(ByteArray(32), ByteArray(32), 0)
    }
}

data class SignedInput(val input: Input, val signature: Signature): Serializable {
    override fun toByteArray(): ByteArray = input.toByteArray() + signature.toByteArray()

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is SignedInput -> other.toHexString() == this.toHexString()
            else -> super.equals(other)
        }
    }

    companion object : Decoder<SignedInput> by
            Input bind {input ->
            Signature bind  {sig ->
                pure(SignedInput(input, sig))
            } }
}

data class UnsignedTransfer(val inputs: List<Input>, val outputs: List<Output>) : Serializable {
    override fun toByteArray(): ByteArray =
                3.toUByte().toByteArray() +
                lengthsHash(inputs.size.toUByte(), outputs.size.toUByte()).toByteArray() +
                inputs.map { SignedInput(it, Signature.EMPTY) }.toByteArray() +
                outputs.toByteArray()

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is UnsignedTransfer -> other.toHexString() == this.toHexString()
            else -> super.equals(other)
        }
    }

    companion object : Decoder<UnsignedTransfer> by
            GetUByte                     bind {
            GetUByte.map(::parseLengths) bind { pair ->
            // kotlinc-js bug??
            val ins = pair.first
            val outs = pair.second
            mul(SignedInput, ins)        bind { signedInputs ->
            mul(Output, outs)            bind { outputs ->
                val inputs = signedInputs.map { it.input }
                pure(UnsignedTransfer(inputs, outputs))
            } } } }
}






