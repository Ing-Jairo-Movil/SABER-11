package co.saber11preparate.app.domain

/**
 * Sesión de demostración en memoria.
 * No representa tokens reales ni autenticación de producción.
 */
data class AuthSession(
    val user: UserProfile,
)
