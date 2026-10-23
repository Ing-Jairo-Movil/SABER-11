package co.saber11preparate.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.saber11preparate.app.data.auth.FakeAuthRepository
import co.saber11preparate.app.domain.AuthRepository
import co.saber11preparate.app.domain.AuthSession
import co.saber11preparate.app.domain.UserRole
import co.saber11preparate.app.ui.screens.admin.AdminShell
import co.saber11preparate.app.ui.screens.auth.ForgotPasswordScreen
import co.saber11preparate.app.ui.screens.auth.LoginScreen
import co.saber11preparate.app.ui.screens.auth.RegisterScreen
import co.saber11preparate.app.ui.screens.auth.WelcomeScreen
import co.saber11preparate.app.ui.screens.home.HomeScreen
import co.saber11preparate.app.ui.screens.student.StudentShell
import co.saber11preparate.app.ui.screens.teacher.TeacherShell
import kotlinx.coroutines.launch

/**
 * Grafo raíz: autenticación demo + shells + hub de exploración UI.
 */
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    authRepository: AuthRepository = remember { FakeAuthRepository.getInstance() },
    startDestination: String = AppRoute.Welcome.route,
) {
    val scope = rememberCoroutineScope()

    fun navigateToRoleShell(session: AuthSession) {
        val target = when (session.user.role) {
            UserRole.STUDENT -> AppRoute.Student.route
            UserRole.TEACHER -> AppRoute.Teacher.route
            UserRole.ADMIN -> AppRoute.Admin.route
        }
        navController.navigate(target) {
            popUpTo(AppRoute.Welcome.route) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun openDemoShell(route: String) {
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

    fun exitDemoToHub() {
        val returned = navController.popBackStack(AppRoute.DemoHub.route, inclusive = false)
        if (!returned) {
            navController.navigate(AppRoute.DemoHub.route) {
                launchSingleTop = true
            }
        }
    }

    fun exitAuthenticatedSession() {
        scope.launch {
            authRepository.signOut()
            navController.navigate(AppRoute.Welcome.route) {
                popUpTo(navController.graph.id) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(AppRoute.Welcome.route) {
            WelcomeScreen(
                onSignInClick = { navController.navigate(AppRoute.Login.route) },
                onRegisterClick = { navController.navigate(AppRoute.Register.route) },
                onExploreDemoClick = { navController.navigate(AppRoute.DemoHub.route) },
            )
        }
        composable(AppRoute.Login.route) {
            LoginScreen(
                authRepository = authRepository,
                onBack = { navController.popBackStack() },
                onForgotPassword = { navController.navigate(AppRoute.ForgotPassword.route) },
                onSignedIn = ::navigateToRoleShell,
            )
        }
        composable(AppRoute.Register.route) {
            RegisterScreen(
                authRepository = authRepository,
                onBack = { navController.popBackStack() },
                onRegistered = ::navigateToRoleShell,
            )
        }
        composable(AppRoute.ForgotPassword.route) {
            ForgotPasswordScreen(
                authRepository = authRepository,
                onBack = { navController.popBackStack() },
            )
        }
        composable(AppRoute.DemoHub.route) {
            HomeScreen(
                onOpenStudentDemo = { openDemoShell(AppRoute.Student.route) },
                onOpenTeacherDemo = { openDemoShell(AppRoute.Teacher.route) },
                onOpenAdminDemo = { openDemoShell(AppRoute.Admin.route) },
                onBackToWelcome = { navController.popBackStack() },
            )
        }
        composable(AppRoute.Student.route) {
            val session = authRepository.currentSession()
            val authenticated = session?.user?.role == UserRole.STUDENT
            StudentShell(
                onExitDemo = {
                    if (authenticated) exitAuthenticatedSession() else exitDemoToHub()
                },
            )
        }
        composable(AppRoute.Teacher.route) {
            val session = authRepository.currentSession()
            val authenticated = session?.user?.role == UserRole.TEACHER
            TeacherShell(
                onExitDemo = {
                    if (authenticated) exitAuthenticatedSession() else exitDemoToHub()
                },
            )
        }
        composable(AppRoute.Admin.route) {
            // Solo accesible vía DemoHub; no hay registro/login público de admin.
            AdminShell(onExitDemo = ::exitDemoToHub)
        }
        composable(AppRoute.UnknownFallback.route) {
            WelcomeScreen(
                onSignInClick = {
                    navController.navigate(AppRoute.Login.route) {
                        popUpTo(AppRoute.Welcome.route) { inclusive = false }
                    }
                },
                onRegisterClick = {
                    navController.navigate(AppRoute.Register.route) {
                        popUpTo(AppRoute.Welcome.route) { inclusive = false }
                    }
                },
                onExploreDemoClick = {
                    navController.navigate(AppRoute.DemoHub.route)
                },
            )
        }
    }
}
