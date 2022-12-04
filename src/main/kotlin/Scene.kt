data class Sphere(val center: Vector, val radius: Double, val material: Material)

class Scene(val spheres: Array<Sphere>, val lights: Array<Light>)