package co.saber11preparate.app.ui.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import co.saber11preparate.app.R
import co.saber11preparate.app.domain.AuthError
import co.saber11preparate.app.domain.AuthValidation

@Composable
fun authErrorMessage(error: AuthError): String = when (error) {
    AuthError.EmptyFields -> stringResource(R.string.auth_error_empty_fields)
    AuthError.InvalidEmail -> stringResource(R.string.auth_error_invalid_email)
    AuthError.WeakPassword -> stringResource(
        R.string.auth_error_weak_password,
        AuthValidation.MIN_PASSWORD_LENGTH,
    )
    AuthError.PasswordMismatch -> stringResource(R.string.auth_error_password_mismatch)
    AuthError.EmailAlreadyRegistered -> stringResource(R.string.auth_error_email_registered)
    AuthError.InvalidCredentials -> stringResource(R.string.auth_error_invalid_credentials)
    AuthError.AdminRegistrationForbidden -> stringResource(R.string.auth_error_admin_forbidden)
    AuthError.MissingStudentGrade -> stringResource(R.string.auth_error_missing_grade)
    AuthError.MissingTeachingArea -> stringResource(R.string.auth_error_missing_area)
}
