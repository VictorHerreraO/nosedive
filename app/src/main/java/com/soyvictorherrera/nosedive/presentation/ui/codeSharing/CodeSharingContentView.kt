package com.soyvictorherrera.nosedive.presentation.ui.codeSharing

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.ErrorOutline
import androidx.compose.material.icons.sharp.PhotoCamera
import androidx.compose.material.icons.sharp.Pin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.component.button.ActionButton
import com.soyvictorherrera.nosedive.presentation.component.button.SecondaryButton
import com.soyvictorherrera.nosedive.presentation.component.common.NoTitleTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.modifier.contentPadding
import com.soyvictorherrera.nosedive.presentation.component.profile.UserPhoto
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.codeSharing.CodeSharingEvent.*

sealed class CodeSharingEvent {
    object NavigateBack : CodeSharingEvent()
    object GenerateSharingCode : CodeSharingEvent()
    object ScanSharingCode : CodeSharingEvent()
}

@Composable
fun CodeSharingContentView(
    user: UserModel,
    scaffoldState: ScaffoldState,
    imageCodeState: ImageCodeState,
    textCodeState: TextCodeState,
    modifier: Modifier = Modifier,
    onNavigationEvent: (event: CodeSharingEvent) -> Unit
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier,
        topBar = {
            NoTitleTopAppBar(onNavigateBack = { onNavigationEvent(NavigateBack) })
        },
        content = {
            CodeSharingContent(
                user = user,
                onGenerateSharingCode = { onNavigationEvent(GenerateSharingCode) },
                onScanSharingCode = { onNavigationEvent(ScanSharingCode) },
                imageCodeState = imageCodeState,
                textCodeState = textCodeState
            )
        }
    )
}

@Composable
fun CodeSharingContent(
    user: UserModel,
    onGenerateSharingCode: () -> Unit,
    onScanSharingCode: () -> Unit,
    imageCodeState: ImageCodeState,
    textCodeState: TextCodeState,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.contentPadding(),
        horizontalAlignment = CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        UserCodeCard(user = user) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = CenterHorizontally
            ) {
                ImageCodePreview(
                    imageCodeState = imageCodeState,
                    modifier = Modifier
                        .height(200.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                TextCodePreview(
                    textCodeState = textCodeState,
                    onGenerateSharingCode = onGenerateSharingCode,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        ActionButton(
            text = stringResource(R.string.code_sharing_scan_code),
            onClick = { onScanSharingCode() },
            icon = Icons.Sharp.PhotoCamera
        )
    }
}

@Composable
fun UserCodeCard(
    user: UserModel,
    modifier: Modifier = Modifier,
    cardContent: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = CenterHorizontally
        ) {
            UserPhoto(
                painter = user.photoUrl?.let { uri ->
                    rememberImagePainter(data = Uri.parse(uri.toString()))
                } ?: painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier.height(96.dp),
                borderWidth = 12.dp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = user.name, style = MaterialTheme.typography.body2)

            Spacer(modifier = Modifier.height(32.dp))

            cardContent()
        }
    }
}

@Composable
fun ImageCodePreview(
    imageCodeState: ImageCodeState,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White,
        shape = MaterialTheme.shapes.small,
        modifier = modifier.aspectRatio(1f)
    ) {
        when (imageCodeState) {
            ImageCodeState.Loading -> {
                Box {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Center)
                    )
                }
            }
            is ImageCodeState.Generated -> {
                Image(
                    painter = rememberImagePainter(
                        data = imageCodeState.codeUri
                    ),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }
            ImageCodeState.Error -> {
                Box {
                    Icon(
                        imageVector = Icons.Sharp.ErrorOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colors.error,
                        modifier = Modifier
                            .size(36.dp)
                            .align(Center)
                    )
                }
            }
        }
    }
}

@Composable
fun TextCodePreview(
    textCodeState: TextCodeState,
    onGenerateSharingCode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = CenterHorizontally
    ) {
        when (textCodeState) {
            TextCodeState.Error -> {
                Text(
                    text = "Error",
                    style = MaterialTheme.typography.body1
                )
            }
            is TextCodeState.Generated -> {
                val fullCode = textCodeState.code
                val aPart = fullCode.substring(0, 3)
                val bPart = fullCode.substring(3, 6)

                Text(
                    text = stringResource(R.string.code_sharing_your_single_use_code),
                    style = MaterialTheme.typography.caption
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = CenterVertically) {
                    TextCodeChip(code = aPart)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "-")
                    Spacer(modifier = Modifier.width(4.dp))
                    TextCodeChip(code = bPart)
                }
            }
            TextCodeState.Loading -> {
                Text(
                    text = stringResource(R.string.code_sharing_not_working),
                    style = MaterialTheme.typography.caption,
                )

                Spacer(modifier = Modifier.height(8.dp))

                SecondaryButton(
                    text = stringResource(R.string.code_sharing_generate_code),
                    onClick = { /* */ },
                    enabled = false,
                    showLoader = true
                )
            }
            TextCodeState.None -> {
                Text(
                    text = stringResource(R.string.code_sharing_not_working),
                    style = MaterialTheme.typography.caption
                )

                Spacer(modifier = Modifier.height(8.dp))

                SecondaryButton(
                    text = stringResource(R.string.code_sharing_generate_code),
                    onClick = { onGenerateSharingCode() },
                    icon = Icons.Sharp.Pin
                )
            }
        }
    }
}

@Composable
fun TextCodeChip(code: String) {
    Surface(
        elevation = 2.dp,
        shape = RoundedCornerShape(2.dp)
    ) {
        Text(
            text = code,
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.subtitle1
        )
    }
}

@Preview
@Composable
fun CodeSharingContentViewPreview() {
    NosediveTheme {
        CodeSharingContentView(
            user = UserModel(name = "Victor Herrera", email = ""),
            scaffoldState = rememberScaffoldState(),
            onNavigationEvent = {},
            imageCodeState = ImageCodeState.Loading,
            textCodeState = TextCodeState.Generated(code = "123456")
        )
    }
}
