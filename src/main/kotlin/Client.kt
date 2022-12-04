import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.html.canvas
import kotlinx.html.dom.create
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.math.roundToInt

/**
 * Inspired by:
 * https://gabrielgambetta.com/computer-graphics-from-scratch/
 */
fun main() {
    window.onload = {
        val c = makeCanvas("viewport")
        document.body!!.appendChild(c)
        val scene = Scene(
            arrayOf(
                Sphere(
                    Vector(0.0, -1.0, -3.0),
                    1.0,
                    Material(Color.RED, shininess = 500.0)
                ),
                Sphere(
                    Vector(-2.0, 0.0, -4.0),
                    1.0,
                    Material(Color.GREEN, shininess = 10.0)
                ),
                Sphere(Vector(2.0, 0.0, -4.0), 1.0, Material(Color.BLUE, shininess = 500.0)),
                Sphere(Vector(0.0, -5001.0, 0.0), 5000.0, Material(Color.YELLOW, shininess = 1000.0)),
            ), arrayOf(
                AmbientLight(Color.WHITE * 0.2),
                PointLight(Vector(2.0, 1.0, 0.0), Color.WHITE * 0.6),
                DirectionalLight(Vector(-1.0, -4.0, 4.0), Color.WHITE * 0.2)
            )
        )
        val viewport = Viewport()
        val background = Color.WHITE // Color.of(0.0, .75, 1.0)
        with(c.get2DContext()) {
            val stream = newPixelStream()
            while (!stream.done) {
                val ray = viewport.canvasToViewport(Point.from(stream.position), Point.from(stream.size))
                val (sphere, p) = viewport.traceRay(ray, scene, 1.0..POSITIVE_INFINITY)
                if (sphere == null) {
                    stream.addPixel(background)
                } else {
                    var color = Color.BLACK
                    val normal = p - sphere.center
                    for (light in scene.lights) {
                        color += light.illuminate(p, normal, viewport.origin, sphere.material)
                    }
                    stream.addPixel(color)
                }
            }
            putPixelStream(stream)
        }
    }
}

fun makeCanvas(id: String, size: Point = Point(600.0, 600.0)): HTMLCanvasElement {
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
