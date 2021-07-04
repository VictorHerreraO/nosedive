package com.soyvictorherrera.nosedive.ui.content.signIn

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.ui.composable.button.MainButton
import com.soyvictorherrera.nosedive.ui.composable.form.EmailTextField
import com.soyvictorherrera.nosedive.ui.composable.form.PasswordTextField
import com.soyvictorherrera.nosedive.ui.composable.state.EmailState
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
    onNavigationEvent: (SignInEvent) -> Unit
) {
    NosediveTheme {
        Surface(color = MaterialTheme.colors.background) {
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
                        onSignInSubmitted = { email, password ->
                            onNavigationEvent(SignInEvent.SignIn(email, password))
                        },
                        onFormPositioned = { bounds -> emailTop = bounds.top }
                    )

                    // Forgot password button
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = {
                        onNavigationEvent(SignInEvent.ResetPassword)
                    }) {
                        Text(
                            text = stringResource(R.string.login_forgot_your_password).toUpperCase(
                                Locale.current
                            ),
                            color = Alto,
                            style = MaterialTheme.typography.button.copy(fontSize = 12.sp)
                        )
                    }

                    // Create account button
                    Spacer(modifier = Modifier.weight(0.75f))
                    TextButton(
                        onClick = {
                            onNavigationEvent(SignInEvent.SignUp)
                        }
                    ) {
                        Text(
                            text = stringResource(R.string.login_create_account).toUpperCase(
                                Locale.current
                            ),
                            color = Alto,
                            style = MaterialTheme.typography.button.copy(fontSize = 12.sp)
                        )
                    }
                }
            }
        }
    }
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
    onSignInSubmitted: (email: String, password: String) -> Unit,
    onFormPositioned: (bounds: Rect) -> Unit = {}
) {
    Column {
        // User email
        val errorTemplate = stringResource(id = R.string.login_invalid_email)
        val focusRequester = remember { FocusRequester() }
        val emailState = remember {
            EmailState(errorFor = { email ->
                String.format(errorTemplate, email)
            })
        }
        EmailTextField(
            emailState = emailState,
            modifier = Modifier.onGloballyPositioned {
                onFormPositioned(it.boundsInRoot())
            },
            onImeAction = {
                Log.d("onImeAction", "request focus")
                focusRequester.requestFocus()
            }
        )

        // User password
        Spacer(modifier = Modifier.height(16.dp))
        val passwordState = remember {
            PasswordState(errorFor = { password ->
                "${password.length}/4"
            })
        }
        PasswordTextField(
            label = stringResource(id = R.string.login_password),
            passwordState = passwordState,
            modifier = Modifier.focusRequester(focusRequester),
            onImeAction = {
                if (emailState.isValid && passwordState.isValid) {
                    onSignInSubmitted(emailState.text, passwordState.text)
                }
            }
        )

        // Login Button
        Spacer(modifier = Modifier.height(32.dp))
        MainButton(
            text = stringResource(R.string.login_do_login),
            onClick = { onSignInSubmitted(emailState.text, passwordState.text) },
            enabled = emailState.isValid && passwordState.isValid
        )
    }
}

@Preview(
    showBackground = true,
    name = "Sign in dark theme"
)
@Composable
fun SignInContentPreviewDark() {
    NosediveTheme {
        SignInContent {}
    }
}
