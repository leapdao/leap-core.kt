package leapcore

import kotlin.test.Test
import kotlin.test.assertTrue

external class Buffer {
    fun toString(encoding: String): String
}

@JsModule("leap-core")
@JsNonModule
external class LeapCore {
    class Output(value: Int, address: String, color: Int) {
        fun toRaw(): Buffer
    }
}


class CompatibilityTests {

    @Test
    fun outputIsSerializedIdentically() {
        val value = 10
        val address = "0xb1ccdb544f603af631525ec406245909ad6e1b60"
        val color = 0

        val originalOutput = LeapCore.Output(value, address, color)
        val newOutput = Output(BigInt.fromString(value.toString()), byteArrayFomHexString(address), color.toUShort())

        assertTrue { newOutput.toHexString() == "0x" + originalOutput.toRaw().toString("hex") }
    }

}