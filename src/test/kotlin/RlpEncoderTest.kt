import RlpEncoder.encode
import org.junit.jupiter.api.Test

class RlpEncoderTest {

    @Test
    fun `empty test`() {
        val emptyInput = byteArrayOf()
        assert(encode(emptyInput)!!.toHexString() == "80")
    }

    @Test
    fun `integer test`() {
        val integerInput = byteArrayOf(4,0) //1024
        assert(encode(integerInput)!!.toHexString() == "820400")
    }

    @Test
    fun `from 0x00 to 0x7fb test`() {
        val smallInput = "d".toByteArray()
        assert(encode(smallInput)!!.toHexString() == "64")
    }

    @Test
    fun `from 0 to 55 bytes long test`() {
        val mediumInput = "RLP-Kotlin".toByteArray()
        assert(encode(mediumInput)!!.toHexString() == "8a524c502d4b6f746c696e")
    }

    @Test
    fun `more than 55 bytes long test`() {
        val longInput = "RLP-Kotlin is an RLP encoder and decoder written in Kotlin for educational purposes".toByteArray()
        assert(encode(longInput)!!.toHexString() == "b853524c502d4b6f746c696e20697320616e20524c5020656e636f64657220616e64206465636f646572207772697474656e20696e204b6f746c696e20666f7220656475636174696f6e616c20707572706f736573")
    }

    @Test
    fun `less than 55 bytes long list test`() {
        val shortListInput = listOf(encode("cat".toByteArray())!!, encode("dog".toByteArray())!!)
        assert(encode(shortListInput)!!.toHexString() == "c88363617483646f67")
    }

    @Test
    fun `less than 55 bytes long nested list test`() {
        val nestedShortByteArray = listOf( // [ [], [[]], [ [], [[]] ] ]
            encode(listOf(byteArrayOf()))!!,
            encode(listOf(encode(listOf(byteArrayOf()))!!))!!,
            encode(listOf(encode(listOf(byteArrayOf()))!!, encode(listOf(encode(listOf(byteArrayOf()))!!))!!))!!
        )
        assert(encode(nestedShortByteArray)!!.toHexString() == "c7c0c1c0c3c0c1c0")
    }
}