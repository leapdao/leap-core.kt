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
    }

    @Test
    fun spendingConditionInput() {
        testProperty(genSpendingConditionInput, spendingConditionInputRoundaboutIsIdentity)
        testProperty(genSpendingConditionInput, spendingConditionInputSerializesIdentically)
        testProperty(genRawSpendingConditionInput, spendingConditionInputDeserialzesIdentically)
    }

    @Test
    fun transferRoundabout() {
        testProperty(genTransfer, transferRoundaboutIsIdentity)
    }
    @Test
    fun transferSerialization() {
        testProperty(genTransfer, transferSerializesIdentically)
    }
    @Test
    fun transferDeserialization() {
        testPropertyShort(genRawTransfer, transferDeserializesIdentiacally)
    }

    @Test
    fun spendingConditionRoundabout() {
        testProperty(genSpendingCondition, spendingConditionRoundaboutIsIdentity)
    }
    @Test
    fun spendingConditionSerialization() {
        testProperty(genSpendingCondition, spendingConditionSerializesIdentically)
    }
    @Test
    fun spendingConditionDeserialization() {
        testPropertyShort(genRawSpendingCondition, spendingConditionDeserializesIdentically)
    }


}