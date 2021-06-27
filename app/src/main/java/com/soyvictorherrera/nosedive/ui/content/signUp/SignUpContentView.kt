package com.soyvictorherrera.nosedive.ui.content.signUp

import android.text.Annotation
import android.text.SpannedString
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.ui.composable.common.DefaultProminentTopAppBar
import com.soyvictorherrera.nosedive.ui.theme.Alto
import com.soyvictorherrera.nosedive.ui.theme.Dove_Gray
import com.soyvictorherrera.nosedive.ui.theme.NosediveTheme
import com.soyvictorherrera.nosedive.ui.theme.Wild_Watermelon

@Composable
fun SignUpContent() {
    NosediveTheme {
        Surface(color = MaterialTheme.colors.background) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                DefaultProminentTopAppBar(
                    title = stringResource(R.string.signup_title),
                    navigationIcon = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(Icons.Sharp.ArrowBack, contentDescription = null)
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                ) {
                    // User name
                    Spacer(modifier = Modifier.height(16.dp))
                    var username by rememberSaveable { mutableStateOf("") }
                    TextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text(stringResource(R.string.signup_name)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    // User email
                    Spacer(modifier = Modifier.height(16.dp))
                    var usermail by rememberSaveable { mutableStateOf("") }
                    TextField(
                        value = usermail,
                        onValueChange = { usermail = it },
                        label = { Text(stringResource(R.string.login_user_email)) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
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
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(
                            start = 32.dp,
                            end = 32.dp,
                            bottom = 64.dp
                        ),
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Registrarme".toUpperCase(Locale.current),
                            color = Color.White
                        )
                    }

                    val termsAndConditions =
                        LocalContext.current.resources.getText(R.string.signup_terms_and_conditions) as SpannedString
                    val annotations = termsAndConditions.getSpans(
                        0,
                        termsAndConditions.length,
                        Annotation::class.java
                    )
                    val annotatedString by remember {
                        mutableStateOf(with(AnnotatedString.Builder()) {
                            Log.d("AnnotatedString", "building")
                            append(termsAndConditions.toString())
                            for (annotation in annotations) {
                                if (annotation.key == "target") {
                                    val annotationStart =
                                        termsAndConditions.getSpanStart(annotation)
                                    val annotationEnd = termsAndConditions.getSpanEnd(annotation)
                                    addStyle(
                                        style = SpanStyle(
                                            color = Wild_Watermelon,
                                            textDecoration = TextDecoration.Underline
                                        ),
                                        start = annotationStart,
                                        end = annotationEnd
                                    )
                                    val target = annotation.value
                                    Log.d("AnnotatedString", "target = $target")
                                    addStringAnnotation(
                                        tag = "URL",
                                        annotation = "https://soyvictorherrera.com/?target=$target",
                                        start = annotationStart,
                                        end = annotationEnd
                                    )
                                }
                            }
                            toAnnotatedString()
                        })
                    }
                    val uriHandler = LocalUriHandler.current

                    Spacer(modifier = Modifier.height(16.dp))
                    ClickableText(
                        text = annotatedString,
                        modifier = Modifier.fillMaxWidth(),
                        style = MaterialTheme.typography.body2.copy(
                            color = Dove_Gray,
                            fontSize = 12.sp
                        ),
                        onClick = { offset ->
                            annotatedString.getStringAnnotations("URL", offset, offset)
                                .firstOrNull()?.let { annotation ->
                                    Log.d("OnClick", annotation.item)
                                    uriHandler.openUri(annotation.item)
                                }
                        }
                    )

                    // Have an account button
                    Spacer(modifier = Modifier.height(32.dp))
                    TextButton(
                        onClick = { /*TODO*/ }
                    ) {
                        Spacer(modifier = Modifier.height(32.dp))
                        Text(
                            text = stringResource(R.string.signup_login).toUpperCase(
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

@Preview
@Composable
fun SignUpContentPreview() {
    SignUpContent()
}
