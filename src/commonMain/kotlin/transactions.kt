package leapcore

sealed class Transaction : Serializable {
    abstract val type: UByte
}

// where do I put input validation? inputs and outputs max 15
data class Transfer(val inputs: List<Input>, val outputs: List<Output>) : Transaction() {
    override val type =
        3u.toUByte(); // will be ignored by data class methods, problem? Maybe on copy type wont get copied?

    override fun toBinary(): ByteArray = TODO()

    override fun toHexString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object : Deserializable<Transfer> {
        override fun fromBinary(binaryData: ByteArray): Transfer = TODO()

        override fun fromHexString(string: String): Transfer {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}

data class Deposit(val depositId: UInt) : Transaction() {
    override val type = 2u.toUByte();
    override fun toBinary(): ByteArray = TODO()

    override fun toHexString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object : Deserializable<Deposit> {
        override fun fromBinary(binaryData: ByteArray): Deposit = TODO()

        override fun fromHexString(string: String): Deposit {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}

fun lengthsHash(inLength: UByte, outLength: UByte): UByte {
    return (16u.toUByte() * inLength + outLength).toUByte()
}