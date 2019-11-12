package leapcore

import leapcore.lib.bigint.JSBigInt
import leapcore.lib.decode.byteArrayFromHexString
import leapcore.lib.encode.toHexString

external class Buffer {
    fun toString(encoding: String): String

    companion object {
        fun from(string: String, encoding: String): Buffer
    }
}
fun ByteArray.toBuffer(): Buffer = Buffer.from(this.toHexString(), "hex")
fun Buffer.toByteArray(): ByteArray = byteArrayFromHexString(this.toString("hex"))

@JsModule("leap-core")
@JsNonModule
external class LeapCore {
    class Output {
        constructor(value: String, address: String, color: Int)
        constructor(value: String, address: String, color: Int, data: String)
        fun toRaw(): Buffer
        var value: JSBigInt
        var address: String
        var color: Number
        var data: String

        companion object {
            fun fromRaw(buf: Buffer): Output
        }
    }

    class Outpoint {
        constructor(hash: String, index: Int)
        fun toRaw(): Buffer
        fun getUtxoId(): String
        var hash: Buffer
        var index: Number

        companion object {
            fun fromRaw(buf: Buffer): Outpoint
        }
    }

    interface SpendingConditionInputOptions {
        var script: Buffer
        var msgData: Buffer
        var prevout: Outpoint
    }

    class Input {
        constructor(outpoint: Outpoint)
        constructor(options: SpendingConditionInputOptions)
        fun toRaw(): Buffer
        fun setSig(r: Buffer, s: Buffer, v: Number, signer: String): Unit
        var r: Buffer
        var s: Buffer
        var v : Number
        var prevout: Outpoint
        var msgData: Buffer
        var script: Buffer

        companion object {
            fun fromRaw(buf: Buffer, offset: Int, sigHashBuff: Buffer): Input
            fun fromRaw(buf: Buffer, offset: Int, type: Int): Input
        }
    }

    class Tx {
        constructor(type: Int, inputs: Array<Outpoint>, outputs: Array<Output>)
        fun toRaw(): Buffer
        fun sigDataBuf(): Buffer

        var inputs: Array<Input>
        var outputs: Array<Output>

        companion object {
            fun fromRaw(buf: Buffer): Tx
            fun transfer(inputs: Array<Input>, outputs: Array<Output>): Tx
            fun spendCond(inputs: Array<Input>, outputs: Array<Output>): Tx
            fun deposit(depositId: Long, value: JSBigInt, address: String, color: Number): Tx
        }
    }
}

fun LeapCore.Output.toHexString() = this.toRaw().toString("hex")
fun LeapCore.Output.Companion.fromHexString(hexString: String) = this.fromRaw(Buffer.from(hexString, "hex"))

fun LeapCore.Outpoint.toHexString() = this.toRaw().toString("hex")
fun LeapCore.Outpoint.Companion.fromHexString(hexString: String) = this.fromRaw(Buffer.from(hexString, "hex"))

fun LeapCore.Input.toHexString() = this.toRaw().toString("hex")
fun LeapCore.Input.Companion.fromHexString(hexString: String, msgHash: ByteArray) = this.fromRaw(Buffer.from(hexString, "hex"), 0, msgHash.toBuffer())
fun LeapCore.Input.Companion.fromHexString(hexString: String) = this.fromRaw(Buffer.from(hexString, "hex"), 0, 13)

fun LeapCore.Tx.toHexString() = this.toRaw().toString("hex")
fun LeapCore.Tx.Companion.fromHexString(hexString: String) = this.fromRaw(Buffer.from(hexString, "hex"))