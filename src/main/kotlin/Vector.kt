import kotlin.math.sqrt

data class Vector(val x: Double, val y: Double, val z: Double) {
    operator fun times(s: Double): Vector {
        return Vector(x * s, y * s, z * s)
    }

    operator fun div(s: Double): Vector {
        return Vector(x / s, y / s, z / s)
    }

    operator fun plus(v: Vector): Vector {
        return Vector(x + v.x, y + v.y, z + v.z)
    }

    operator fun unaryMinus(): Vector {
        return Vector(-x, -y, -z)
    }

    operator fun minus(v: Vector): Vector {
        return Vector(x - v.x, y - v.y, z - v.z)
    }

    infix fun dot(v: Vector): Double {
        return x * v.x + y * v.y + z * v.z
    }

    infix fun cross(v: Vector): Vector {
        return Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x)
    }

    infix fun reflect(normal: Vector): Vector {
        val n = normal.normalized
        return 2.0 * (n dot this) * n - this
    }

    val lengthSquared get() = this dot this
    val length get() = sqrt(lengthSquared)
    val normalized get() = this / length

    companion object {
        val ORIGIN = Vector(0.0, 0.0, 0.0)
        val X_AXIS = Vector(1.0, 0.0, 0.0)
        val Y_AXIS = Vector(0.0, 1.0, 0.0)
        val Z_AXIS = Vector(0.0, 0.0, 1.0)
    }
}

operator fun Double.times(v: Vector): Vector {
    return v * this
}