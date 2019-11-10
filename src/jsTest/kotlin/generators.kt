package leapcore

import leapcore.lib.bigint.BigInt
import leapcore.lib.crypto.sign
import leapcore.lib.decode.byteArrayFromHexString


// TODO: Monadize this

val exampleAddress = "0x11CE694469797ee31BB755af6f7064312571740E"
val examplePrivateKey = "820a14201caaf3be42bb0976a80ab5c6aee692c71eb8f0b1cb6bf678cef295de"

val genUByte = { (0 .. 255).random().toUByte() }
val genByte = { (-128 .. 127).random().toByte() }
val genUShort = { (0 .. 65535).random().toUShort() }
fun getByteArrayGen(n: Int): () -> ByteArray = { ByteArray(n){ genUByte().toByte() } }
val genBytes32 = getByteArrayGen(32)
val genBytes20 = getByteArrayGen(20)
val genBigInt =  { BigInt.fromByteArray(genBytes32()) }
val genOutput = { Output(genBigInt(), genBytes20(), genColor()) }
val genColor = { (0 .. 0b1100000000000000).random().toUShort() }
val genNstColor = {(0b1100000000000001 .. 0b1111111111111111).random().toUShort()}
val genNstOutput = { Output(genBigInt(), genBytes20(), genNstColor()) }
val genRawOutput = { genOutput().toHexString() }
val genOutputWithData = {
    val output = genNstOutput()
    val data = genBytes32()
    OutputWithData(output, data)
}
val genRawOutputWithData = { genOutputWithData().toHexString() }
val genInput = { Input(genBytes32(), genUByte()) }
val genV = { (0 .. 127).random().toByte() }
val genSignature = { Signature(genBytes32(), genBytes32(), genV()) }
val genRawInput = { genInput().toHexString() }
val genSignedInput = { SignedInput(genInput(), genSignature()) }
val genValidSignedInput = {
    val input = genInput()
    val sig = sign(input.txHash, byteArrayFromHexString(examplePrivateKey))
    val signature = Signature(sig.r, sig.s, sig.v)
    SignedInput(input, signature)
}
val genRawSignedInput = {
    val validInput = genValidSignedInput()
    Pair(validInput.toHexString(), validInput.input.txHash)
}
fun <A> genList( len: Int, gen: () -> A ): List<A> {
    return (1 .. len).map { gen() }
}
val genLength = { (1 .. 15).random() }
val genUnsignedTransfer = {
    val inputs = genList(genLength(), genInput)
    val outputs = genList(genLength(), genOutput)
    UnsignedTransfer(inputs, outputs)
}
val genRawUnsignedTransfer = { genUnsignedTransfer().toHexString() }

