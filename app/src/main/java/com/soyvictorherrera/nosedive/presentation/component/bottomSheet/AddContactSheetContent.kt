package com.soyvictorherrera.nosedive.presentation.component.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.PhotoCamera
import androidx.compose.material.icons.sharp.QrCode
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.component.button.SecondaryButton
import com.soyvictorherrera.nosedive.presentation.component.modifier.sheetPadding
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun AddContactSheetContent(
    onScanCodeSelected: () -> Unit,
    onShowCodeSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .sheetPadding()
    ) {

        Text(
            text = stringResource(R.string.sheet_add_contact_title),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onBackground
        )

        Spacer(modifier = Modifier.height(32.dp))

        SecondaryButton(
            text = stringResource(R.string.sheet_add_contact_option_scan_code),
            onClick = onScanCodeSelected,
            icon = Icons.Sharp.PhotoCamera
        )

        Spacer(modifier = Modifier.height(16.dp))

        SecondaryButton(
            text = stringResource(R.string.sheet_add_contact_option_show_code),
            onClick = onShowCodeSelected,
            icon = Icons.Sharp.QrCode
        )
    }
}

@Preview
@Composable
fun AddContactSheetContentPreview() {
    NosediveTheme {
        AddContactSheetContent(
            onScanCodeSelected = {},
            onShowCodeSelected = {}
        )
    }
}
