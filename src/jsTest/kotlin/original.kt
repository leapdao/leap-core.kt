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
    class Output(value: String, address: String, color: Int) {
        fun toRaw(): Buffer
        var value: JSBigInt
        var address: String
        var color: Number

        companion object {
            fun fromRaw(buf: Buffer): Output
        }
    }
}