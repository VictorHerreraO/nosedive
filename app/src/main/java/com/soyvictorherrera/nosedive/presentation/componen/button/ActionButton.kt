package com.soyvictorherrera.nosedive.presentation.componen.button

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.presentation.theme.Alto
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun ActionButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: ImageVector? = null
) {
    TextButton(
        onClick = { onClick() },
        modifier = modifier,
        enabled = enabled
    ) {
        Row {
            val contentColor = Alto
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
                color = contentColor,
                style = MaterialTheme.typography.button.copy(fontSize = 12.sp)
            )
        }
    }
}

@Preview(name = "Secondary button preview")
@Composable
fun ActionButtonPreview() {
    NosediveTheme {
        ActionButton(
            text = "Listo",
            onClick = {},
            icon = Icons.Sharp.Done
        )
    }
}
