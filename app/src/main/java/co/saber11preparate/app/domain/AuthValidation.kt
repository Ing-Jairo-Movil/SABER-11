package co.saber11preparate.app.domain

object AuthValidation {
    private val EMAIL_REGEX = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    const val MIN_PASSWORD_LENGTH = 8

    fun normalizeEmail(email: String): String = email.trim().lowercase()

    fun isValidEmail(email: String): Boolean =
        EMAIL_REGEX.matches(normalizeEmail(email))

    fun isStrongEnoughPassword(password: String): Boolean =
        password.length >= MIN_PASSWORD_LENGTH
}
