package leapcore

import kotlin.test.Test
import kotlin.test.assertTrue

class CommonTests {
    @Test
    fun hello() {
        println("Hello from tests")
//        assertTrue { false }
    }

    @Test
    fun outputIsParsedCorrectly() {
        val outputHexString = ""
        val expectedOutput = Output(BigInt.fromString("11"), byteArrayFomHexString("2343"), 0.toUShort())

        assertTrue { Output.fromHexString(outputHexString) == (expectedOutput) }
    }

    @Test
    fun outputIsSerializedCorrectly() {
        val output = Output(BigInt.fromString("11"), byteArrayFomHexString("2343"), 0.toUShort())
        val expectedHexString = ""

        assertTrue { output.toHexString() == expectedHexString }
    }
}