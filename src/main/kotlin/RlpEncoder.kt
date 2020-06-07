import java.lang.Exception
import kotlin.math.pow

object RlpEncoder {

    fun encode(input: ByteArray) : ByteArray? {
        if(input.size == 1) return input
        else if(input.size in 2..55 || input.isEmpty()) return encodeMedium(input)
        else if(input.size < 256.toDouble().pow(8.toDouble())) return encodeLong(input)
        else  throw Exception("Long input provided!")
    }

    fun encode(inputList: List<ByteArray>) : ByteArray? {
        val payloadSize = payloadSize(inputList)
        if (payloadSize in 0..55) return encodeSmallList(inputList, payloadSize)
        else return null
    }

    private fun encodeSmallList(inputList: List<ByteArray>, payloadSize: Int): ByteArray {
        val prefix = 0xc0 + payloadSize
        val concatInput: ByteArray = concatByteArray(inputList)
        return ByteArray(payloadSize + 1) {
            i ->
            when(i) {
                0 -> prefix.toByte()
                else -> concatInput[i - 1]
            }
        }
    }

    private fun concatByteArray(inputList: List<ByteArray>): ByteArray {
        val concat = arrayListOf<Byte>()
        concat.addAll(inputList.map { byteArray -> byteArray.toList() }.flatten())
        return concat.toByteArray()
    }

//    private fun payloadSize(input: ByteArray): Any {
//        if(input::class == ByteArray::class) return encode(input is ByteArray)
//    }

    private fun payloadSize(input: List<ByteArray>): Int {
        return input.map { it.size }.sum()
    }

    private fun encodeLong(input: ByteArray): ByteArray {
        val prefix = 0xb7 + input.size.toString().length - 1
        return ByteArray(input.size + 2
        ) { i ->
            when(i) {
                0 -> prefix.toByte()
                1-> input.size.toByte()
                else -> input[i - 2]
            }
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