import Vector.Companion.ORIGIN
import Vector.Companion.X_AXIS
import Vector.Companion.Y_AXIS
import Vector.Companion.Z_AXIS
import kotlin.Double.Companion.NaN
import kotlin.test.Test
import kotlin.test.assertEquals

class VectorTest {
    @Test
    fun testDot() {
        val v1 = Vector(1.0, 2.0, 3.0)
        val v2 = Vector(3.0, 2.0, 1.0)
        assertEquals(10.0, v1 dot v2)
        assertEquals(10.0, v2 dot v1)
    }

    @Test
    fun testCross() {
        val v1 = Vector(1.0, 2.0, 3.0)
        val v2 = Vector(3.0, 2.0, 1.0)
        assertEquals(Vector(-4.0, 8.0, -4.0), v1 cross v2)
        assertEquals(Vector(4.0, -8.0, 4.0), v2 cross v1)
    }

    @Test
    fun testAxes() {
        for (axis in arrayOf(X_AXIS, Y_AXIS, Z_AXIS)) {
            assertEquals(1.0, axis.length)
            assertEquals(1.0, axis.lengthSquared)
            assertEquals(axis, axis.normalized)
        }

        assertEquals(0.0, X_AXIS dot Y_AXIS)
        assertEquals(0.0, X_AXIS dot Z_AXIS)
        assertEquals(0.0, Y_AXIS dot Z_AXIS)

        assertEquals(Z_AXIS, X_AXIS cross Y_AXIS, Double.MIN_VALUE)
        assertEquals(-Z_AXIS, Y_AXIS cross X_AXIS, Double.MIN_VALUE)
        assertEquals(Y_AXIS, Z_AXIS cross X_AXIS, Double.MIN_VALUE)
        assertEquals(-Y_AXIS, X_AXIS cross Z_AXIS, Double.MIN_VALUE)
        assertEquals(X_AXIS, Y_AXIS cross Z_AXIS, Double.MIN_VALUE)
        assertEquals(-X_AXIS, Z_AXIS cross Y_AXIS, Double.MIN_VALUE)
    }

    @Test
    fun testNormalized() {
        assertEquals(Vector(.36, .48, .8), Vector(9.0, 12.0, 20.0).normalized)
    }

    @Test
    fun testLength() {
        assertEquals(25.0, Vector(9.0, 12.0, 20.0).length)
    }

    @Test
    fun testLengthSquared() {
        assertEquals(14.0, Vector(1.0, 2.0, 3.0).lengthSquared)
    }

    @Test
    fun testTimes() {
        assertEquals(Vector(2.0, 4.0, 6.0), Vector(1.0, 2.0, 3.0) * 2.0)
        assertEquals(Vector(2.0, 4.0, 6.0), 2.0 * Vector(1.0, 2.0, 3.0))
    }

    @Test
    fun testOrigin() {
        val v = Vector(1.0, 2.0, 3.0)
        assertEquals(v, v + ORIGIN)
        assertEquals(v, v - ORIGIN)
        assertEquals(-v, ORIGIN - v)
        assertEquals(ORIGIN, -ORIGIN, Double.MIN_VALUE)
        assertEquals(ORIGIN, v * 0.0)
        assertEquals(ORIGIN, ORIGIN * 2.0)
        assertEquals(ORIGIN, ORIGIN / 2.0)
        assertEquals(0.0, ORIGIN dot v)
        assertEquals(0.0, v dot ORIGIN)
        assertEquals(ORIGIN, ORIGIN cross v)
        assertEquals(ORIGIN, v cross ORIGIN)
        assertEquals(0.0, ORIGIN.length)
        assertEquals(0.0, ORIGIN.lengthSquared)
        assertEquals(Vector(NaN, NaN, NaN), ORIGIN.normalized)
    }

    @Test
    fun testDiv() {
        assertEquals(Vector(1.0, 2.0, 3.0), Vector(2.0, 4.0, 6.0) / 2.0)
    }

    @Test
    fun testPlus() {
        assertEquals(Vector(5.0, 7.0, 9.0), Vector(1.0, 2.0, 3.0) + Vector(4.0, 5.0, 6.0))
    }

    @Test
    fun testMinus() {
        assertEquals(Vector(3.0, 3.0, 3.0), Vector(4.0, 5.0, 6.0) - Vector(1.0, 2.0, 3.0))
    }

    @Test
    fun testUnaryMinus() {
        assertEquals(Vector(-1.0, -2.0, -3.0), -Vector(1.0, 2.0, 3.0))
    }

    @Test
    fun testReflect() {
        assertEquals(Vector(4.0, 3.0, 0.0), Vector(3.0, 4.0, 0.0) reflect Vector(1.0, 1.0, 0.0), 1e-10)
        assertEquals(Vector(-1.0, 2.0, 2.0), Vector(3.0, 0.0, 0.0) reflect Vector(1.0, 1.0, 1.0), 1e-10)
    }
}