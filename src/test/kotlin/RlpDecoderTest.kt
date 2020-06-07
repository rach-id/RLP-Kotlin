import RlpDecoder.decode
import org.junit.jupiter.api.Test

class RlpDecoderTest {

    @Test
    fun `medium size string input test`() {
        val encodedInput = byteArrayOf(0x8a.toByte(), 0x52, 0x4c, 0x50, 0x2d, 0x4b, 0x6f, 0x74, 0x6c, 0x69, 0x6e)
        assert(decode(encodedInput) == "RLP-Kotlin")
    }

    @Test
    fun `one byte string input test`() {
        val encodedInput = byteArrayOf(0x64)
        assert(decode(encodedInput) == "d")
    }

    @Test
    fun `long size string input test`() {
        val encodedInput = byteArrayOf(0xb8.toByte(),0x53,0x52,0x4c,0x50,0x2d,0x4b,0x6f,0x74,0x6c,0x69,0x6e,0x20,0x69,0x73,0x20,0x61,0x6e,0x20,0x52,0x4c,0x50,0x20,0x65,0x6e,0x63,0x6f,0x64,0x65,0x72,0x20,0x61,0x6e,0x64,0x20,0x64,0x65,0x63,0x6f,0x64,0x65,0x72,0x20,0x77,0x72,0x69,0x74,0x74,0x65,0x6e,0x20,0x69,0x6e,0x20,0x4b,0x6f,0x74,0x6c,0x69,0x6e,0x20,0x66,0x6f,0x72,0x20,0x65,0x64,0x75,0x63,0x61,0x74,0x69,0x6f,0x6e,0x61,0x6c,0x20,0x70,0x75,0x72,0x70,0x6f,0x73,0x65,0x73)
        assert(decode(encodedInput) == "RLP-Kotlin is an RLP encoder and decoder written in Kotlin for educational purposes")
    }
}