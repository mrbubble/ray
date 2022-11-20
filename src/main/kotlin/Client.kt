import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.canvas
import kotlinx.html.dom.create
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.math.roundToInt

fun main() {
    window.onload = {
        val c = makeCanvas("viewport")
        document.body!!.appendChild(c)
        val scene = arrayOf(
            Sphere(Vector(0.0, -1.0, -3.0), 1.0, Color.of(1.0, 0.0, 0.0)),
            Sphere(Vector(-2.0, 0.0, -4.0), 1.0, Color.of(0.0, 1.0, 0.0)),
            Sphere(Vector(2.0, 0.0, -4.0), 1.0, Color.of(0.0, 0.0, 1.0)),
        )
        val viewport = Viewport()
        val background = Color.of(1.0, 1.0, 1.0)
        with(c.get2DContext()) {
            val stream = newPixelStream()
            while (!stream.done) {
                val ray = viewport.canvasToViewport(Point.from(stream.position), Point.from(stream.size))
                val sphere = viewport.traceRay(ray, scene, 1.0..POSITIVE_INFINITY)
                stream.addPixel(sphere?.color ?: background)
            }
            putPixelStream(stream)
        }
    }
}

fun makeCanvas(id: String, size: Point = Point(500.0, 500.0)): HTMLCanvasElement {
    return document.create.canvas {
        attributes["id"] = id
        width = "${size.x.roundToInt()}"
        height = "${size.y.roundToInt()}"
    } as HTMLCanvasElement
}

fun HTMLCanvasElement.get2DContext(): CanvasRenderingContext2D {
    return getContext("2d") as CanvasRenderingContext2D
}

val CanvasRenderingContext2D.width: Double get() = canvas.width.toDouble()
val CanvasRenderingContext2D.height: Double get() = canvas.height.toDouble()
val CanvasRenderingContext2D.size: Point get() = Point(width, height)

fun CanvasRenderingContext2D.fillRect(rect: Rect, style: String) {
    fillStyle = style
    fillRect(rect.position.x, rect.position.y, rect.size.x, rect.size.y)
}
