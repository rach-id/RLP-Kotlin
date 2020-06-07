fun ByteArray.toHexString() = joinToString("") { "%02x".format(it) }

fun String.hexToAscii(): String {
    val output = StringBuilder("")
    var i = 0
    while (i < length) {
        val str = substring(i, i + 2)
        output.append(str.toInt(16).toChar())
        i += 2
    }
    return output.toString()
}