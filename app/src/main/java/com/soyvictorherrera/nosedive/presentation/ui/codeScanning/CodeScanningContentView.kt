package com.soyvictorherrera.nosedive.presentation.ui.codeScanning

import android.view.ViewGroup
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ErrorOutline
import androidx.compose.material.icons.sharp.PhotoCamera
import androidx.compose.material.icons.sharp.Pin
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.component.button.ActionButton
import com.soyvictorherrera.nosedive.presentation.component.button.SecondaryButton
import com.soyvictorherrera.nosedive.presentation.component.common.NoTitleTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.modifier.contentPadding
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.codeScanning.CodeScanningEvent.NavigateBack
import com.soyvictorherrera.nosedive.presentation.ui.codeScanning.CodeScanningEvent.NavigateCodeShow
import com.soyvictorherrera.nosedive.presentation.ui.codeScanning.TextCodeInputState.Idle
import com.soyvictorherrera.nosedive.presentation.ui.codeScanning.TextCodeInputState.Ready

sealed class CodeScanningEvent {
    object NavigateBack : CodeScanningEvent()
    object NavigateCodeShow : CodeScanningEvent()
}

sealed class CodeScanningActionEvent {
    object WriteCode : CodeScanningActionEvent()
    data class QrPreviewCreated(val view: PreviewView) : CodeScanningActionEvent()
}

@Composable
fun CodeScanningContentView(
    inputState: TextCodeInputState,
    scanState: CodeScanState,
    modifier: Modifier = Modifier,
    onNavigationEvent: (event: CodeScanningEvent) -> Unit,
    onActionEvent: (event: CodeScanningActionEvent) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            NoTitleTopAppBar(onNavigateBack = { onNavigationEvent(NavigateBack) })
        },
        content = {
            CodeScanningContent(
                inputState = inputState,
                scanState = scanState,
                onNavigationEvent = onNavigationEvent,
                onActionEvent = onActionEvent
            )
        }
    )
}

@Composable
fun CodeScanningContent(
    inputState: TextCodeInputState,
    scanState: CodeScanState,
    modifier: Modifier = Modifier,
    onNavigationEvent: (event: CodeScanningEvent) -> Unit,
    onActionEvent: (event: CodeScanningActionEvent) -> Unit
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
                CameraPreview(
                    scanState = scanState,
                    modifier = Modifier.size(200.dp),
                    onPreviewCreated = { preview ->
                        onActionEvent(CodeScanningActionEvent.QrPreviewCreated(view = preview))
                    }
                )

                Spacer(modifier = Modifier.height(32.dp))

                InputTextCode(
                    inputState = inputState,
                    onWriteCode = {
                        onActionEvent(CodeScanningActionEvent.WriteCode)
                    }
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        ActionButton(
            text = stringResource(R.string.code_scanning_show_code),
            onClick = { onNavigationEvent(NavigateCodeShow) },
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
fun CameraPreview(
    scanState: CodeScanState,
    modifier: Modifier = Modifier,
    onPreviewCreated: (PreviewView) -> Unit
) {
    Surface(
        modifier = modifier.aspectRatio(1f)
    ) {
        when (scanState) {
            CodeScanState.Waiting -> {
                Box {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.Center)
                    )
                }
            }
            CodeScanState.Active -> {
                AndroidView(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxSize(),
                    factory = { context ->
                        PreviewView(context).apply {
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )
                        }.also { preview ->
                            onPreviewCreated(preview)
                        }
                    }
                )
            }
            CodeScanState.Error -> {
                Box {
                    Icon(
                        imageVector = Icons.Sharp.ErrorOutline,
                        contentDescription = null,
                        tint = MaterialTheme.colors.error,
                        modifier = Modifier
                            .size(36.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}

@Composable
fun InputTextCode(
    inputState: TextCodeInputState,
    modifier: Modifier = Modifier,
    onWriteCode: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var code by remember { mutableStateOf("") }
        when (inputState) {
            Idle -> {
                Text(
                    text = stringResource(id = R.string.code_sharing_not_working),
                    style = MaterialTheme.typography.caption
                )

                Spacer(modifier = Modifier.height(8.dp))

                SecondaryButton(
                    text = stringResource(R.string.code_scanning_write_a_code),
                    onClick = { onWriteCode() },
                    icon = Icons.Sharp.Pin
                )
            }
            is Ready -> {
                TextCodeField(
                    code = code,
                    onValueChange = {
                        inputState.code = it
                        code = it
                    }
                )
            }
            is TextCodeInputState.Error -> {

            }
            TextCodeInputState.Loading -> {
                TextCodeField(code = code, loading = true)
            }
        }
    }
}

@Composable
fun TextCodeField(
    code: String,
    loading: Boolean = false,
    onValueChange: (it: String) -> Unit = {}
) {
    TextField(
        value = code,
        modifier = Modifier.fillMaxWidth(),
        label = {
            Text(text = stringResource(R.string.code_scanning_write_the_code))
        },
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number
        ),
        singleLine = true,
        enabled = !loading,
        trailingIcon = {
            if (loading) {
                CircularProgressIndicator()
            }
        }
    )
}

@Preview
@Composable
fun CodeScanningContentViewIdlePreview() {
    NosediveTheme {
        CodeScanningContentView(
            inputState = Idle,
            scanState = CodeScanState.Waiting,
            onNavigationEvent = {},
            onActionEvent = {}
        )
    }
}
