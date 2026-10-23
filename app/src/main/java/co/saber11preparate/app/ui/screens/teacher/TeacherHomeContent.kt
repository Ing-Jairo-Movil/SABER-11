package co.saber11preparate.app.ui.screens.teacher

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Task
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
fun TeacherHomeContent(
    onOpenGroups: () -> Unit,
    onOpenStudents: () -> Unit,
    onOpenActivities: () -> Unit,
    onOpenReports: () -> Unit,
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
                text = stringResource(R.string.teacher_greeting, DemoContent.TEACHER_NAME),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = DemoContent.TEACHER_SUBJECT,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = stringResource(R.string.teacher_panel_summary),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = AppSpacing.sm),
            )
            DemoContent.teacherSummaries.forEach { item ->
                SummaryMetricCard(
                    title = item.title,
                    value = item.value,
                    hint = item.hint,
                )
            }
            Text(
                text = stringResource(R.string.teacher_quick_access),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = AppSpacing.sm),
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_groups),
                subtitle = stringResource(R.string.teacher_groups_hint),
                icon = Icons.Filled.Groups,
                onClick = onOpenGroups,
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_students),
                subtitle = stringResource(R.string.teacher_students_hint),
                icon = Icons.Filled.People,
                onClick = onOpenStudents,
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_activities),
                subtitle = stringResource(R.string.teacher_activities_hint),
                icon = Icons.Filled.Task,
                onClick = onOpenActivities,
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_reports),
                subtitle = stringResource(R.string.teacher_reports_hint),
                icon = Icons.Filled.Assessment,
                onClick = onOpenReports,
            )
            Spacer(modifier = Modifier.height(AppSpacing.sm))
        }
    }
}
