package leapcore

import leapcore.lib.bigint.JSBigInt

external class Buffer {
    fun toString(encoding: String): String

    companion object {
        fun from(string: String, encoding: String): Buffer
    }
}

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

    class Input {
        constructor(outpoint: Outpoint)
        fun toRaw(): Buffer
        fun setSig(r: Buffer, s: Buffer, v: Number, signer: String): Unit
        var r: Buffer
        var s: Buffer
        var v : Number
        var prevout: Outpoint

        companion object {
            fun fromRaw(buf: Buffer, offset: Int, sigHashBuff: Buffer): Input
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
        }
    }

}