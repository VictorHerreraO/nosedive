package com.soyvictorherrera.nosedive.presentation.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.Collections
import androidx.compose.material.icons.sharp.Done
import androidx.compose.material.icons.sharp.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.component.button.MainButton
import com.soyvictorherrera.nosedive.presentation.component.button.SecondaryButton
import com.soyvictorherrera.nosedive.presentation.component.common.DefaultProminentTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.form.EmailTextField
import com.soyvictorherrera.nosedive.presentation.component.form.NameTextField
import com.soyvictorherrera.nosedive.presentation.component.form.PasswordTextField
import com.soyvictorherrera.nosedive.presentation.component.profile.UserPhoto
import com.soyvictorherrera.nosedive.presentation.component.state.EmailState
import com.soyvictorherrera.nosedive.presentation.component.state.MIN_PASSWORD_LENGTH
import com.soyvictorherrera.nosedive.presentation.component.state.NameState
import com.soyvictorherrera.nosedive.presentation.component.state.PasswordState
import com.soyvictorherrera.nosedive.presentation.theme.Black_32
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

sealed class ProfileEvent {
    object NavigateBack : ProfileEvent()
    object UpdateUserProfilePhoto : ProfileEvent()
    data class UpdateUserPassword(val password: String, val newPassword: String) : ProfileEvent()
    object SelectPhotoFromCamera : ProfileEvent()
    object SelectPhotoFromGallery : ProfileEvent()
}

@Composable
@ExperimentalMaterialApi
fun ProfileContentView(
    user: UserModel,
    sheetState: ModalBottomSheetState,
    profilePhotoState: ProfilePhotoState = ProfilePhotoState.Idle(photoUri = null),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    onNavigationEvent: (event: ProfileEvent) -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetShape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp,
            bottomStart = 0.dp,
            bottomEnd = 0.dp
        ),
        sheetContent = {
            SelectProfilePhotoSourceSheetContent(
                onCameraSourceSelected = { onNavigationEvent(ProfileEvent.SelectPhotoFromCamera) },
                onGallerySourceSelected = { onNavigationEvent(ProfileEvent.SelectPhotoFromGallery) }
            )
        },
        scrimColor = Black_32
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                ProfileTopAppBar {
                    onNavigationEvent(ProfileEvent.NavigateBack)
                }
            },
            content = {
                ProfileContent(
                    user = user,
                    profilePhotoState = profilePhotoState,
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
                    onUpdatePassword = { password, newPassword ->
                        onNavigationEvent(
                            ProfileEvent.UpdateUserPassword(
                                password = password,
                                newPassword = newPassword
                            )
                        )
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
    profilePhotoState: ProfilePhotoState = ProfilePhotoState.Idle(photoUri = null),
    onUpdatePhoto: () -> Unit,
    onUpdatePassword: (password: String, newPassword: String) -> Unit,
) {
    Column(modifier = modifier) {
        ProfilePhoto(
            profilePhotoState = profilePhotoState,
            onUpdatePhoto = onUpdatePhoto
        )

        Spacer(modifier = Modifier.height(32.dp))

        UserForm(user = user) { passwordState, newPasswordState ->
            Spacer(modifier = Modifier.weight(1f))

            MainButton(
                text = stringResource(R.string.profile_save_changes),
                icon = Icons.Sharp.Done,
                enabled = passwordState.isValid && newPasswordState.isValid,
                onClick = {
                    onUpdatePassword(passwordState.text, newPasswordState.text)
                }
            )
        }
    }
}

@Composable
fun ProfilePhoto(
    profilePhotoState: ProfilePhotoState,
    modifier: Modifier = Modifier,
    onUpdatePhoto: () -> Unit
) {
    Row(modifier = modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.weight(1f))

        when (profilePhotoState) {
            is ProfilePhotoState.Idle -> {
                val photoUri = profilePhotoState.photoUri
                IdleUserPhoto(
                    painter = if (photoUri == null) {
                        painterResource(id = R.drawable.ic_launcher_foreground)
                    } else rememberImagePainter(photoUri),
                    modifier = Modifier.weight(2f),
                    onUpdatePhoto = onUpdatePhoto
                )
            }
            is ProfilePhotoState.Loading -> {
                LoadingUserPhoto(
                    painter = rememberImagePainter(profilePhotoState.previewUri),
                    modifier = Modifier.weight(2f)
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
fun IdleUserPhoto(
    painter: Painter,
    modifier: Modifier = Modifier,
    onUpdatePhoto: () -> Unit
) {
    Box(modifier = modifier) {
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
}

@Composable
fun LoadingUserPhoto(
    painter: Painter,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier) {
        UserPhoto(
            painter = painter,
            modifier = Modifier.alpha(0.25f)
        )
        CircularProgressIndicator(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(24.dp)
                .offset(
                    x = (-20).dp,
                    y = (-20).dp
                )
        )
    }
}

@Composable
fun UserForm(
    user: UserModel,
    modifier: Modifier = Modifier,
    formActions: @Composable (
        passwordState: PasswordState,
        newPasswordState: PasswordState
    ) -> Unit
) {
    Column(modifier = modifier) {
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
        val newPasswordFocusRequester = remember { FocusRequester() }
        PasswordTextField(
            label = stringResource(R.string.profile_current_password),
            passwordState = passwordState,
            imeAction = ImeAction.Next,
            onImeAction = { newPasswordFocusRequester.requestFocus() }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // New user password
        val newPasswordState = remember {
            PasswordState(errorFor = { password ->
                "${password.length}/$MIN_PASSWORD_LENGTH"
            })
        }
        PasswordTextField(
            label = stringResource(R.string.profile_new_password),
            passwordState = newPasswordState,
            modifier = Modifier.focusRequester(newPasswordFocusRequester),
            imeAction = ImeAction.Done,
            onImeAction = { focusManager.clearFocus() }
        )

        // Form actions
        formActions(
            passwordState,
            newPasswordState
        )
    }
}

@Composable
fun SelectProfilePhotoSourceSheetContent(
    onCameraSourceSelected: () -> Unit,
    onGallerySourceSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 64.dp)
    ) {
        Text(
            text = stringResource(R.string.profile_sheet_photo_title),
            style = MaterialTheme.typography.subtitle2,
            color = Color.White
        )

        Spacer(modifier = Modifier.height(32.dp))

        SecondaryButton(
            text = stringResource(R.string.profile_sheet_photo_source_camera),
            onClick = onCameraSourceSelected,
            icon = Icons.Sharp.PhotoCamera
        )

        Spacer(modifier = Modifier.height(16.dp))

        SecondaryButton(
            text = stringResource(R.string.profile_sheet_photo_source_gallery),
            onClick = onGallerySourceSelected,
            icon = Icons.Sharp.Collections
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

@Preview
@Composable
fun SelectProfilePhotoSourceSheetContentPreview() {
    NosediveTheme {
        SelectProfilePhotoSourceSheetContent(
            onCameraSourceSelected = {},
            onGallerySourceSelected = {}
        )
    }
}
