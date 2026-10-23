package co.saber11preparate.app.ui.screens.auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import co.saber11preparate.app.R
import co.saber11preparate.app.core.AppDimens
import co.saber11preparate.app.core.AppSpacing
import co.saber11preparate.app.data.auth.FakeAuthRepository
import co.saber11preparate.app.domain.AuthError
import co.saber11preparate.app.domain.AuthRepository
import co.saber11preparate.app.domain.AuthResult
import co.saber11preparate.app.domain.AuthSession
import co.saber11preparate.app.ui.auth.authErrorMessage
import co.saber11preparate.app.ui.components.AppPrimaryButton
import co.saber11preparate.app.ui.components.AppTextField
import co.saber11preparate.app.ui.components.DemoModeBanner
import co.saber11preparate.app.ui.components.ResponsiveContent
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBack: () -> Unit,
    onForgotPassword: () -> Unit,
    onSignedIn: (AuthSession) -> Unit,
    modifier: Modifier = Modifier,
    authRepository: AuthRepository = FakeAuthRepository.getInstance(),
) {
    BackHandler(onBack = onBack)
    val scope = rememberCoroutineScope()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<AuthError?>(null) }
    var loading by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column {
                DemoModeBanner()
                TopAppBar(
                    title = { Text(stringResource(R.string.auth_sign_in)) },
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
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = stringResource(R.string.auth_demo_notice),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.Center,
                )
                AppTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        error = null
                    },
                    label = stringResource(R.string.auth_email),
                    enabled = !loading,
                    isError = error != null,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )
                AppTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        error = null
                    },
                    label = stringResource(R.string.auth_password),
                    enabled = !loading,
                    isError = error != null,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                error?.let {
                    Text(
                        text = authErrorMessage(it),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                AppPrimaryButton(
                    text = if (loading) {
                        stringResource(R.string.auth_please_wait)
                    } else {
                        stringResource(R.string.auth_sign_in)
                    },
                    enabled = !loading,
                    onClick = {
                        scope.launch {
                            loading = true
                            error = null
                            when (val result = authRepository.signIn(email, password)) {
                                is AuthResult.Success -> onSignedIn(result.session)
                                is AuthResult.Failure -> error = result.error
                                AuthResult.PasswordResetSimulated -> Unit
                            }
                            loading = false
                        }
                    },
                )
                TextButton(onClick = onForgotPassword, enabled = !loading) {
                    Text(stringResource(R.string.auth_forgot_password))
                }
                Spacer(modifier = Modifier.height(AppSpacing.sm))
            }
        }
    }
}
