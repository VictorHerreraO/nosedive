package com.soyvictorherrera.nosedive.presentation.ui.codeSharing

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.material.icons.sharp.PhotoCamera
import androidx.compose.material.icons.sharp.Pin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import com.soyvictorherrera.nosedive.presentation.component.modifier.contentPadding
import com.soyvictorherrera.nosedive.presentation.component.profile.UserPhoto
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun CodeSharingContentView(
    user: UserModel,
    scaffoldState: ScaffoldState,
    modifier: Modifier = Modifier
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = modifier,
        topBar = {
            CodeSharingTopBar(onNavigateBack = { })
        },
        content = {
            CodeSharingContent(
                user = user,
                onGenerateSharingCode = {},
                onScanSharingCode = {}
            )
        }
    )
}

@Composable
fun CodeSharingTopBar(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { /* No title */ },
        navigationIcon = {
            IconButton(onClick = { onNavigateBack() }) {
                Icon(
                    imageVector = Icons.Sharp.ArrowBack,
                    contentDescription = null
                )
            }
        },
        modifier = modifier.fillMaxWidth(),
        elevation = 0.dp
    )
}

@Composable
fun CodeSharingContent(
    user: UserModel,
    onGenerateSharingCode: () -> Unit,
    onScanSharingCode: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.contentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        UserCodeCard(user = user) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Surface(
                    color = Color.White,
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .height(200.dp)
                        .aspectRatio(1f)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

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
            horizontalAlignment = Alignment.CenterHorizontally
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

@Preview
@Composable
fun CodeSharingContentViewPreview() {
    NosediveTheme {
        CodeSharingContentView(
            user = UserModel(name = "Victor Herrera", email = ""),
            scaffoldState = rememberScaffoldState()
        )
    }
}
