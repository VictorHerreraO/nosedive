package com.soyvictorherrera.nosedive.presentation.component.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun LoadingDialog() = Dialog(
    onDismissRequest = {
        /* prevent dialog dismissal*/
    },
    properties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false,

        ),
    content = {
        LoadingDialogContent()
    }
)

@Composable
private fun LoadingDialogContent() = Column(
    modifier = Modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    CircularProgressIndicator(
        modifier = Modifier.size(40.dp)
    )
}

@Preview
@Composable
fun LoadingDialogPreview() {
    NosediveTheme {
        LoadingDialog()
    }
}
