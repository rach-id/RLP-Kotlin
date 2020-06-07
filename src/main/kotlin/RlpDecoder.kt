object RlpDecoder {

    fun decode(encodedInput: ByteArray) : String {
        val hexEncodedInput = encodedInput.toHexString()
        when(Integer.parseInt(hexEncodedInput.substring(0..1), 16)) {
            in 0x0..0x7f -> return decodeShortString(encodedInput)
            in 0x80..0xb7 -> return decodeMediumString(encodedInput)
            in 0xb8..0xbf -> return decodeLongString(encodedInput)
            in 0xc0..0xf7 -> return decodeShortList(encodedInput)
            in 0xf0..0xff -> return decodeLongList(encodedInput)
        }
        return ""
    }

    private fun decodeShortList(encodedInput: ByteArray): String {
        val decodedList = mutableListOf<String>()
        var leftInput = encodedInput.drop(1).toByteArray()
        while(leftInput.isNotEmpty()){
            val currentLength = getLength(leftInput.toHexString()) + 2
            val currentList = leftInput.slice(0..(currentLength - 2))
            leftInput = leftInput.drop(currentLength-1).toByteArray()
            decodedList += decode(currentList.toByteArray())
        }

        return decodedList.joinToString(",")
    }

    private fun decodeLongList(encodedInput: ByteArray): String {
        val decodedList = mutableListOf<String>()
        var leftInput = encodedInput.drop(2).toByteArray()
        while(leftInput.isNotEmpty()){
            val currentLength = getLength(leftInput.toHexString()) + 2
            val currentList = leftInput.slice(0..(currentLength - 4))
            leftInput = leftInput.drop(currentLength-1).toByteArray()
            decodedList += decode(currentList.toByteArray())
        }

        return decodedList.joinToString(",")
    }

    private fun getLength(hexEncodedInput: String): Int {
        return when(val prefix = Integer.parseInt(hexEncodedInput.substring(0..1), 16)) {
            in 0x0..0x7f -> 1
            in 0x80..0xb7 -> prefix - 0x80
            in 0xb8..0xbf -> Integer.parseInt(hexEncodedInput.substring(2..3), 16)
            in 0xc0..0xf7 -> prefix - 0xc0
            in 0xf8..0xff -> Integer.parseInt(hexEncodedInput.substring(2..3), 16)
            else -> 0
        }
    }

    private fun decodeShortString(encodedInput: ByteArray): String = encodedInput.toHexString().hexToAscii()

    private fun decodeMediumString(encodedInput: ByteArray): String {
        val hexEncodedInput = encodedInput.toHexString()
        return encodedInput.slice(1..getLength(hexEncodedInput)).toByteArray().toHexString().hexToAscii()
    }

    private fun decodeLongString(encodedInput: ByteArray): String {
        val hexEncodedInput = encodedInput.toHexString()
        return encodedInput.slice(2..getLength(hexEncodedInput)).toByteArray().toHexString().hexToAscii()
    }
}