package leapcore.lib.encode

interface Serializable {
    fun toByteArray(): ByteArray
    fun toHexString(): String = toByteArray().toHexString()
}

fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }

fun UShort.toByteArray() = ByteArray(2) { this.toUInt().shr((1-it) * 8).toByte() }
fun UByte.toByteArray() = ByteArray(1) { this.toByte() }
fun UInt.toByteArray() = ByteArray(4) { this.toUInt().shr((3-it) * 8).toByte() }
fun Byte.toByteArray() = ByteArray(1) { this }

fun List<Serializable>.toByteArray(): ByteArray = this.map { it.toByteArray() } .fold(ByteArray(0), {acc, curr -> acc + curr})