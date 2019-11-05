package leapcore

import leapcore.lib.bigint.BigInt


// TODO: Monadize this

val genUByte = { (0 .. 255).random().toUByte() }
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


