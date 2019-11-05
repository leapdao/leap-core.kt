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
}