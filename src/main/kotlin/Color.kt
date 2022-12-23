import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

data class Color(val red: Double, val green: Double, val blue: Double) {
    fun toVector(): Vector {
        return Vector(red, green, blue)
    }

    operator fun times(c: Color): Color {
        return Color(red * c.red, green * c.green, blue * c.blue)
    }

    operator fun times(n: Double): Color {
        return Color(red * n, green * n, blue * n)
    }

    operator fun plus(c: Color): Color {
        return Color(red + c.red, green + c.green, blue + c.blue)
    }

    val clamped: Color get() = Color(clamp(red), clamp(green), clamp(blue))

    companion object {
        fun from(color: UByteColor): Color {
            return Color(convert(color.red), convert(color.green), convert(color.blue))
        }

        fun from(v: Vector): Color {
            return Color(v.x, v.y, v.z)
        }

        private fun convert(value: UByte): Double {
            return value.toDouble() / UByte.MAX_VALUE.toDouble()
        }

        private fun clamp(value: Double): Double {
            return max(0.0, min(value, 1.0))
        }

        val BLACK = Color(0.0, 0.0, 0.0)
        val RED = Color(1.0, 0.0, 0.0)
        val GREEN = Color(0.0, 1.0, 0.0)
        val BLUE = Color(0.0, 0.0, 1.0)
        val CYAN = Color(0.0, 1.0, 1.0)
        val YELLOW = Color(1.0, 1.0, 0.0)
        val MAGENTA = Color(1.0, 0.0, 1.0)
        val WHITE = Color(1.0, 1.0, 1.0)
    }
}

operator fun Double.times(c: Color): Color {
    return c * this
}

data class UByteColor(val red: UByte, val green: UByte, val blue: UByte) {
    companion object {
        fun from(color: Color): UByteColor {
            val c = color.clamped
            return UByteColor(convert(c.red), convert(c.green), convert(c.blue))
        }

        private fun convert(value: Double): UByte {
            return (value * UByte.MAX_VALUE.toDouble()).roundToInt().toUByte()
        }
    }
}

fun Color.toStyle(): String {
    return UByteColor.from(this).toStyle()
}

fun UByteColor.toStyle(): String {
    return "rgb($red,$green,$blue)"
}

