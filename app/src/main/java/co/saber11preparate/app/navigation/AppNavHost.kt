package co.saber11preparate.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.saber11preparate.app.ui.screens.admin.AdminShell
import co.saber11preparate.app.ui.screens.home.HomeScreen
import co.saber11preparate.app.ui.screens.student.StudentShell
import co.saber11preparate.app.ui.screens.teacher.TeacherShell

/**
 * Grafo raíz de navegación.
 * Los shells de rol se abren solo en modo demostración desde [HomeScreen].
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = AppRoute.Home.route,
) {
    fun exitRoleDemo() {
        val returnedToHome = navController.popBackStack(AppRoute.Home.route, inclusive = false)
        if (!returnedToHome) {
            navController.navigate(AppRoute.Home.route) {
                popUpTo(navController.graph.id) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }
    }

    fun openRole(route: String) {
        when (route) {
            AppRoute.Student.route,
            AppRoute.Teacher.route,
            AppRoute.Admin.route,
            -> navController.navigate(route) { launchSingleTop = true }

            else -> navController.navigate(AppRoute.UnknownFallback.route) {
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(AppRoute.Home.route) {
            HomeScreen(
                onOpenStudentDemo = { openRole(AppRoute.Student.route) },
                onOpenTeacherDemo = { openRole(AppRoute.Teacher.route) },
                onOpenAdminDemo = { openRole(AppRoute.Admin.route) },
            )
        }
        composable(AppRoute.Student.route) {
            StudentShell(onExitDemo = ::exitRoleDemo)
        }
        composable(AppRoute.Teacher.route) {
            TeacherShell(onExitDemo = ::exitRoleDemo)
        }
        composable(AppRoute.Admin.route) {
            AdminShell(onExitDemo = ::exitRoleDemo)
        }
        composable(AppRoute.UnknownFallback.route) {
            HomeScreen(
                onOpenStudentDemo = { openRole(AppRoute.Student.route) },
                onOpenTeacherDemo = { openRole(AppRoute.Teacher.route) },
                onOpenAdminDemo = { openRole(AppRoute.Admin.route) },
            )
        }
    }
}
