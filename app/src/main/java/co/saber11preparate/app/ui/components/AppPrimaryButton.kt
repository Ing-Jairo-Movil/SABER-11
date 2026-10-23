package co.saber11preparate.app.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import co.saber11preparate.app.core.AppDimens
import co.saber11preparate.app.core.AppSpacing
import co.saber11preparate.app.ui.theme.Saber11Theme

@Composable
fun AppPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = AppDimens.buttonHeight)
            .defaultMinSize(minHeight = AppDimens.minTouchTarget),
        contentPadding = PaddingValues(
            horizontal = AppSpacing.lg,
            vertical = AppSpacing.md,
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        shape = MaterialTheme.shapes.medium,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            maxLines = 2,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPrimaryButtonPreview() {
    Saber11Theme {
        AppPrimaryButton(text = "Continuar", onClick = {})
    }
}
