package leapcore

typealias Bytes32 = ByteArray
typealias Bytes20 = ByteArray
//typealias Bytes1 = UByte
//typealias Bytes2 = UShort
//typealias Bytes4 = UInt

fun byteArrayFomHexString(hexString: String): ByteArray {
    val arrayOfBytes = hexString.subSequence(2, hexString.length).chunked(2).map { it.toUByte(16).toByte() }
    return ByteArray(arrayOfBytes.size) { arrayOfBytes[it] }
}

fun ByteArray.toHexString() = "0x" + asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }