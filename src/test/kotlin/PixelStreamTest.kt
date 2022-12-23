import kotlinx.browser.document
import org.w3c.dom.HTMLCanvasElement
import kotlin.test.Test
import kotlin.test.assertContentEquals

class PixelStreamTest {
    @Test
    fun testStream() {
        val canvas = document.createElement("canvas") as HTMLCanvasElement
        canvas.width = 2
        canvas.height = 2
        with(canvas.get2DContext()) {
            val stream = newPixelStream()
            while (!stream.done) {
                stream.addPixel(Color(stream.position.x.toDouble(), stream.position.y.toDouble(), 0.5))
            }
            putPixelStream(stream)

            val img = getImageData(0.0, 0.0, width, height)
            assertContentEquals(
                arrayOf(0U, 0U, 128U, 255U, 255U, 0U, 128U, 255U, 0U, 255U, 128U, 255U, 255U, 255U, 128U, 255U),
                img.data.getContents()
            )
        }
    }
}