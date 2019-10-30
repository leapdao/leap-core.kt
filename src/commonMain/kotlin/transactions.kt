package leapcore

import leapcore.lib.encode.*
import leapcore.lib.decode.*

sealed class Transaction : Serializable {
    abstract val type: UByte
}

fun lengthsHash(inLength: UByte, outLength: UByte): UByte {
    return (16u.toUByte() * inLength + outLength).toUByte()
}

// where do I put input validation? inputs and outputs max 15
data class Transfer(val inputs: List<Input>, val outputs: List<Output>) : Transaction() {
    override val type = 3u.toUByte(); // will be ignored by data class methods, problem? Maybe on copy type wont get copied?
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<Transfer> by
            TODO()
}

data class Deposit(val depositId: UInt, val output: Output) : Transaction() {
    override val type = 2u.toUByte();
    override fun toByteArray(): ByteArray = TODO()

    companion object : Decoder<Deposit> by
            TODO()
}