package com.soyvictorherrera.nosedive.ui.content.signIn

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.ui.composable.button.ActionButton
import com.soyvictorherrera.nosedive.ui.composable.button.MainButton
import com.soyvictorherrera.nosedive.ui.composable.form.EmailTextField
import com.soyvictorherrera.nosedive.ui.composable.form.PasswordTextField
import com.soyvictorherrera.nosedive.ui.composable.state.EmailState
import com.soyvictorherrera.nosedive.ui.composable.state.MIN_PASSWORD_LENGTH
import com.soyvictorherrera.nosedive.ui.composable.state.PasswordState
import com.soyvictorherrera.nosedive.ui.theme.Alto
import com.soyvictorherrera.nosedive.ui.theme.NosediveTheme

sealed class SignInEvent {
    data class SignIn(val email: String, val password: String) : SignInEvent()
    object SignUp : SignInEvent()
    object ResetPassword : SignInEvent()
}

@Composable
fun SignInContent(
    signInState: SignInState = SignInState.Idle,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onNavigationEvent: (SignInEvent) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        content = {
            Box(modifier = Modifier.fillMaxWidth()) {
                var emailTop: Float? by remember { mutableStateOf(null) }
                // Background
                emailTop?.let { top ->
                    SignInBackground(top)
                }
                // Content
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 32.dp,
                            top = 32.dp,
                            end = 32.dp,
                            bottom = 64.dp
                        ),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Greeting
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.login_greeting),
                        color = Alto,
                        style = MaterialTheme.typography.h1.copy(fontSize = 64.sp)
                    )

                    // Form
                    Spacer(modifier = Modifier.weight(1f))
                    SignInForm(
                        onFormPositioned = { bounds -> emailTop = bounds.top }
                    ) { emailState, passwordState ->
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val isLoading = signInState == SignInState.Loading

                            // Login button
                            MainButton(
                                text = if (isLoading) stringResource(R.string.login_signing_in)
                                else stringResource(R.string.login_do_login),
                                onClick = {
                                    onNavigationEvent(
                                        SignInEvent.SignIn(
                                            emailState.text,
                                            passwordState.text
                                        )
                                    )
                                },
                                enabled = !isLoading && (emailState.isValid && passwordState.isValid)
                            )

                            if (isLoading) {
                                Spacer(modifier = Modifier.height(8.dp))
                                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
                                Spacer(modifier = Modifier.height(8.dp))
                            }

                            // Forgot password button
                            Spacer(modifier = Modifier.height(8.dp))
                            ActionButton(
                                text = stringResource(id = R.string.login_forgot_your_password),
                                onClick = {
                                    onNavigationEvent(SignInEvent.ResetPassword)
                                }
                            )
                        }
                    }

                    // Create account button
                    Spacer(modifier = Modifier.weight(0.75f))
                    ActionButton(
                        text = stringResource(R.string.login_create_account),
                        onClick = { onNavigationEvent(SignInEvent.SignUp) }
                    )
                }
            }
        })
}

@Composable
fun SignInBackground(yCenter: Float = 0f) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val radius = this.size.width.times(1.3f).div(2f)
        val yOffset = yCenter.minus(16.dp.toPx()).minus(radius)
        drawCircle(
            color = Color.White,
            alpha = 0.05f,
            radius = radius,
            center = this.center.copy(y = yOffset)
        )
    }
}

@Composable
fun SignInForm(
    onFormPositioned: (bounds: Rect) -> Unit = {},
    formActions: @Composable (emailState: EmailState, passwordState: PasswordState) -> Unit
) {
    Column {
        // User email
        val context = LocalContext.current
        val focusRequester = remember { FocusRequester() }
        val emailState = remember {
            EmailState(errorFor = { email ->
                when {
                    email.isEmpty() -> context.getString(R.string.login_required_field)
                    else -> context.getString(R.string.login_invalid_email, email)
                }
            })
        }
        EmailTextField(
            emailState = emailState,
            modifier = Modifier.onGloballyPositioned {
                onFormPositioned(it.boundsInRoot())
            },
            onImeAction = { focusRequester.requestFocus() }
        )

        // User password
        Spacer(modifier = Modifier.height(16.dp))
        val passwordState = remember {
            PasswordState(errorFor = { password ->
                "${password.length}/$MIN_PASSWORD_LENGTH"
            })
        }
        val focusManager = LocalFocusManager.current
        PasswordTextField(
            label = stringResource(id = R.string.login_password),
            passwordState = passwordState,
            modifier = Modifier.focusRequester(focusRequester),
            onImeAction = {
                focusManager.clearFocus()
            }
        )

        // Form Actions
        Spacer(modifier = Modifier.height(32.dp))
        formActions(emailState, passwordState)
    }
}

@Preview(
    showBackground = true,
    name = "Sign in dark theme"
)
@Composable
fun SignInContentPreviewDark() {
    NosediveTheme {
        SignInContent(
            signInState = SignInState.Idle,
            onNavigationEvent = {}
        )
    }
}
