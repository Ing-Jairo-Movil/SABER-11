package co.saber11preparate.app.navigation

/**
 * Rutas tipadas de la aplicación.
 * Auth demo ≠ autorización de producción.
 */
sealed class AppRoute(val route: String) {
    data object Welcome : AppRoute("auth/welcome")
    data object Login : AppRoute("auth/login")
    data object Register : AppRoute("auth/register")
    data object ForgotPassword : AppRoute("auth/forgot_password")

    /** Selector de roles solo para explorar UI (sin sesión). */
    data object DemoHub : AppRoute("demo/hub")

    data object Student : AppRoute("student")
    data object Teacher : AppRoute("teacher")
    data object Admin : AppRoute("admin")
    data object UnknownFallback : AppRoute("unknown_fallback")
}

sealed class StudentTab(val route: String) {
    data object Home : StudentTab("student/home")
    data object Practice : StudentTab("student/practice")
    data object Simulations : StudentTab("student/simulations")
    data object Ranking : StudentTab("student/ranking")
    data object Profile : StudentTab("student/profile")

    companion object {
        val start = Home
        val items = listOf(Home, Practice, Simulations, Ranking, Profile)

        fun fromRoute(route: String?): StudentTab =
            items.firstOrNull { it.route == route } ?: Home
    }
}

sealed class TeacherTab(val route: String) {
    data object Home : TeacherTab("teacher/home")
    data object Groups : TeacherTab("teacher/groups")
    data object Students : TeacherTab("teacher/students")
    data object Activities : TeacherTab("teacher/activities")
    data object Reports : TeacherTab("teacher/reports")

    companion object {
        val start = Home
        val items = listOf(Home, Groups, Students, Activities, Reports)

        fun fromRoute(route: String?): TeacherTab =
            items.firstOrNull { it.route == route } ?: Home
    }
}

sealed class AdminTab(val route: String) {
    data object Home : AdminTab("admin/home")
    data object Users : AdminTab("admin/users")
    data object Academic : AdminTab("admin/academic")
    data object Stats : AdminTab("admin/stats")
    data object Settings : AdminTab("admin/settings")

    companion object {
        val start = Home
        val items = listOf(Home, Users, Academic, Stats, Settings)

        fun fromRoute(route: String?): AdminTab =
            items.firstOrNull { it.route == route } ?: Home
    }
}
