package co.saber11preparate.app.data.auth

import co.saber11preparate.app.domain.AuthError
import co.saber11preparate.app.domain.AuthResult
import co.saber11preparate.app.domain.SaberArea
import co.saber11preparate.app.domain.SchoolGrade
import co.saber11preparate.app.domain.UserRole
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class FakeAuthRepositoryTest {

    private lateinit var repository: FakeAuthRepository

    @Before
    fun setUp() {
        repository = FakeAuthRepository.createForTests()
    }

    @Test
    fun registerStudent_success_navigatesToStudentRole() = runBlocking {
        val result = repository.registerStudent(
            displayName = "Camila Demo",
            email = "camila@demo.test",
            grade = SchoolGrade.GRADE_11,
            password = "password1",
            confirmPassword = "password1",
        )
        assertTrue(result is AuthResult.Success)
        val session = (result as AuthResult.Success).session
        assertEquals(UserRole.STUDENT, session.user.role)
        assertEquals(SchoolGrade.GRADE_11, session.user.studentGrade)
        assertEquals(session, repository.currentSession())
    }

    @Test
    fun registerTeacher_success() = runBlocking {
        val result = repository.registerTeacher(
            displayName = "Andres Demo",
            email = "andres@demo.test",
            area = SaberArea.MATHEMATICS,
            password = "password1",
            confirmPassword = "password1",
        )
        assertTrue(result is AuthResult.Success)
        val session = (result as AuthResult.Success).session
        assertEquals(UserRole.TEACHER, session.user.role)
        assertEquals(SaberArea.MATHEMATICS, session.user.teachingArea)
    }

    @Test
    fun register_rejectsEmptyFields() = runBlocking {
        val result = repository.registerStudent(
            displayName = "",
            email = "",
            grade = SchoolGrade.GRADE_10,
            password = "",
            confirmPassword = "",
        )
        assertEquals(AuthError.EmptyFields, (result as AuthResult.Failure).error)
    }

    @Test
    fun register_rejectsInvalidEmail() = runBlocking {
        val result = repository.registerStudent(
            displayName = "Ana",
            email = "no-es-correo",
            grade = SchoolGrade.GRADE_10,
            password = "password1",
            confirmPassword = "password1",
        )
        assertEquals(AuthError.InvalidEmail, (result as AuthResult.Failure).error)
    }

    @Test
    fun register_rejectsPasswordMismatch() = runBlocking {
        val result = repository.registerTeacher(
            displayName = "Ana",
            email = "ana@demo.test",
            area = SaberArea.ENGLISH,
            password = "password1",
            confirmPassword = "password2",
        )
        assertEquals(AuthError.PasswordMismatch, (result as AuthResult.Failure).error)
    }

    @Test
    fun register_rejectsDuplicateEmail() = runBlocking {
        repository.registerStudent(
            displayName = "Uno",
            email = "dup@demo.test",
            grade = SchoolGrade.GRADE_9,
            password = "password1",
            confirmPassword = "password1",
        )
        repository.signOut()
        val second = repository.registerTeacher(
            displayName = "Dos",
            email = "dup@demo.test",
            area = SaberArea.CRITICAL_READING,
            password = "password1",
            confirmPassword = "password1",
        )
        assertEquals(AuthError.EmailAlreadyRegistered, (second as AuthResult.Failure).error)
    }

    @Test
    fun signIn_validAndInvalid() = runBlocking {
        repository.registerStudent(
            displayName = "Luis",
            email = "luis@demo.test",
            grade = SchoolGrade.GRADE_11,
            password = "password1",
            confirmPassword = "password1",
        )
        repository.signOut()

        val ok = repository.signIn("luis@demo.test", "password1")
        assertTrue(ok is AuthResult.Success)

        repository.signOut()
        val bad = repository.signIn("luis@demo.test", "wrong-pass")
        assertEquals(AuthError.InvalidCredentials, (bad as AuthResult.Failure).error)
    }

    @Test
    fun signOut_clearsSession() = runBlocking {
        repository.registerStudent(
            displayName = "Eva",
            email = "eva@demo.test",
            grade = SchoolGrade.GRADE_10,
            password = "password1",
            confirmPassword = "password1",
        )
        repository.signOut()
        assertNull(repository.currentSession())
    }

    @Test
    fun passwordReset_isSimulatedOnly() = runBlocking {
        val result = repository.requestPasswordReset("alguien@demo.test")
        assertEquals(AuthResult.PasswordResetSimulated, result)
    }

    @Test
    fun publicAdminRegistration_isForbidden() {
        val result = repository.rejectPublicAdminRegistration()
        assertEquals(AuthError.AdminRegistrationForbidden, (result as AuthResult.Failure).error)
    }

    @Test
    fun registerStudent_requiresGrade() = runBlocking {
        val result = repository.registerStudent(
            displayName = "Sin Grado",
            email = "grado@demo.test",
            grade = null,
            password = "password1",
            confirmPassword = "password1",
        )
        assertEquals(AuthError.MissingStudentGrade, (result as AuthResult.Failure).error)
    }

    @Test
    fun registerTeacher_requiresArea() = runBlocking {
        val result = repository.registerTeacher(
            displayName = "Sin Area",
            email = "area@demo.test",
            area = null,
            password = "password1",
            confirmPassword = "password1",
        )
        assertEquals(AuthError.MissingTeachingArea, (result as AuthResult.Failure).error)
    }
}
