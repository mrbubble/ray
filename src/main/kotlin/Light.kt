import kotlin.math.pow

interface Light {
    fun illuminate(p: Vector, normal: Vector, origin: Vector, m: Material, scene: Scene): Color
}

class AmbientLight(private val color: Color) : Light {
    override fun illuminate(p: Vector, normal: Vector, origin: Vector, m: Material, scene: Scene): Color {
        return color * m.ambientColor
    }
}

sealed class VectorLight : Light {
    protected abstract val color: Color
    abstract fun incidence(p: Vector): Vector
    abstract fun isShadowed(p: Vector, scene: Scene): Boolean
    override fun illuminate(p: Vector, normal: Vector, origin: Vector, m: Material, scene: Scene): Color {
        if (isShadowed(p, scene)) {
            return Color.BLACK
        }
        val l = incidence(p)
        val n = normal.normalized
        val v = (origin - p).normalized
        return diffuse(l, n, m.diffuseColor) + specular(l, n, v, m.specularColor, m.shininess)
    }

    private fun diffuse(l: Vector, n: Vector, c: Color): Color {
        (l dot n).let {
            if (it < 0) {
                return Color.BLACK
            }
            return color * it * c
        }
    }

    private fun specular(l: Vector, n: Vector, v: Vector, c: Color, s: Double): Color {
        if (s <= 0.0) {
            return Color.BLACK
        }
        val r = (l reflect n).normalized
        (r dot v).let {
            if (it < 0) {
                return Color.BLACK
            }
            return color * it.pow(s) * c
        }
    }
}

internal const val EPSILON = 1e-3

class PointLight(private val position: Vector, override val color: Color) : VectorLight() {
    override fun incidence(p: Vector): Vector {
        return (position - p).normalized
    }

    override fun isShadowed(p: Vector, scene: Scene): Boolean {
        return scene.traceRay(Ray(p to position), EPSILON..1.0).first != null
    }
}

class DirectionalLight(direction: Vector, override val color: Color) : VectorLight() {
    private val direction = -direction.normalized
    override fun incidence(p: Vector): Vector {
        return direction
    }

    override fun isShadowed(p: Vector, scene: Scene): Boolean {
        return scene.traceRay(Ray(p, direction), EPSILON..Double.POSITIVE_INFINITY).first != null
    }
}