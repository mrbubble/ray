data class Sphere(val center: Vector, val radius: Double, val color: Color)

class Scene(val spheres: Array<Sphere>, val lights: Array<Light>)