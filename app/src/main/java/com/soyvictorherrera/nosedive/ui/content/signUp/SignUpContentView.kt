package com.soyvictorherrera.nosedive.ui.content.signUp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.Annotation
import android.text.SpannedString
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.ui.composable.button.ActionButton
import com.soyvictorherrera.nosedive.ui.composable.button.MainButton
import com.soyvictorherrera.nosedive.ui.composable.common.DefaultProminentTopAppBar
import com.soyvictorherrera.nosedive.ui.composable.form.EmailTextField
import com.soyvictorherrera.nosedive.ui.composable.form.NameTextField
import com.soyvictorherrera.nosedive.ui.composable.form.PasswordTextField
import com.soyvictorherrera.nosedive.ui.composable.state.*
import com.soyvictorherrera.nosedive.ui.theme.Dove_Gray
import com.soyvictorherrera.nosedive.ui.theme.NosediveTheme
import com.soyvictorherrera.nosedive.ui.theme.Wild_Watermelon

private const val BASE_URL = "https://soyvictorherrera.com/?target="
private const val TAG_URL = "URL"

sealed class SignUpEvent {
    data class SignUp(val name: String, val email: String, val password: String) : SignUpEvent()
    object SignIn : SignUpEvent()
    object NavigateBack : SignUpEvent()
}

@Composable
fun SignUpContent(
    signUpState: SignUpState = SignUpState.Idle,
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onNavigationEvent: (SignUpEvent) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            DefaultProminentTopAppBar(
                title = stringResource(R.string.signup_title),
                navigationIcon = {
                    IconButton(onClick = { onNavigationEvent(SignUpEvent.NavigateBack) }) {
                        Icon(Icons.Sharp.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .padding(bottom = 64.dp)
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Form
                SignUpForm { nameState, emailState, passwordState ->
                    Spacer(modifier = Modifier.weight(1f))

                    // Actions
                    SignUpActions(
                        nameState = nameState,
                        emailState = emailState,
                        passwordState = passwordState,
                        isLoading = (signUpState == SignUpState.Loading),
                        onSignUpSubmitted = { name, email, password ->
                            onNavigationEvent(
                                SignUpEvent.SignUp(
                                    name = name, email = email, password = password
                                )
                            )
                        },
                        onCancelSignUp = {
                            onNavigationEvent(SignUpEvent.SignIn)
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun SignUpForm(
    formActions: @Composable (
        nameState: NameState,
        emailState: EmailState,
        passwordState: PasswordState
    ) -> Unit
) {
    Column {
        val context = LocalContext.current

        val nameState = remember {
            NameState(errorFor = { name ->
                when {
                    name.isEmpty() -> context.getString(R.string.login_required_field)
                    name.length > MAX_NAME_LENGTH -> context.getString(R.string.login_name_too_long)
                    else -> ""
                }
            })
        }
        val emailFocusRequester = remember { FocusRequester() }
        NameTextField(
            nameState = nameState,
            imeAction = ImeAction.Next,
            onImeAction = { emailFocusRequester.requestFocus() }
        )


        // User email
        Spacer(modifier = Modifier.height(16.dp))
        val emailState = remember {
            EmailState(errorFor = { email ->
                when {
                    email.isEmpty() -> context.getString(R.string.login_required_field)
                    else -> context.getString(R.string.login_invalid_email, email)
                }
            })
        }
        val passwordFocusRequester = remember { FocusRequester() }
        EmailTextField(
            emailState = emailState,
            modifier = Modifier.focusRequester(emailFocusRequester),
            onImeAction = {
                passwordFocusRequester.requestFocus()
            }
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
            modifier = Modifier.focusRequester(passwordFocusRequester),
            onImeAction = { focusManager.clearFocus() }
        )

        // Form actions
        formActions(nameState, emailState, passwordState)
    }
}

@Composable
fun SignUpActions(
    nameState: NameState,
    emailState: EmailState,
    passwordState: PasswordState,
    isLoading: Boolean = false,
    onSignUpSubmitted: (name: String, email: String, password: String) -> Unit,
    onCancelSignUp: () -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Register button
        MainButton(
            text = stringResource(R.string.login_sign_up),
            onClick = {
                onSignUpSubmitted(nameState.text, emailState.text, passwordState.text)
            },
            enabled = nameState.isValid && emailState.isValid && passwordState.isValid,
            showLoader = isLoading
        )

        // Policy and terms
        Spacer(modifier = Modifier.height(16.dp))
        UserAgreement()

        // Have an account button
        Spacer(modifier = Modifier.height(32.dp))
        ActionButton(
            text = stringResource(R.string.signup_login),
            onClick = { onCancelSignUp() }
        )
    }
}

@Composable
fun UserAgreement() {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val context = LocalContext.current
        val termsAndConditions =
            context.resources.getText(R.string.signup_terms_and_conditions) as SpannedString
        val annotatedString = remember { spannedToAnnotatedString(termsAndConditions) }

        ClickableText(
            text = annotatedString,
            modifier = Modifier.fillMaxWidth(),
            style = MaterialTheme.typography.body2.copy(
                color = Dove_Gray,
                fontSize = 12.sp
            ),
            onClick = { offset ->
                annotatedString.getStringAnnotations(TAG_URL, offset, offset)
                    .firstOrNull()?.let { annotation ->
                        openUrl(context = context, url = annotation.item)
                    }
            }
        )
    }
}

private fun spannedToAnnotatedString(spanned: SpannedString): AnnotatedString {
    val annotations = spanned.getSpans(
        0,
        spanned.length,
        Annotation::class.java
    )
    return with(AnnotatedString.Builder()) {
        append(spanned.toString())
        for (annotation in annotations) {
            if (annotation.key == "target") {
                val annotationStart =
                    spanned.getSpanStart(annotation)
                val annotationEnd = spanned.getSpanEnd(annotation)
                addStyle(
                    style = SpanStyle(
                        color = Wild_Watermelon,
                        textDecoration = TextDecoration.Underline
                    ),
                    start = annotationStart,
                    end = annotationEnd
                )
                val target = annotation.value
                addStringAnnotation(
                    tag = TAG_URL,
                    annotation = BASE_URL + target,
                    start = annotationStart,
                    end = annotationEnd
                )
            }
        }
        toAnnotatedString()
    }
}

private fun openUrl(context: Context, url: String) =
    context.startActivity(Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(url)
    })

@Preview(
    showBackground = true,
    name = "Sign up dark theme"
)
@Composable
fun SignUpContentPreview() {
    NosediveTheme {
        SignUpContent {}
    }
}
