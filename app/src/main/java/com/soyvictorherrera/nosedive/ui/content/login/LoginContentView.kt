package com.soyvictorherrera.nosedive.ui.content.login

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

@Composable
fun LoginActivityContent() {
    NosediveTheme {
        Surface(color = MaterialTheme.colors.background) {
            Box(modifier = Modifier.fillMaxWidth()) {
                var emailTop: Float? by remember { mutableStateOf(null) }
                // Background
                Canvas(modifier = Modifier.fillMaxSize()) {
                    emailTop?.let { top ->
                        val radius = this.size.width.times(1.3f).div(2f)
                        val yOffset = top.minus(16.dp.toPx()).minus(radius)
                        drawCircle(
                            color = Color.White,
                            alpha = 0.05f,
                            radius = radius,
                            center = this.center.copy(y = yOffset)
                        )
                    }
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
                    // User email
                    Spacer(modifier = Modifier.weight(1f))
                    var username by rememberSaveable { mutableStateOf("") }
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text(stringResource(R.string.login_user_email)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .onGloballyPositioned { emailTop = it.boundsInRoot().top }
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
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.login_do_login).toUpperCase(Locale.current),
                            color = Color.White
                        )
                    }
                    // Forgot password button
                    Spacer(modifier = Modifier.height(8.dp))
                    TextButton(onClick = { /*TODO*/ }) {
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
                        onClick = { /*TODO*/ }
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

@Preview(showBackground = true)
@Composable
fun LoginActivityPreview() {
    NosediveTheme {
        LoginActivityContent()
    }
}
