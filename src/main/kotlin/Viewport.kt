import Vector.Companion.Y_AXIS
import Vector.Companion.Z_AXIS

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
}