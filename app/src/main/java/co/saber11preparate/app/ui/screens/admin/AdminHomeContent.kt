package co.saber11preparate.app.ui.screens.admin

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BarChart
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.saber11preparate.app.R
import co.saber11preparate.app.core.AppDimens
import co.saber11preparate.app.core.AppSpacing
import co.saber11preparate.app.ui.components.FeatureAccessCard
import co.saber11preparate.app.ui.components.ResponsiveContent
import co.saber11preparate.app.ui.components.SummaryMetricCard
import co.saber11preparate.app.ui.demo.DemoContent

@Composable
fun AdminHomeContent(
    onOpenUsers: () -> Unit,
    onOpenAcademic: () -> Unit,
    onOpenStats: () -> Unit,
    onOpenSettings: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ResponsiveContent(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(
                horizontal = AppDimens.screenPaddingHorizontal,
                vertical = AppDimens.screenPaddingVertical,
            ),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(AppSpacing.md)) {
            Text(
                text = stringResource(R.string.admin_greeting, DemoContent.ADMIN_NAME),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = stringResource(R.string.admin_panel_summary),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = AppSpacing.sm),
            )
            DemoContent.adminSummaries.forEach { item ->
                SummaryMetricCard(
                    title = item.title,
                    value = item.value,
                    hint = item.hint,
                )
            }
            Text(
                text = stringResource(R.string.admin_quick_access),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = AppSpacing.sm),
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_users),
                subtitle = stringResource(R.string.admin_users_hint),
                icon = Icons.Filled.ManageAccounts,
                onClick = onOpenUsers,
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_academic),
                subtitle = stringResource(R.string.admin_academic_hint),
                icon = Icons.Filled.School,
                onClick = onOpenAcademic,
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_stats),
                subtitle = stringResource(R.string.admin_stats_hint),
                icon = Icons.Filled.BarChart,
                onClick = onOpenStats,
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_settings),
                subtitle = stringResource(R.string.admin_settings_hint),
                icon = Icons.Filled.Settings,
                onClick = onOpenSettings,
            )
            Spacer(modifier = Modifier.height(AppSpacing.sm))
        }
    }
}
