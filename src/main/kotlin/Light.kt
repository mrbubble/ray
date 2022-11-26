interface Light {
    fun illuminate(p: Vector, normal: Vector, c: Color): Color
}

class AmbientLight(private val color: Color) : Light {
    override fun illuminate(p: Vector, normal: Vector, c: Color): Color {
        return color * c
    }
}

class PointLight(private val position: Vector, private val color: Color) : Light {
    override fun illuminate(p: Vector, normal: Vector, c: Color): Color {
        ((position - p).normalized dot normal.normalized).let {
            if (it < 0) {
                return Color.BLACK
            }
            return color * it * c
        }
    }
}

class DirectionalLight(direction: Vector, private val color: Color) : Light {
    private val direction = -direction.normalized
    override fun illuminate(p: Vector, normal: Vector, c: Color): Color {
        (direction dot normal.normalized).let {
            if (it < 0) {
                return Color.BLACK
            }
            return color * it * c
        }
    }
}