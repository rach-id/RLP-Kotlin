import java.lang.Exception
import kotlin.math.pow

object RlpEncoder {

    fun encode(input: ByteArray) : ByteArray? {
        if(input.size == 1 || input.isEmpty()) return input
        else if(input.size in 2..55) return encodeMedium(input)
        else if(input.size < 256.toDouble().pow(8.toDouble())) return encodeLong(input)
        else  throw Exception("Long input provided!")
    }

    private fun encodeLong(input: ByteArray): ByteArray {
        val prefix = 0xb7 + input.size.toString().length
        return ByteArray(input.size + 1
        ) { i ->
            if (i == 0) prefix.toByte()
            else input[i - 1]
        }
    }

    private fun encodeMedium(input: ByteArray): ByteArray {
        val prefix = 0x80 + input.size
        return ByteArray(input.size + 1
        ) { i ->
            if (i == 0) prefix.toByte()
            else input[i - 1]
        }
    }
}