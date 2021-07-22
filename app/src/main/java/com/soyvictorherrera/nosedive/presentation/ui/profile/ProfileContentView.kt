package com.soyvictorherrera.nosedive.presentation.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material.icons.sharp.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.component.button.MainButton
import com.soyvictorherrera.nosedive.presentation.component.common.DefaultProminentTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.form.EmailTextField
import com.soyvictorherrera.nosedive.presentation.component.form.NameTextField
import com.soyvictorherrera.nosedive.presentation.component.form.PasswordTextField
import com.soyvictorherrera.nosedive.presentation.component.profile.UserPhoto
import com.soyvictorherrera.nosedive.presentation.component.state.*
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

sealed class ProfileEvent {
    object NavigateBack : ProfileEvent()
    object UpdateUserProfilePhoto : ProfileEvent()
    data class UpdateUserPassword(val newPassword: String) : ProfileEvent()
}

@Composable
@ExperimentalMaterialApi
fun ProfileContentView(
    user: UserModel,
    sheetState: ModalBottomSheetState,
    onNavigationEvent: (event: ProfileEvent) -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(text = "Actualiza tu foto de perfil")

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Tomar una foto")
                }

                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Tú galería")
                }
            }
        }) {
        Scaffold(
            topBar = {
                ProfileTopAppBar {
                    onNavigationEvent(ProfileEvent.NavigateBack)
                }
            },
            content = {
                ProfileContent(
                    user = user,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            start = 32.dp,
                            end = 32.dp,
                            top = 16.dp,
                            bottom = 64.dp
                        ),
                    onUpdatePhoto = {
                        onNavigationEvent(ProfileEvent.UpdateUserProfilePhoto)
                    },
                    onUpdatePassword = { newPassword ->
                        onNavigationEvent(ProfileEvent.UpdateUserPassword(newPassword))
                    }
                )
            })
    }
}

@Composable
fun ProfileTopAppBar(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    DefaultProminentTopAppBar(title = "Editar mi perfil",
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(imageVector = Icons.Sharp.ArrowBack, contentDescription = null)
            }
        })
}

@Composable
fun ProfileContent(
    user: UserModel,
    modifier: Modifier = Modifier,
    onUpdatePhoto: () -> Unit,
    onUpdatePassword: (newPassword: String) -> Unit,
) {
    Column(modifier = modifier) {
        ProfilePhoto(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            onUpdatePhoto = onUpdatePhoto
        )

        Spacer(modifier = Modifier.height(32.dp))

        UserForm(user = user) { _, passwordState, confirmPasswordState ->
            Spacer(modifier = Modifier.weight(1f))

            MainButton(
                text = stringResource(R.string.profile_save_changes),
                icon = Icons.Sharp.Done,
                enabled = passwordState.isValid && confirmPasswordState.isValid,
                onClick = {
                    onUpdatePassword(passwordState.text)
                }
            )
        }
    }
}

@Composable
fun ProfilePhoto(
    painter: Painter,
    modifier: Modifier = Modifier,
    onUpdatePhoto: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1f))

        Box(modifier = Modifier.weight(2f)) {
            UserPhoto(
                painter = painter
            )
            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(
                        x = (-20).dp,
                        y = (-20).dp
                    )
                    .size(40.dp),
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = MaterialTheme.colors.onPrimary,
                onClick = onUpdatePhoto
            ) {
                Icon(
                    imageVector = Icons.Sharp.PhotoCamera,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun UserForm(
    user: UserModel,
    modifier: Modifier = Modifier,
    formActions: @Composable (
        nameState: NameState,
        passwordState: PasswordState,
        confirmPasswordState: ConfirmPasswordState
    ) -> Unit
) {
    Column(modifier = modifier) {
        val context = LocalContext.current
        val focusManager = LocalFocusManager.current

        // User email - read only
        val emailState = remember {
            EmailState(errorFor = { "" })
        }
        emailState.apply { text = user.email }
        EmailTextField(
            emailState = emailState,
            enabled = false,
            onImeAction = { }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User name
        val nameState = remember {
            NameState(errorFor = { "" }).apply { text = user.name }
        }
        nameState.apply { text = user.name }
        NameTextField(
            nameState = nameState,
            enabled = false,
            imeAction = ImeAction.Next,
            onImeAction = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        // User password
        val passwordState = remember {
            PasswordState(errorFor = { password ->
                "${password.length}/$MIN_PASSWORD_LENGTH"
            })
        }
        val confirmPasswordFocusRequester = remember { FocusRequester() }
        PasswordTextField(
            label = stringResource(R.string.profile_current_password),
            passwordState = passwordState,
            imeAction = ImeAction.Next,
            onImeAction = { confirmPasswordFocusRequester.requestFocus() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Confirm user password
        val confirmPasswordState = remember {
            ConfirmPasswordState(passwordState = passwordState, errorFor = {
                context.getString(R.string.profile_error_password_dont_match)
            })
        }
        PasswordTextField(
            label = stringResource(R.string.profile_new_password),
            passwordState = confirmPasswordState,
            modifier = Modifier.focusRequester(confirmPasswordFocusRequester),
            imeAction = ImeAction.Done,
            onImeAction = { focusManager.clearFocus() }
        )

        // Form actions
        formActions(
            nameState,
            passwordState,
            confirmPasswordState
        )
    }
}

@Preview
@Composable
@ExperimentalMaterialApi
fun ProfileContentViewPreview() {
    NosediveTheme {
        val userState by remember {
            mutableStateOf(
                UserModel(
                    name = "Víctor Herrera",
                    email = "vicho@example.com"
                )
            )
        }
        ProfileContentView(
            user = userState,
            sheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)
        ) {}
    }
}