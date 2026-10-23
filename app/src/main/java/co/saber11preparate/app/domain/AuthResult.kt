package co.saber11preparate.app.domain

sealed class AuthResult {
    data class Success(val session: AuthSession) : AuthResult()

    /** Solicitud de recuperación aceptada en modo simulado; no se envió correo. */
    data object PasswordResetSimulated : AuthResult()

    data class Failure(val error: AuthError) : AuthResult()
}

sealed class AuthError {
    data object EmptyFields : AuthError()
    data object InvalidEmail : AuthError()
    data object WeakPassword : AuthError()
    data object PasswordMismatch : AuthError()
    data object EmailAlreadyRegistered : AuthError()
    data object InvalidCredentials : AuthError()
    data object AdminRegistrationForbidden : AuthError()
    data object MissingStudentGrade : AuthError()
    data object MissingTeachingArea : AuthError()
}
