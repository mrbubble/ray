fun assertEquals(v1: Vector, v2: Vector, tolerance: Double, message: String? = null) {
    kotlin.test.assertEquals(
        0.0,
        (v1 - v2).length,
        tolerance,
        "${if (message != null) "${message}: " else ""}${v1} vs. ${v2}"
    )
}