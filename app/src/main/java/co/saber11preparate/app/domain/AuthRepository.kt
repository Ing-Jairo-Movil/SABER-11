package co.saber11preparate.app.domain

/**
 * Contrato de autenticación desacoplado del proveedor.
 * La implementación de esta fase es solo demostración en memoria.
 */
interface AuthRepository {
    fun currentSession(): AuthSession?

    suspend fun registerStudent(
        displayName: String,
        email: String,
        grade: SchoolGrade?,
        password: String,
        confirmPassword: String,
    ): AuthResult

    suspend fun registerTeacher(
        displayName: String,
        email: String,
        area: SaberArea?,
        password: String,
        confirmPassword: String,
    ): AuthResult

    suspend fun signIn(email: String, password: String): AuthResult

    suspend fun signOut()

    /**
     * Simula una solicitud de recuperación.
     * No envía correo ni genera tokens reales.
     */
    suspend fun requestPasswordReset(email: String): AuthResult
}
