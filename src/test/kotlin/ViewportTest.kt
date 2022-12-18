import kotlin.test.Test
import kotlin.test.assertEquals

class ViewportTest {
    @Test
    fun testCanvasToViewport() {
        val viewport = Viewport()
        val canvasSize = Point(500.0, 500.0)
        assertEquals(Vector(-.5, .5, -1.0), viewport.canvasToViewport(Point.ORIGIN, canvasSize))
        assertEquals(Vector(.0, .0, -1.0), viewport.canvasToViewport(Point(250.0, 250.0), canvasSize))
        assertEquals(Vector(.5, -.5, -1.0), viewport.canvasToViewport(Point(500.0, 500.0), canvasSize))
    }

    @Test
    fun testDefaults() {
        assertEquals(-Vector.Z_AXIS, Viewport().front)
        assertEquals(Vector.Y_AXIS, Viewport().up)
        assertEquals(Vector.X_AXIS, Viewport().right)
    }

    @Test
    fun testFront() {
        val v = Viewport(front = Vector(0.0, 0.0, -2.0))
        assertEquals(-Vector.Z_AXIS, v.front, Double.MIN_VALUE)
        assertEquals(Vector.Y_AXIS, v.up, Double.MIN_VALUE)
        assertEquals(Vector.X_AXIS, v.right, Double.MIN_VALUE)
    }

    @Test
    fun testUp() {
        val v = Viewport(up = Vector(0.0, 1.0, -1.0))
        assertEquals(-Vector.Z_AXIS, v.front, Double.MIN_VALUE)
        assertEquals(Vector.Y_AXIS, v.up, Double.MIN_VALUE)
        assertEquals(Vector.X_AXIS, v.right, Double.MIN_VALUE)
    }

    @Test
    fun testTwisted() {
        val v = Viewport(
            origin = Vector(33.0, 42.0, -18.0),
            front = Vector(1.0, -1.0, -1.0),
            up = Vector(-1.0, 1.0, -1.0),
            size = Vector(300.0, 200.0, 80.0)
        )
        assertEquals(Vector(1.0, 1.0, 0.0).normalized, v.right, Double.MIN_VALUE)
        val canvasSize = Point(500.0, 500.0)
        val center = v.canvasToViewport(Point(250.0, 250.0), canvasSize)
        val top = v.canvasToViewport(Point(250.0, 0.0), canvasSize)
        val right = v.canvasToViewport(Point(500.0, 250.0), canvasSize)

        assertEquals(80.0, (center - v.origin).length, 1e-10)
        assertEquals(100.0, (top - center).length, 1e-10)
        assertEquals(150.0, (right - center).length, 1e-10)
        assertEquals(v.front, (center - v.origin).normalized, 1e-10)
        assertEquals(v.up, (top - center).normalized, 1e-10)
        assertEquals(v.right, (right - center).normalized, 1e-10)
    }
}