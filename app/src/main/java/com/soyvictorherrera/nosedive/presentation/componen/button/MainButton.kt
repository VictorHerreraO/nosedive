package com.soyvictorherrera.nosedive.presentation.componen.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun MainButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null,
    showLoader: Boolean = false
) {
    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled && !showLoader
    ) {
        if (showLoader) {
            LoadingButtonContent()
        } else {
            MainButtonContent(text = text, icon = icon)
        }
    }
}

@Composable
fun MainButtonContent(
    text: String,
    icon: ImageVector? = null
) {
    val contentColor = Color.White
    icon?.let { ic ->
        Icon(
            imageVector = ic,
            contentDescription = null,
            modifier = Modifier.size(ButtonDefaults.IconSize),
            tint = contentColor
        )
        Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
    }
    Text(
        text = text.toUpperCase(Locale.current),
        color = contentColor
    )
}

@Composable
fun LoadingButtonContent() {
    CircularProgressIndicator(modifier = Modifier.size(ButtonDefaults.IconSize))
}

@Preview("Main button preview")
@Composable
fun MainButtonPreview() {
    NosediveTheme {
        MainButton(
            text = "Listo",
            onClick = {},
            icon = Icons.Sharp.Done
        )
    }
}