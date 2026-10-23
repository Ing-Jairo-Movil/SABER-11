package co.saber11preparate.app.ui.screens.student

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import co.saber11preparate.app.R
import co.saber11preparate.app.core.AppDimens
import co.saber11preparate.app.core.AppSpacing
import co.saber11preparate.app.ui.components.AreaProgressCard
import co.saber11preparate.app.ui.components.FeatureAccessCard
import co.saber11preparate.app.ui.components.ResponsiveContent
import co.saber11preparate.app.ui.demo.DemoContent

@Composable
fun StudentHomeContent(
    onOpenPractice: () -> Unit,
    onOpenSimulations: () -> Unit,
    onOpenRanking: () -> Unit,
    onOpenProfile: () -> Unit,
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
                text = stringResource(R.string.student_greeting, DemoContent.STUDENT_NAME),
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground,
            )
            Text(
                text = DemoContent.STUDENT_GRADE,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Text(
                text = stringResource(R.string.student_progress_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = AppSpacing.sm),
            )
            Text(
                text = stringResource(
                    R.string.student_progress_summary,
                    DemoContent.STUDENT_PROGRESS_PERCENT,
                    DemoContent.STUDENT_POINTS,
                    DemoContent.STUDENT_STREAK_DAYS,
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            LinearProgressIndicator(
                progress = { DemoContent.STUDENT_PROGRESS_PERCENT / 100f },
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )

            Text(
                text = stringResource(R.string.student_areas_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = AppSpacing.sm),
            )
            DemoContent.saberAreas.forEach { area ->
                AreaProgressCard(
                    areaName = area.name,
                    progressPercent = area.progressPercent,
                    levelLabel = area.levelLabel,
                )
            }

            Text(
                text = stringResource(R.string.student_quick_access),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(top = AppSpacing.sm),
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_practice),
                subtitle = stringResource(R.string.student_practice_hint),
                icon = Icons.Filled.Quiz,
                onClick = onOpenPractice,
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_simulations),
                subtitle = stringResource(R.string.student_simulations_hint),
                icon = Icons.Filled.Timer,
                onClick = onOpenSimulations,
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_ranking),
                subtitle = stringResource(R.string.student_ranking_hint),
                icon = Icons.Filled.EmojiEvents,
                onClick = onOpenRanking,
            )
            FeatureAccessCard(
                title = stringResource(R.string.nav_profile),
                subtitle = stringResource(R.string.student_profile_hint),
                icon = Icons.Filled.Person,
                onClick = onOpenProfile,
            )
            Spacer(modifier = Modifier.height(AppSpacing.sm))
        }
    }
}
