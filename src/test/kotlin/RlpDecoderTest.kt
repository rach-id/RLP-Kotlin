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
}