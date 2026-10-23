package co.saber11preparate.app.ui.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import co.saber11preparate.app.R

data class ShellNavItem(
    val route: String,
    val label: String,
    val icon: ImageVector,
    val contentDescription: String = label,
)

private val CompactNavBreakpoint = 600.dp

/**
 * Estructura visual compartida de un shell de rol:
 * banner demo, top bar, contenido y navegación inferior o lateral según el ancho.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoleShellScaffold(
    title: String,
    navItems: List<ShellNavItem>,
    selectedRoute: String,
    onNavItemSelected: (String) -> Unit,
    onExitDemo: () -> Unit,
    onSystemBack: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable (PaddingValues) -> Unit,
) {
    BackHandler(onBack = onSystemBack)

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val useNavigationRail = maxWidth >= CompactNavBreakpoint

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                Column {
                    DemoModeBanner()
                    TopAppBar(
                        title = {
                            Text(
                                text = title,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        },
                        navigationIcon = {
                            IconButton(onClick = onExitDemo) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.action_exit_demo),
                                )
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                            titleContentColor = MaterialTheme.colorScheme.onSurface,
                        ),
                    )
                }
            },
            bottomBar = {
                if (!useNavigationRail) {
                    CompactNavigationBar(
                        navItems = navItems,
                        selectedRoute = selectedRoute,
                        onNavItemSelected = onNavItemSelected,
                    )
                }
            },
        ) { innerPadding ->
            if (useNavigationRail) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                ) {
                    NavigationRail(modifier = Modifier.fillMaxHeight()) {
                        navItems.forEach { item ->
                            NavigationRailItem(
                                selected = item.route == selectedRoute,
                                onClick = { onNavItemSelected(item.route) },
                                icon = {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.contentDescription,
                                    )
                                },
                                label = {
                                    Text(
                                        text = item.label,
                                        style = MaterialTheme.typography.labelMedium,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                    )
                                },
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                    ) {
                        // Padding del Scaffold ya aplicado al Row.
                        content(PaddingValues(0.dp))
                    }
                }
            } else {
                content(innerPadding)
            }
        }
    }
}

@Composable
private fun CompactNavigationBar(
    navItems: List<ShellNavItem>,
    selectedRoute: String,
    onNavItemSelected: (String) -> Unit,
) {
    NavigationBar {
        navItems.forEach { item ->
            val selected = item.route == selectedRoute
            NavigationBarItem(
                selected = selected,
                onClick = { onNavItemSelected(item.route) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.contentDescription,
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        style = MaterialTheme.typography.labelSmall,
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                    )
                },
                alwaysShowLabel = true,
            )
        }
    }
}
