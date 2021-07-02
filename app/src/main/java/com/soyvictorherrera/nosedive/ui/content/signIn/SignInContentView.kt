package com.soyvictorherrera.nosedive.ui.content.signIn

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.sharp.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
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
//    Surface(color = MaterialTheme.colors.background) {
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
    //}
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
        var usermail by rememberSaveable { mutableStateOf("") }
        TextField(
            value = usermail,
            onValueChange = { usermail = it },
            label = { Text(stringResource(R.string.login_user_email)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { onFormPositioned(it.boundsInRoot()) }
        )

        // User password
        Spacer(modifier = Modifier.height(16.dp))
        var password by rememberSaveable { mutableStateOf("") }
        var passwordHidden by rememberSaveable { mutableStateOf(true) }
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(stringResource(R.string.login_password)) },
            visualTransformation =
            if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { passwordHidden = !passwordHidden }) {
                    val visibilityIcon =
                        if (passwordHidden) Icons.Filled.Visibility else Icons.Sharp.VisibilityOff
                    val description =
                        if (passwordHidden) stringResource(R.string.login_show_password) else stringResource(
                            R.string.login_hide_password
                        )
                    Icon(imageVector = visibilityIcon, contentDescription = description)
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        // Login Button
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                onSignInSubmitted(usermail, password)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.login_do_login).toUpperCase(Locale.current),
                color = Color.White
            )
        }
    }
}

@Preview(name= "Sign in dark theme")
@Composable
fun SignInContentPreviewDark() {
    NosediveTheme {
        SignInContent {}
    }
}
