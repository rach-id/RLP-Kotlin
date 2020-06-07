object RlpDecoder {

    fun decode(encodedInput: ByteArray) : String {
        val hexEncodedInput = encodedInput.toHexString()
        when(Integer.parseInt(hexEncodedInput.substring(0..1), 16)) {
            in 0x0..0x7f -> return encodedInput.toHexString().hexToAscii()
            in 0x80..0xb7 -> return encodedInput.drop(1).toByteArray().toHexString().hexToAscii()
            in 0xb8..0xbf -> return encodedInput.slice(2..Integer.parseInt(hexEncodedInput.substring(2..3), 16)).toByteArray().toHexString().hexToAscii()
        }
        return ""
    }
}