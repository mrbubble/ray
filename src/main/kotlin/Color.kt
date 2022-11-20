import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

sealed class Color {
    abstract val red: Double
    abstract val green: Double
    abstract val blue: Double

    private data class ColorImpl(override val red: Double, override val green: Double, override val blue: Double) :
        Color()

    companion object {
        fun of(red: Double, green: Double, blue: Double): Color {
            return ColorImpl(clamp(red), clamp(green), clamp(blue))
        }

        fun from(color: UByteColor): Color {
            return of(convert(color.red), convert(color.green), convert(color.blue))
        }

        private fun convert(value: UByte): Double {
            return value.toDouble() / UByte.MAX_VALUE.toDouble()
        }

        private fun clamp(value: Double): Double {
            return max(0.0, min(value, 1.0))
        }
    }
}

data class UByteColor(val red: UByte, val green: UByte, val blue: UByte) {
    companion object {
        fun from(color: Color): UByteColor {
            return UByteColor(convert(color.red), convert(color.green), convert(color.blue))
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

