import Vector.Companion.Y_AXIS
import Vector.Companion.Z_AXIS
import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.math.sqrt

class Viewport(
    val origin: Vector = Vector.ORIGIN,
    front: Vector = -Z_AXIS,
    up: Vector = Y_AXIS,
    val size: Vector = Vector(1.0, 1.0, 1.0)
) {
    val front = front.normalized
    val right = (front cross up).normalized
    val up = right cross this.front

    fun canvasToViewport(p: Point, canvasSize: Point): Vector {
        val dx = size.x * (p.x / canvasSize.x - .5)
        val dy = size.y * (.5 - p.y / canvasSize.y)
        return origin + (right * dx) + (up * dy) + (front * size.z)
    }

    fun traceRay(ray: Vector, scene: Scene, range: ClosedFloatingPointRange<Double>): Sphere? {
        var closest: Sphere? = null
        var distance: Double = POSITIVE_INFINITY
        for (sphere in scene) {
            val (t1, t2) = intersect(ray, sphere)
            if (t1 in range && t1 < distance) {
                closest = sphere
                distance = t1
            } else if (t2 in range && t2 < distance) {
                closest = sphere
                distance = t2
            }
        }
        return closest
    }

    internal fun intersect(ray: Vector, sphere: Sphere): Pair<Double, Double> {
        val co = sphere.center - origin
        val a = ray dot ray
        val m = (ray dot co) / a
        val dd = m * m + (sphere.radius * sphere.radius - (co dot co)) / a
        if (dd < 0) return POSITIVE_INFINITY to POSITIVE_INFINITY
        val d = sqrt(dd)
        return m - d to m + d
    }
}