import kotlin.math.pow

interface Light {
    fun illuminate(p: Vector, normal: Vector, origin: Vector, m: Material): Color
}

class AmbientLight(private val color: Color) : Light {
    override fun illuminate(p: Vector, normal: Vector, origin: Vector, m: Material): Color {
        return color * m.ambientColor
    }
}

sealed class VectorLight : Light {
    protected abstract val color: Color
    abstract fun incidence(p: Vector): Vector
    override fun illuminate(p: Vector, normal: Vector, origin: Vector, m: Material): Color {
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
        val r = ((2 * (n dot l) * n) - l).normalized
        (r dot v).let {
            if (it < 0) {
                return Color.BLACK
            }
            return color * it.pow(s) * c

        }
    }
}

class PointLight(private val position: Vector, override val color: Color) : VectorLight() {
    override fun incidence(p: Vector): Vector {
        return (position - p).normalized
    }
}

class DirectionalLight(direction: Vector, override val color: Color) : VectorLight() {
    private val direction = -direction.normalized
    override fun incidence(p: Vector): Vector {
        return direction
    }
}