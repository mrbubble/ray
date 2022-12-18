import kotlin.math.sqrt
import kotlin.test.Test

class SphereTest {
    @Test
    fun testIntersect() {
        val ray = Ray(Vector(1.0, 1.0, -1.0), Vector.Z_AXIS * -2.0)
        val black = Material(Color.BLACK)
        kotlin.test.assertEquals(3.0 to 7.0, Sphere(Vector(1.0, 1.0, -11.0), 4.0, black).intersect(ray))
        kotlin.test.assertEquals(5.0 to 5.0, Sphere(Vector(5.0, 1.0, -11.0), 4.0, black).intersect(ray))
        kotlin.test.assertEquals(
            4.0 to 6.0,
            Sphere(Vector(2.0 * sqrt(3.0) + 1.0, 1.0, -11.0), 4.0, black).intersect(ray)
        )
        kotlin.test.assertEquals(
            Double.POSITIVE_INFINITY to Double.POSITIVE_INFINITY,
            Sphere(Vector(7.0, 1.0, -11.0), 4.0, black).intersect(ray)
        )
    }
}