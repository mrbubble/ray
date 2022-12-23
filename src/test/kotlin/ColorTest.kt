import kotlin.test.Test
import kotlin.test.assertEquals

class ColorTest {
    @Test
    fun testClamping() {
        assertEquals(Color(0.0, 0.5, 1.0), Color(-1.0, 0.5, 2.0).clamped)
    }

    @Test
    fun testConversion() {
        assertEquals(UByteColor(0U, 128U, 255U), UByteColor.from(Color(0.0, 0.5, 1.0)))
        assertEquals(Color(0.0, 128.0 / 255.0, 1.0), Color.from(UByteColor(0U, 128U, 255U)))
    }

    @Test
    fun testStyle() {
        assertEquals("rgb(0,128,255)", Color(0.0, 0.5, 1.0).toStyle())
    }
}