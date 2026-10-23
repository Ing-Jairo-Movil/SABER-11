package co.saber11preparate.app.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.saber11preparate.app.core.AppDimens
import co.saber11preparate.app.core.AppSpacing
import co.saber11preparate.app.ui.theme.Saber11Theme

@Composable
fun SummaryMetricCard(
    title: String,
    value: String,
    hint: String,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Column(modifier = Modifier.padding(AppSpacing.md)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(top = AppSpacing.xs),
            )
            Text(
                text = hint,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.padding(top = AppSpacing.xs),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SummaryMetricCardPreview() {
    Saber11Theme {
        SummaryMetricCard(
            title = "Grupos",
            value = "3",
            hint = "Datos demo",
            modifier = Modifier.padding(AppDimens.screenPaddingHorizontal),
        )
    }
}
