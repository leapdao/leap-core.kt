package leapcore

import leapcore.lib.encode.*
import leapcore.lib.decode.*

sealed class Transaction : Serializable {
    abstract val type: UByte
}

fun lengthsHash(inLength: UByte, outLength: UByte): UByte {
    return (16u.toUByte() * inLength + outLength).toUByte()
}


data class Transfer(val inputs: List<SignedInput>, val outputs: List<Output>) : Transaction() {
    override val type = 3.toUByte(); // will be ignored by data class methods, problem? Maybe on copy type wont get copied?
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<Transfer> by
            TODO()
}

data class SpendingCondition(val inputs: List<SignedInput>, val outputs: List<Output>): Transaction() {
    override val type: UByte = 13.toUByte()
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<SpendingCondition> by
            TODO()
}

data class Deposit(val depositId: UInt, val output: Output) : Transaction() {
    override val type = 2.toUByte();
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<Deposit> by
            TODO()
}

data class EpochLength(val epochLength: UInt) : Transaction() {
    override val type: UByte = 12.toUByte()
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<EpochLength> by
            TODO()
}

data class MinGasPrice(val minGasPrice: ByteArray): Transaction() {
    override val type: UByte = 14.toUByte()
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<MinGasPrice> by
            TODO()
}

data class ValidatorJoin(val slotId: UShort, val tendermintKey: Bytes32, val eventsCount: UInt, val ethAddress: Bytes32): Transaction() {
    override val type: UByte = 8.toUByte()
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<ValidatorJoin> by
            TODO()
}

data class ValidatorLogout(val slotId: UShort,
                           val tendermintKey: Bytes32,
                           val eventsCount: UInt,
                           val activationEpoch: UInt,
                           val newSigner: Bytes32): Transaction() {
    override val type: UByte = 9.toUByte()
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<ValidatorLogout> by
            TODO()
}

data class PeriodVote(val slotId: UShort): Transaction() {
    override val type: UByte = 11.toUByte()
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<PeriodVote> by
            TODO()
}

data class Exit(val input: Input): Transaction() {
    override val type: UByte = 7.toUByte()
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<Exit> by
            TODO()
}

