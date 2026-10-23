package co.saber11preparate.app.domain

/**
 * Perfil de usuario en el entorno de autenticación simulada.
 */
data class UserProfile(
    val id: String,
    val displayName: String,
    val email: String,
    val role: UserRole,
    val studentGrade: SchoolGrade? = null,
    val teachingArea: SaberArea? = null,
)
