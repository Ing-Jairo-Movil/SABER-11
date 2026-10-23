package co.saber11preparate.app.data.auth

import co.saber11preparate.app.domain.AuthError
import co.saber11preparate.app.domain.AuthRepository
import co.saber11preparate.app.domain.AuthResult
import co.saber11preparate.app.domain.AuthSession
import co.saber11preparate.app.domain.AuthValidation
import co.saber11preparate.app.domain.SaberArea
import co.saber11preparate.app.domain.SchoolGrade
import co.saber11preparate.app.domain.UserProfile
import co.saber11preparate.app.domain.UserRole

/**
 * Repositorio de autenticación de demostración.
 * Alcance: proceso de la app en memoria; se pierde al cerrar el proceso.
 */
class FakeAuthRepository(
    private val store: InMemoryCredentialStore = InMemoryCredentialStore(),
) : AuthRepository {

    override fun currentSession(): AuthSession? {
        val id = store.currentSessionProfileId ?: return null
        val profile = store.profileById(id) ?: return null
        return AuthSession(profile)
    }

    override suspend fun registerStudent(
        displayName: String,
        email: String,
        grade: SchoolGrade?,
        password: String,
        confirmPassword: String,
    ): AuthResult {
        val name = displayName.trim()
        val normalizedEmail = AuthValidation.normalizeEmail(email)
        validateRegistrationBasics(name, normalizedEmail, password, confirmPassword)?.let {
            return AuthResult.Failure(it)
        }
        if (grade == null) return AuthResult.Failure(AuthError.MissingStudentGrade)
        if (store.emailExists(normalizedEmail)) {
            return AuthResult.Failure(AuthError.EmailAlreadyRegistered)
        }

        val profile = UserProfile(
            id = store.newId(),
            displayName = name,
            email = normalizedEmail,
            role = UserRole.STUDENT,
            studentGrade = grade,
            teachingArea = null,
        )
        store.register(profile, password)
        store.setSession(profile.id)
        return AuthResult.Success(AuthSession(profile))
    }

    override suspend fun registerTeacher(
        displayName: String,
        email: String,
        area: SaberArea?,
        password: String,
        confirmPassword: String,
    ): AuthResult {
        val name = displayName.trim()
        val normalizedEmail = AuthValidation.normalizeEmail(email)
        validateRegistrationBasics(name, normalizedEmail, password, confirmPassword)?.let {
            return AuthResult.Failure(it)
        }
        if (area == null) return AuthResult.Failure(AuthError.MissingTeachingArea)
        if (store.emailExists(normalizedEmail)) {
            return AuthResult.Failure(AuthError.EmailAlreadyRegistered)
        }

        val profile = UserProfile(
            id = store.newId(),
            displayName = name,
            email = normalizedEmail,
            role = UserRole.TEACHER,
            studentGrade = null,
            teachingArea = area,
        )
        store.register(profile, password)
        store.setSession(profile.id)
        return AuthResult.Success(AuthSession(profile))
    }

    override suspend fun signIn(email: String, password: String): AuthResult {
        val normalizedEmail = AuthValidation.normalizeEmail(email)
        if (normalizedEmail.isEmpty() || password.isEmpty()) {
            return AuthResult.Failure(AuthError.EmptyFields)
        }
        if (!AuthValidation.isValidEmail(normalizedEmail)) {
            return AuthResult.Failure(AuthError.InvalidEmail)
        }
        val profile = store.authenticate(normalizedEmail, password)
            ?: return AuthResult.Failure(AuthError.InvalidCredentials)
        store.setSession(profile.id)
        return AuthResult.Success(AuthSession(profile))
    }

    override suspend fun signOut() {
        store.setSession(null)
    }

    override suspend fun requestPasswordReset(email: String): AuthResult {
        val normalizedEmail = AuthValidation.normalizeEmail(email)
        if (normalizedEmail.isEmpty()) {
            return AuthResult.Failure(AuthError.EmptyFields)
        }
        if (!AuthValidation.isValidEmail(normalizedEmail)) {
            return AuthResult.Failure(AuthError.InvalidEmail)
        }
        // No se verifica si el correo existe ni se envía nada: solo simulación UX.
        return AuthResult.PasswordResetSimulated
    }

    /**
     * Intento explícito de registro admin (solo para pruebas); siempre rechazado.
     */
    fun rejectPublicAdminRegistration(): AuthResult =
        AuthResult.Failure(AuthError.AdminRegistrationForbidden)

    private fun validateRegistrationBasics(
        name: String,
        normalizedEmail: String,
        password: String,
        confirmPassword: String,
    ): AuthError? {
        if (name.isEmpty() || normalizedEmail.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            return AuthError.EmptyFields
        }
        if (!AuthValidation.isValidEmail(normalizedEmail)) {
            return AuthError.InvalidEmail
        }
        if (!AuthValidation.isStrongEnoughPassword(password)) {
            return AuthError.WeakPassword
        }
        if (password != confirmPassword) {
            return AuthError.PasswordMismatch
        }
        return null
    }

    companion object {
        @Volatile
        private var instance: FakeAuthRepository? = null

        fun getInstance(): FakeAuthRepository {
            return instance ?: synchronized(this) {
                instance ?: FakeAuthRepository().also { instance = it }
            }
        }

        /** Solo para pruebas unitarias: evita compartir estado entre tests. */
        internal fun createForTests(): FakeAuthRepository = FakeAuthRepository()
    }
}
