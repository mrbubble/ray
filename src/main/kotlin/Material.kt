data class Material(
    val diffuseColor: Color,
    val specularColor: Color = diffuseColor,
    val shininess: Double = 0.0,
    val ambientColor: Color = diffuseColor
)