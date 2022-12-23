data class Material(
    val diffuseColor: Color,
    val specularColor: Color = diffuseColor,
    val shininess: Double = 0.0,
    val ambientColor: Color = diffuseColor,
    val reflectiveness: Double = 0.0,
) {
    init {
        require(reflectiveness in 0.0..1.0)
    }
}