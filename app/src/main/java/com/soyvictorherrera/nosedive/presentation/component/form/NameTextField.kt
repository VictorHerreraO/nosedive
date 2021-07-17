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
import androidx.compose.ui.text.input.KeyboardCapitalization
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.component.state.TextFieldState

@Composable
fun NameTextField(
    nameState: TextFieldState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {

    TextField(
        value = nameState.text,
        onValueChange = { nameState.text = it },
        label = { Text(stringResource(R.string.signup_name)) },
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                nameState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    nameState.enableShowErrors()
                }
            },
        isError = nameState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.Words,
            imeAction = imeAction
        ),
        keyboardActions = KeyboardActions(onAny = {
            onImeAction()
        })
    )
    nameState.getError()?.let { error -> TextFieldError(textError = error) }
}
