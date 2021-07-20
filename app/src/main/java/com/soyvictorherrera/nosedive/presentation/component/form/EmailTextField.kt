package com.soyvictorherrera.nosedive.presentation.component.form

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.component.state.TextFieldState

@Composable
fun EmailTextField(
    emailState: TextFieldState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    enabled: Boolean = true,
    onImeAction: () -> Unit
) {
    TextField(
        value = emailState.text,
        onValueChange = { emailState.text = it },
        label = { Text(stringResource(id = R.string.login_user_email)) },
        singleLine = true,
        enabled = enabled,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                emailState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    emailState.enableShowErrors()
                }
            },
        isError = emailState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Email,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onAny = {
            onImeAction()
        })
    )
    emailState.getError()?.let { error -> TextFieldError(textError = error) }
}
