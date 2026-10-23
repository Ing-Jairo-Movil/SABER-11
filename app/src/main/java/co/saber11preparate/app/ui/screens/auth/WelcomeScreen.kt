package co.saber11preparate.app.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import co.saber11preparate.app.R
import co.saber11preparate.app.core.AppDimens
import co.saber11preparate.app.core.AppSpacing
import co.saber11preparate.app.ui.components.AppPrimaryButton
import co.saber11preparate.app.ui.components.AppSecondaryButton
import co.saber11preparate.app.ui.components.DemoModeBanner
import co.saber11preparate.app.ui.components.ResponsiveContent

@Composable
fun WelcomeScreen(
    onSignInClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onExploreDemoClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.statusBarsPadding())
        DemoModeBanner()
        BoxWithConstraints(modifier = Modifier.weight(1f).fillMaxWidth()) {
            ResponsiveContent(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = maxHeight)
                    .verticalScroll(rememberScrollState())
                    .padding(
                        horizontal = AppDimens.screenPaddingHorizontal,
                        vertical = AppDimens.screenPaddingVertical,
                    ),
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.sm))
                    Text(
                        text = stringResource(R.string.home_tagline),
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.md))
                    Text(
                        text = stringResource(R.string.auth_demo_notice),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.tertiary,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.xl))
                    AppPrimaryButton(
                        text = stringResource(R.string.auth_sign_in),
                        onClick = onSignInClick,
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.sm))
                    AppSecondaryButton(
                        text = stringResource(R.string.auth_register),
                        onClick = onRegisterClick,
                    )
                    Spacer(modifier = Modifier.height(AppSpacing.md))
                    TextButton(onClick = onExploreDemoClick) {
                        Text(text = stringResource(R.string.auth_explore_demo_ui))
                    }
                }
            }
        }
    }
}
