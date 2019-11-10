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

    @Test
    fun input() {
        testProperty(genInput, inputRoundaboutIsIdentity)
        testProperty(genInput, inputSerializesIdentically)
        testProperty(genRawInput, inputDeserializesIdentically)
        testProperty(genInput, utxoIdIsIdentiacal)
    }

    @Test
    fun signedInput() {
        testProperty(genSignedInput, signedInputRoundaboutIsIdentity)
        testProperty(genSignedInput, signedInputSerializesIdentiaclly)
        testProperty(genRawSignedInput, signedInputDeserializesIdentically)
    }

    @Test
    fun unsignedTransfer() {
        testProperty(genUnsignedTransfer, unsignedTransferRoundaboutIsIdentity)
        testProperty(genUnsignedTransfer, unsignedTransferSerializesIdentiaclly)
//        Not DOABLE
//        testProperty(genRawUnsignedTransfer, unsignedTransferDeserializesIdentiaclly)
    }

}