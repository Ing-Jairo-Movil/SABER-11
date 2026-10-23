package co.saber11preparate.app.ui.screens.auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import co.saber11preparate.app.R
import co.saber11preparate.app.core.AppDimens
import co.saber11preparate.app.core.AppSpacing
import co.saber11preparate.app.data.auth.FakeAuthRepository
import co.saber11preparate.app.domain.AuthError
import co.saber11preparate.app.domain.AuthRepository
import co.saber11preparate.app.domain.AuthResult
import co.saber11preparate.app.ui.auth.authErrorMessage
import co.saber11preparate.app.ui.components.AppPrimaryButton
import co.saber11preparate.app.ui.components.AppTextField
import co.saber11preparate.app.ui.components.DemoModeBanner
import co.saber11preparate.app.ui.components.ResponsiveContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForgotPasswordScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    authRepository: AuthRepository = FakeAuthRepository.getInstance(),
) {
    BackHandler(onBack = onBack)
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<AuthError?>(null) }
    var simulatedAck by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column {
                DemoModeBanner()
                TopAppBar(
                    title = { Text(stringResource(R.string.auth_forgot_password)) },
                    navigationIcon = {
                        TextButton(onClick = onBack, enabled = !loading) {
                            Text(stringResource(R.string.action_back))
                        }
                    },
                )
            }
        },
    ) { innerPadding ->
        ResponsiveContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(
                    horizontal = AppDimens.screenPaddingHorizontal,
                    vertical = AppDimens.screenPaddingVertical,
                ),
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppSpacing.md),
            ) {
                Text(
                    text = stringResource(R.string.auth_forgot_password_intro),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                AppTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        error = null
                        simulatedAck = false
                    },
                    label = stringResource(R.string.auth_email),
                    enabled = !loading,
                    isError = error != null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
                error?.let {
                    Text(
                        text = authErrorMessage(it),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                if (simulatedAck) {
                    Text(
                        text = stringResource(R.string.auth_forgot_password_simulated),
                        color = MaterialTheme.colorScheme.tertiary,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
                AppPrimaryButton(
                    text = if (loading) {
                        stringResource(R.string.auth_please_wait)
                    } else {
                        stringResource(R.string.auth_send_reset_demo)
                    },
                    enabled = !loading,
                    onClick = {
                        scope.launch {
                            loading = true
                            error = null
                            simulatedAck = false
                            when (val result = authRepository.requestPasswordReset(email)) {
                                AuthResult.PasswordResetSimulated -> simulatedAck = true
                                is AuthResult.Failure -> error = result.error
                                is AuthResult.Success -> Unit
                            }
                            loading = false
                        }
                    },
                )
                TextButton(onClick = onBack, enabled = !loading) {
                    Text(stringResource(R.string.auth_back_to_sign_in))
                }
            }
        }
    }
}
