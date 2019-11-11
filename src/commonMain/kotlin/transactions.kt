package leapcore

import leapcore.lib.encode.*
import leapcore.lib.decode.*

sealed class Transaction : Serializable {}

data class Transfer(val inputs: List<SignedInput>, val outputs: List<Output>) : Transaction() {
    override fun toByteArray(): ByteArray =
                type.toByteArray() +  lengthsHash(inputs.size.toUByte(), outputs.size.toUByte()).toByteArray() +
                inputs.toByteArray() + outputs.toByteArray()

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is Transfer -> other.toHexString() == this.toHexString()
            else -> super.equals(other)
        }
    }

    companion object : Decoder<Transfer> by
            GetUByte.map(::parseLengths) bind { pair ->
            val ins = pair.first
            val outs = pair.second
            mul(SignedInput, ins)        bind { inputs ->
            mul(Output, outs)            bind { outputs ->
                pure(Transfer(inputs, outputs))
            } } }
    {
        val type = 3.toUByte()
    }
}

data class SpendingCondition(val specialInput: SpendingConditionInput, val inputs: List<SignedInput>, val outputs: List<Output>): Transaction() {
    override fun toByteArray(): ByteArray =
        Companion.type.toByteArray() + lengthsHash((inputs.size + 1).toUByte(), outputs.size.toUByte()).toByteArray() +
        specialInput.toByteArray() + inputs.toByteArray() + outputs.toByteArray()

    override fun equals(other: Any?): Boolean {
        return when (other) {
            is SpendingCondition -> other.toHexString() == this.toHexString()
            else -> super.equals(other)
        }
    }

    companion object : Decoder<SpendingCondition> by
            GetUByte.map(::parseLengths) bind { pair ->
            val ins = pair.first - 1
            val outs = pair.second
            SpendingConditionInput       bind { specialInput ->
            mul(SignedInput, ins)        bind { inputs ->
            mul(Output, outs)            bind { outputs ->
                pure(SpendingCondition(specialInput, inputs, outputs))
            } } } }
    {
        val type: UByte = 13.toUByte()
    }
}

data class Deposit(val depositId: UInt, val output: Output) : Transaction() {
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<Deposit> by
            TODO()
    {
        val type = 2.toUByte()
    }
}

data class EpochLength(val epochLength: UInt) : Transaction() {
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<EpochLength> by
            TODO()
    {
        val type: UByte = 12.toUByte()
    }
}

data class MinGasPrice(val minGasPrice: ByteArray): Transaction() {
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<MinGasPrice> by
            TODO()
    {
        val type: UByte = 14.toUByte()
    }
}

data class ValidatorJoin(val slotId: UShort, val tendermintKey: Bytes32, val eventsCount: UInt, val ethAddress: Bytes32): Transaction() {
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<ValidatorJoin> by
            TODO()
    {
        val type: UByte = 8.toUByte()
    }
}

data class ValidatorLogout(val slotId: UShort,
                           val tendermintKey: Bytes32,
                           val eventsCount: UInt,
                           val activationEpoch: UInt,
                           val newSigner: Bytes32): Transaction() {
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<ValidatorLogout> by
            TODO()
    {
        val type: UByte = 9.toUByte()
    }
}

data class PeriodVote(val slotId: UShort): Transaction() {
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<PeriodVote> by
            TODO()
    {
        val type: UByte = 11.toUByte()
    }
}

data class Exit(val input: Input): Transaction() {
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<Exit> by
            TODO()
    {
        val type: UByte = 7.toUByte()
    }
}

