package com.soyvictorherrera.nosedive.presentation.ui.codeScanning

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PhotoCamera
import androidx.compose.material.icons.sharp.Pin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.component.button.ActionButton
import com.soyvictorherrera.nosedive.presentation.component.button.SecondaryButton
import com.soyvictorherrera.nosedive.presentation.component.common.NoTitleTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.modifier.contentPadding
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.codeScanning.CodeScanningEvent.NavigateBack
import com.soyvictorherrera.nosedive.presentation.ui.codeScanning.CodeScanningEvent.NavigateCodeShow

sealed class CodeScanningEvent {
    object NavigateBack : CodeScanningEvent()
    object NavigateCodeShow : CodeScanningEvent()
}

@Composable
fun CodeScanningContentView(
    modifier: Modifier = Modifier,
    onNavigationEvent: (event: CodeScanningEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            NoTitleTopAppBar(onNavigateBack = { onNavigationEvent(NavigateBack) })
        },
        content = {
            CodeScanningContent(
                onShowCode = { onNavigationEvent(NavigateCodeShow) }
            )
        }
    )
}

@Composable
fun CodeScanningContent(
    modifier: Modifier = Modifier,
    onShowCode: () -> Unit
) {
    Column(
        modifier = modifier.contentPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        UserCodeScanCard {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CameraPreview(modifier = Modifier.size(200.dp))

                Spacer(modifier = Modifier.height(32.dp))

                InputTextCode()
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        ActionButton(
            text = stringResource(R.string.code_scanning_show_code),
            onClick = { onShowCode() },
            icon = Icons.Sharp.PhotoCamera
        )
    }
}

@Composable
fun UserCodeScanCard(
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
            Icon(
                imageVector = Icons.Sharp.PhotoCamera,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colors.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.code_scanning_scan_code),
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            cardContent()
        }
    }
}

@Composable
fun CameraPreview(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.aspectRatio(1f),
        color = MaterialTheme.colors.primary
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            color = Color.White,
            shape = CircleShape
        ) {

        }
    }
}

@Composable
fun InputTextCode(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.code_sharing_not_working),
            style = MaterialTheme.typography.caption
        )

        Spacer(modifier = Modifier.height(8.dp))

        SecondaryButton(
            text = "Ingresa un código",
            onClick = { /*TODO*/ },
            icon = Icons.Sharp.Pin
        )
    }
}

@Preview
@Composable
fun CodeScanningContentViewPreview() {
    NosediveTheme {
        CodeScanningContentView() {

        }
    }
}
