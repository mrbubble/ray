import kotlin.math.roundToInt

data class Point(val x: Double, val y: Double) {
    companion object {
        val ORIGIN = Point(0.0, 0.0)
        fun from(p: IntPoint): Point {
            return Point(p.x.toDouble(), p.y.toDouble())
        }
    }
}

data class IntPoint(val x: Int, val y: Int) {
    companion object {
        val ORIGIN = IntPoint(0, 0)
        fun from(p: Point): IntPoint {
            return IntPoint(p.x.roundToInt(), p.y.roundToInt())
        }
    }
}

data class Rect(val position: Point, val size: Point)