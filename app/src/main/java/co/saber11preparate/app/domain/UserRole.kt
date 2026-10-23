package co.saber11preparate.app.domain

/**
 * Roles de usuario de la aplicación.
 * Solo STUDENT y TEACHER son registrables públicamente.
 */
enum class UserRole {
    STUDENT,
    TEACHER,
    ADMIN,
}
