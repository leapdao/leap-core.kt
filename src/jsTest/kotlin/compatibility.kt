package leapcore

import kotlin.test.Test


class CompatibilityTests {

    @Test
    fun output() {
        testProperty(genOutput, outputPathIsIdentity)
        testProperty(genOutput, compareOutputSerialization)
        testProperty(genRawOutput, compareOutputParsing)
    }

}