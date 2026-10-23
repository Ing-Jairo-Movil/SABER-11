package co.saber11preparate.app.ui.screens.student

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Timer
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
import co.saber11preparate.app.navigation.StudentTab
import co.saber11preparate.app.ui.components.RoleShellScaffold
import co.saber11preparate.app.ui.components.ShellNavItem
import co.saber11preparate.app.ui.screens.common.PlaceholderTabScreen

@Composable
fun StudentShell(
    onExitDemo: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabNavController = rememberNavController()
    val backStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val selectedTab = StudentTab.fromRoute(currentRoute)

    val navItems = listOf(
        ShellNavItem(
            route = StudentTab.Home.route,
            label = stringResource(R.string.nav_home),
            icon = Icons.Filled.Home,
            contentDescription = stringResource(R.string.nav_home),
        ),
        ShellNavItem(
            route = StudentTab.Practice.route,
            label = stringResource(R.string.nav_practice),
            icon = Icons.Filled.Quiz,
            contentDescription = stringResource(R.string.nav_practice),
        ),
        ShellNavItem(
            route = StudentTab.Simulations.route,
            label = stringResource(R.string.nav_simulations_short),
            icon = Icons.Filled.Timer,
            contentDescription = stringResource(R.string.nav_simulations),
        ),
        ShellNavItem(
            route = StudentTab.Ranking.route,
            label = stringResource(R.string.nav_ranking),
            icon = Icons.Filled.EmojiEvents,
            contentDescription = stringResource(R.string.nav_ranking),
        ),
        ShellNavItem(
            route = StudentTab.Profile.route,
            label = stringResource(R.string.nav_profile),
            icon = Icons.Filled.Person,
            contentDescription = stringResource(R.string.nav_profile),
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
        title = stringResource(R.string.shell_student_title),
        navItems = navItems,
        selectedRoute = selectedTab.route,
        onNavItemSelected = ::navigateToTab,
        onExitDemo = onExitDemo,
        onSystemBack = {
            if (selectedTab != StudentTab.Home) {
                navigateToTab(StudentTab.Home.route)
            } else {
                onExitDemo()
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = StudentTab.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            composable(StudentTab.Home.route) {
                StudentHomeContent(
                    onOpenPractice = { navigateToTab(StudentTab.Practice.route) },
                    onOpenSimulations = { navigateToTab(StudentTab.Simulations.route) },
                    onOpenRanking = { navigateToTab(StudentTab.Ranking.route) },
                    onOpenProfile = { navigateToTab(StudentTab.Profile.route) },
                )
            }
            composable(StudentTab.Practice.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_practice),
                    description = stringResource(R.string.student_practice_hint),
                )
            }
            composable(StudentTab.Simulations.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_simulations),
                    description = stringResource(R.string.student_simulations_hint),
                )
            }
            composable(StudentTab.Ranking.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_ranking),
                    description = stringResource(R.string.student_ranking_hint),
                )
            }
            composable(StudentTab.Profile.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_profile),
                    description = stringResource(R.string.student_profile_hint),
                )
            }
        }
    }
}
