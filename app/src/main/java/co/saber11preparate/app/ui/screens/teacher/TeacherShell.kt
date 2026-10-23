package co.saber11preparate.app.ui.screens.teacher

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Task
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.saber11preparate.app.R
import co.saber11preparate.app.navigation.TeacherTab
import co.saber11preparate.app.ui.components.RoleShellScaffold
import co.saber11preparate.app.ui.components.ShellNavItem
import co.saber11preparate.app.ui.screens.common.PlaceholderTabScreen

@Composable
fun TeacherShell(
    onExitDemo: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabNavController = rememberNavController()
    val backStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val selectedTab = TeacherTab.fromRoute(currentRoute)

    val navItems = listOf(
        ShellNavItem(
            route = TeacherTab.Home.route,
            label = stringResource(R.string.nav_home),
            icon = Icons.Filled.Home,
            contentDescription = stringResource(R.string.nav_home),
        ),
        ShellNavItem(
            route = TeacherTab.Groups.route,
            label = stringResource(R.string.nav_groups),
            icon = Icons.Filled.Groups,
            contentDescription = stringResource(R.string.nav_groups),
        ),
        ShellNavItem(
            route = TeacherTab.Students.route,
            label = stringResource(R.string.nav_students_short),
            icon = Icons.Filled.People,
            contentDescription = stringResource(R.string.nav_students),
        ),
        ShellNavItem(
            route = TeacherTab.Activities.route,
            label = stringResource(R.string.nav_activities_short),
            icon = Icons.Filled.Task,
            contentDescription = stringResource(R.string.nav_activities),
        ),
        ShellNavItem(
            route = TeacherTab.Reports.route,
            label = stringResource(R.string.nav_reports),
            icon = Icons.Filled.Assessment,
            contentDescription = stringResource(R.string.nav_reports),
        ),
    )

    fun navigateToTab(route: String) {
        tabNavController.navigate(route) {
            popUpTo(tabNavController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    RoleShellScaffold(
        title = stringResource(R.string.shell_teacher_title),
        navItems = navItems,
        selectedRoute = selectedTab.route,
        onNavItemSelected = ::navigateToTab,
        onExitDemo = onExitDemo,
        onSystemBack = {
            if (selectedTab != TeacherTab.Home) {
                navigateToTab(TeacherTab.Home.route)
            } else {
                onExitDemo()
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = TeacherTab.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            composable(TeacherTab.Home.route) {
                TeacherHomeContent(
                    onOpenGroups = { navigateToTab(TeacherTab.Groups.route) },
                    onOpenStudents = { navigateToTab(TeacherTab.Students.route) },
                    onOpenActivities = { navigateToTab(TeacherTab.Activities.route) },
                    onOpenReports = { navigateToTab(TeacherTab.Reports.route) },
                )
            }
            composable(TeacherTab.Groups.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_groups),
                    description = stringResource(R.string.teacher_groups_hint),
                )
            }
            composable(TeacherTab.Students.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_students),
                    description = stringResource(R.string.teacher_students_hint),
                )
            }
            composable(TeacherTab.Activities.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_activities),
                    description = stringResource(R.string.teacher_activities_hint),
                )
            }
            composable(TeacherTab.Reports.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_reports),
                    description = stringResource(R.string.teacher_reports_hint),
                )
            }
        }
    }
}
