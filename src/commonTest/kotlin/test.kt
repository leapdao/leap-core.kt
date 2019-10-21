package leapcore

import kotlin.test.Test
import kotlin.test.assertTrue

class CommonTests {

    @Test
    fun outputIsParsedCorrectly() {
        val outputHexString = "0x000000000000000000000000000000000000000000000000000000000000000a0000b1ccdb544f603af631525ec406245909ad6e1b60"
        val expectedOutput = Output(BigInt.fromString("10"), byteArrayFomHexString("0xb1ccdb544f603af631525ec406245909ad6e1b60"), 0.toUShort())
        val actualOutput = Output.fromHexString(outputHexString)

        assertTrue { actualOutput == expectedOutput }
    }

    @Test
    fun outputIsSerializedCorrectly() {
        val output = Output(BigInt.fromString("10"), byteArrayFomHexString("0xb1ccdb544f603af631525ec406245909ad6e1b60"), 0.toUShort())
        val expectedHexString = "0x000000000000000000000000000000000000000000000000000000000000000a0000b1ccdb544f603af631525ec406245909ad6e1b60"
        val actualHexString = output.toHexString()

        assertTrue { actualHexString == expectedHexString }
    }
}