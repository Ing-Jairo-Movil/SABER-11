package co.saber11preparate.app.ui.demo

/**
 * Contenido ficticio exclusivo para demostrar la interfaz (fase 03).
 * No representa datos reales de usuarios, instituciones ni resultados ICFES.
 */
object DemoContent {
    const val DEMO_BANNER = "Modo demostración — datos ficticios"
    const val STUDENT_NAME = "Camila R. (demo)"
    const val STUDENT_GRADE = "Grado 11"
    const val STUDENT_PROGRESS_PERCENT = 62
    const val STUDENT_POINTS = 1_240
    const val STUDENT_STREAK_DAYS = 5

    data class AreaItem(
        val name: String,
        val progressPercent: Int,
        val levelLabel: String,
    )

    val saberAreas = listOf(
        AreaItem("Lectura crítica", 70, "Intermedio"),
        AreaItem("Matemáticas", 55, "Básico"),
        AreaItem("Ciencias naturales", 48, "Básico"),
        AreaItem("Sociales y ciudadanas", 66, "Intermedio"),
        AreaItem("Inglés", 80, "Avanzado"),
    )

    const val TEACHER_NAME = "Prof. Andrés M. (demo)"
    const val TEACHER_SUBJECT = "Área: Matemáticas (demo)"
    const val TEACHER_GROUPS = 3
    const val TEACHER_STUDENTS = 87
    const val TEACHER_PENDING_ACTIVITIES = 4
    const val TEACHER_AVG_COMPLETION = 71

    data class SummaryItem(val title: String, val value: String, val hint: String)

    val teacherSummaries = listOf(
        SummaryItem("Grupos", "3", "Datos demo"),
        SummaryItem("Estudiantes", "87", "Datos demo"),
        SummaryItem("Actividades", "4 pendientes", "Datos demo"),
        SummaryItem("Avance medio", "71%", "Datos demo"),
    )

    const val ADMIN_NAME = "Admin institucional (demo)"
    const val ADMIN_USERS = 312
    const val ADMIN_ACTIVE_GROUPS = 18
    const val ADMIN_OPEN_SIMULATIONS = 6

    val adminSummaries = listOf(
        SummaryItem("Usuarios", "312", "Datos demo"),
        SummaryItem("Grupos activos", "18", "Datos demo"),
        SummaryItem("Simulacros abiertos", "6", "Datos demo"),
        SummaryItem("Alertas", "2", "Datos demo"),
    )
}
