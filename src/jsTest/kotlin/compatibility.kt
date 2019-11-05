package leapcore

import kotlin.test.Test


class CompatibilityTests {

    @Test
    fun output() {
        testProperty(genOutput, outputRoundaboutIsIdentity)
        testProperty(genOutput, outputSerializesIdentically)
        testProperty(genRawOutput, outputDeserializesIdentically)
    }

    @Test
    fun outputWithData() {
        testProperty(genOutputWithData, outputWithDataRoundaboutIsIdentity)
        testProperty(genOutputWithData, outputWithDataSerializesIdentiaclly)
        testProperty(genRawOutputWithData, outputWithDataDesrializesIdentically)
    }

}