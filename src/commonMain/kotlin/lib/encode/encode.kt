package leapcore.lib.encode

interface Serializable {
    fun toByteArray(): ByteArray
    fun toHexString(): String = toByteArray().toHexString()
}

fun ByteArray.toHexString() = asUByteArray().joinToString("") { it.toString(16).padStart(2, '0') }

fun UShort.toByteArray() = ByteArray(2) { this.toUInt().shr((1-it) * 4).toByte() }
fun UByte.toByteArray() = ByteArray(1) { this.toByte() }
