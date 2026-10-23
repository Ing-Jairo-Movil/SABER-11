package co.saber11preparate.app.ui.screens.admin

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
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
import co.saber11preparate.app.navigation.AdminTab
import co.saber11preparate.app.ui.components.RoleShellScaffold
import co.saber11preparate.app.ui.components.ShellNavItem
import co.saber11preparate.app.ui.screens.common.PlaceholderTabScreen

@Composable
fun AdminShell(
    onExitDemo: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabNavController = rememberNavController()
    val backStackEntry by tabNavController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route
    val selectedTab = AdminTab.fromRoute(currentRoute)

    val navItems = listOf(
        ShellNavItem(
            route = AdminTab.Home.route,
            label = stringResource(R.string.nav_home),
            icon = Icons.Filled.Home,
            contentDescription = stringResource(R.string.nav_home),
        ),
        ShellNavItem(
            route = AdminTab.Users.route,
            label = stringResource(R.string.nav_users),
            icon = Icons.Filled.ManageAccounts,
            contentDescription = stringResource(R.string.nav_users),
        ),
        ShellNavItem(
            route = AdminTab.Academic.route,
            label = stringResource(R.string.nav_academic),
            icon = Icons.Filled.School,
            contentDescription = stringResource(R.string.nav_academic),
        ),
        ShellNavItem(
            route = AdminTab.Stats.route,
            label = stringResource(R.string.nav_stats_short),
            icon = Icons.Filled.BarChart,
            contentDescription = stringResource(R.string.nav_stats),
        ),
        ShellNavItem(
            route = AdminTab.Settings.route,
            label = stringResource(R.string.nav_settings_short),
            icon = Icons.Filled.Settings,
            contentDescription = stringResource(R.string.nav_settings),
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
        title = stringResource(R.string.shell_admin_title),
        navItems = navItems,
        selectedRoute = selectedTab.route,
        onNavItemSelected = ::navigateToTab,
        onExitDemo = onExitDemo,
        onSystemBack = {
            if (selectedTab != AdminTab.Home) {
                navigateToTab(AdminTab.Home.route)
            } else {
                onExitDemo()
            }
        },
        modifier = modifier,
    ) { innerPadding ->
        NavHost(
            navController = tabNavController,
            startDestination = AdminTab.Home.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            composable(AdminTab.Home.route) {
                AdminHomeContent(
                    onOpenUsers = { navigateToTab(AdminTab.Users.route) },
                    onOpenAcademic = { navigateToTab(AdminTab.Academic.route) },
                    onOpenStats = { navigateToTab(AdminTab.Stats.route) },
                    onOpenSettings = { navigateToTab(AdminTab.Settings.route) },
                )
            }
            composable(AdminTab.Users.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_users),
                    description = stringResource(R.string.admin_users_hint),
                )
            }
            composable(AdminTab.Academic.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_academic),
                    description = stringResource(R.string.admin_academic_hint),
                )
            }
            composable(AdminTab.Stats.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_stats),
                    description = stringResource(R.string.admin_stats_hint),
                )
            }
            composable(AdminTab.Settings.route) {
                PlaceholderTabScreen(
                    title = stringResource(R.string.nav_settings),
                    description = stringResource(R.string.admin_settings_hint),
                )
            }
        }
    }
}
