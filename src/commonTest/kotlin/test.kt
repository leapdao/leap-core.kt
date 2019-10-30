package leapcore

import leapcore.lib.decode.fromHexString
import kotlin.test.Test
import kotlin.test.assertTrue

class CommonTests {

    @Test
    fun outputIsParsedCorrectly() {
        val outputHexString = "000000000000000000000000000000000000000000000000000000000000000a0000b1ccdb544f603af631525ec406245909ad6e1b60"
        val expectedOutput = Output(10, "0xb1ccdb544f603af631525ec406245909ad6e1b60", 0)
        val actualOutput = Output.fromHexString(outputHexString)

        assertTrue { actualOutput == expectedOutput }
    }

    @Test
    fun outputIsSerializedCorrectly() {
        val output = Output(10, "0xb1ccdb544f603af631525ec406245909ad6e1b60", 0)
        val expectedHexString = "000000000000000000000000000000000000000000000000000000000000000a0000b1ccdb544f603af631525ec406245909ad6e1b60"
        val actualHexString = output.toHexString()

        assertTrue { actualHexString == expectedHexString }
    }
}