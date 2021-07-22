package com.soyvictorherrera.nosedive.presentation.component.button

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import com.soyvictorherrera.nosedive.presentation.theme.Dove_Gray
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    Button(
        onClick = { onClick() },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Dove_Gray,
            contentColor = Color.White
        )
    ) {
        SecondaryButtonContent(text = text, icon = icon)
    }
}

@Composable
fun SecondaryButtonContent(
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

@Preview
@Composable
fun SecondaryButtonPreview() {
    NosediveTheme {
        SecondaryButton(
            text = "Listo",
            onClick = {},
            icon = Icons.Sharp.Done
        )
    }
}
