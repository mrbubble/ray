import kotlin.math.sqrt

data class Sphere(val center: Vector, val radius: Double, val material: Material) {
    fun intersect(ray: Ray): Pair<Double, Double> {
        val co = center - ray.origin
        val a = ray.direction dot ray.direction
        val m = (ray.direction dot co) / a
        val dd = m * m + (radius * radius - (co dot co)) / a
        if (dd < 0) return Double.POSITIVE_INFINITY to Double.POSITIVE_INFINITY
        val d = sqrt(dd)
        return m - d to m + d
    }
}

data class Ray(val origin: Vector, val direction: Vector) {
    constructor(points: Pair<Vector, Vector>) : this(points.first, points.second - points.first)
}

class Scene(val spheres: Array<Sphere>, val lights: Array<Light>, val background: Color = Color.WHITE) {
    /**
     * Given a ray and a range, gives the sphere that is hit first by the ray and the point of intersection.
     * The range is in factors of the ray's length, so e.g. 1.0 means the endpoint of the ray,
     * 0.0 means the origin point of the range, 0.5 means the midpoint between the ends of the ray.
     */
    fun traceRay(ray: Ray, range: ClosedFloatingPointRange<Double>): Pair<Sphere?, Vector> {
        var closest: Sphere? = null
        var distance: Double = Double.POSITIVE_INFINITY
        for (sphere in spheres) {
            val (t1, t2) = sphere.intersect(ray)
            if (t1 in range && t1 < distance) {
                closest = sphere
                distance = t1
            } else if (t2 in range && t2 < distance) {
                closest = sphere
                distance = t2
            }
        }
        return if (closest == null) null to Vector.ORIGIN else {
            closest to ray.origin + ray.direction * distance
        }
    }

    fun illuminate(ray: Ray): Color {
        return illuminateImpl(ray, 1.0..Double.POSITIVE_INFINITY, 3)
    }

    private fun illuminateImpl(ray: Ray, range: ClosedFloatingPointRange<Double>, depth: Int): Color {
        val (sphere, p) = traceRay(ray, range)
        if (sphere == null) {
            return background
        }

        var color = Color.BLACK
        val normal = p - sphere.center
        for (light in lights) {
            color += light.illuminate(p, normal, ray.origin, sphere.material, this)
        }
        val r = sphere.material.reflectiveness
        if (depth <= 0 || r <= 0) {
            return color
        }

        val reflection =
            illuminateImpl(Ray(p, (-ray.direction) reflect normal), EPSILON..Double.POSITIVE_INFINITY, depth - 1)

        return (color * (1 - r)) + (reflection * r)
    }
}