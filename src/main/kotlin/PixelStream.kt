import org.khronos.webgl.Uint8ClampedArray
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.ImageData

interface PixelStream {
    val position: IntPoint
    val size: IntPoint
    val done: Boolean

    fun addPixel(color: Color) {
        addPixel(UByteColor.from(color))
    }

    fun addPixel(color: UByteColor)
}

inline operator fun Uint8ClampedArray.get(index: Int): UByte = (asDynamic()[index] as Int).toUByte()
inline operator fun Uint8ClampedArray.set(index: Int, value: UByte) {
    asDynamic()[index] = value.toInt()
}

fun Uint8ClampedArray.getContents() = Array(length) { this[it] }

class ImageDataPixelStream(private val img: ImageData) : PixelStream {
    private var _position = IntPoint.ORIGIN
    private var _cursor = 0
    override val position get() = _position
    override val size get() = IntPoint(img.width, img.height)

    override fun addPixel(color: UByteColor) {
        img.data[_cursor++] = color.red
        img.data[_cursor++] = color.green
        img.data[_cursor++] = color.blue
        img.data[_cursor++] = 255U
        val nextX = _position.x + 1
        _position = if (nextX >= size.x) {
            _position.copy(x = 0, y = _position.y + 1)
        } else {
            _position.copy(x = nextX)
        }
    }

    override val done: Boolean get() = position.y >= size.y

    fun put(ctx: CanvasRenderingContext2D, position: Point = Point.ORIGIN) {
        ctx.putImageData(img, position.x, position.y)
    }
}

fun CanvasRenderingContext2D.newPixelStream(): PixelStream {
    return ImageDataPixelStream(createImageData(width, height))
}

fun CanvasRenderingContext2D.putPixelStream(stream: PixelStream, position: Point = Point.ORIGIN) {
    (stream as ImageDataPixelStream).put(this, position)
}