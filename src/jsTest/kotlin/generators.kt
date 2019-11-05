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
val genRawOutput = { genOutput().toHexString() }


