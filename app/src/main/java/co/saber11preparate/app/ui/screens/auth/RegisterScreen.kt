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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import co.saber11preparate.app.domain.SaberArea
import co.saber11preparate.app.domain.SchoolGrade
import co.saber11preparate.app.domain.UserRole
import co.saber11preparate.app.ui.auth.authErrorMessage
import co.saber11preparate.app.ui.components.AppPrimaryButton
import co.saber11preparate.app.ui.components.AppTextField
import co.saber11preparate.app.ui.components.DemoModeBanner
import co.saber11preparate.app.ui.components.ResponsiveContent
import kotlinx.coroutines.launch

private enum class PublicRegisterRole { STUDENT, TEACHER }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    onBack: () -> Unit,
    onRegistered: (AuthSession) -> Unit,
    modifier: Modifier = Modifier,
    authRepository: AuthRepository = FakeAuthRepository.getInstance(),
) {
    BackHandler(onBack = onBack)
    val scope = rememberCoroutineScope()
    var roleIndex by remember { mutableIntStateOf(0) }
    var displayName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var grade by remember { mutableStateOf<SchoolGrade?>(null) }
    var area by remember { mutableStateOf<SaberArea?>(null) }
    var error by remember { mutableStateOf<AuthError?>(null) }
    var loading by remember { mutableStateOf(false) }

    val publicRole = if (roleIndex == 0) PublicRegisterRole.STUDENT else PublicRegisterRole.TEACHER

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column {
                DemoModeBanner()
                TopAppBar(
                    title = { Text(stringResource(R.string.auth_register)) },
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
                    text = stringResource(R.string.auth_demo_notice),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.tertiary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                Text(
                    text = stringResource(R.string.auth_register_role_hint),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                SingleChoiceSegmentedButtonRow(modifier = Modifier.fillMaxWidth()) {
                    SegmentedButton(
                        selected = roleIndex == 0,
                        onClick = {
                            roleIndex = 0
                            error = null
                        },
                        shape = SegmentedButtonDefaults.itemShape(index = 0, count = 2),
                        enabled = !loading,
                        label = { Text(stringResource(R.string.shell_student_title)) },
                    )
                    SegmentedButton(
                        selected = roleIndex == 1,
                        onClick = {
                            roleIndex = 1
                            error = null
                        },
                        shape = SegmentedButtonDefaults.itemShape(index = 1, count = 2),
                        enabled = !loading,
                        label = { Text(stringResource(R.string.shell_teacher_title)) },
                    )
                }
                Text(
                    text = stringResource(R.string.auth_no_public_admin),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )

                AppTextField(
                    value = displayName,
                    onValueChange = {
                        displayName = it
                        error = null
                    },
                    label = stringResource(R.string.auth_display_name),
                    enabled = !loading,
                )
                AppTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        error = null
                    },
                    label = stringResource(R.string.auth_email),
                    enabled = !loading,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                )

                when (publicRole) {
                    PublicRegisterRole.STUDENT -> {
                        Text(
                            text = stringResource(R.string.auth_school_grade),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        SchoolGrade.entries.forEach { option ->
                            FilterChip(
                                selected = grade == option,
                                onClick = {
                                    grade = option
                                    error = null
                                },
                                enabled = !loading,
                                label = { Text(option.displayName) },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                    PublicRegisterRole.TEACHER -> {
                        Text(
                            text = stringResource(R.string.auth_teaching_area),
                            style = MaterialTheme.typography.titleMedium,
                        )
                        SaberArea.entries.forEach { option ->
                            FilterChip(
                                selected = area == option,
                                onClick = {
                                    area = option
                                    error = null
                                },
                                enabled = !loading,
                                label = { Text(option.displayName) },
                                modifier = Modifier.fillMaxWidth(),
                            )
                        }
                    }
                }

                AppTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        error = null
                    },
                    label = stringResource(R.string.auth_password),
                    enabled = !loading,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )
                AppTextField(
                    value = confirmPassword,
                    onValueChange = {
                        confirmPassword = it
                        error = null
                    },
                    label = stringResource(R.string.auth_confirm_password),
                    enabled = !loading,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                )

                error?.let {
                    Text(
                        text = authErrorMessage(it),
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }

                AppPrimaryButton(
                    text = if (loading) {
                        stringResource(R.string.auth_please_wait)
                    } else {
                        stringResource(R.string.auth_create_account)
                    },
                    enabled = !loading,
                    onClick = {
                        scope.launch {
                            loading = true
                            error = null
                            val result = when (publicRole) {
                                PublicRegisterRole.STUDENT -> authRepository.registerStudent(
                                    displayName = displayName,
                                    email = email,
                                    grade = grade,
                                    password = password,
                                    confirmPassword = confirmPassword,
                                )
                                PublicRegisterRole.TEACHER -> authRepository.registerTeacher(
                                    displayName = displayName,
                                    email = email,
                                    area = area,
                                    password = password,
                                    confirmPassword = confirmPassword,
                                )
                            }
                            when (result) {
                                is AuthResult.Success -> {
                                    if (result.session.user.role == UserRole.ADMIN) {
                                        error = AuthError.AdminRegistrationForbidden
                                        authRepository.signOut()
                                    } else {
                                        onRegistered(result.session)
                                    }
                                }
                                is AuthResult.Failure -> error = result.error
                                AuthResult.PasswordResetSimulated -> Unit
                            }
                            loading = false
                        }
                    },
                )
            }
        }
    }
}
