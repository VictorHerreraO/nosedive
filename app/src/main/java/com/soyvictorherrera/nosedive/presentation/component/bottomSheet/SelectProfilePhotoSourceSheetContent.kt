package com.soyvictorherrera.nosedive.presentation.component.bottomSheet

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Collections
import androidx.compose.material.icons.sharp.PhotoCamera
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.component.button.SecondaryButton
import com.soyvictorherrera.nosedive.presentation.component.modifier.sheetPadding
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun SelectProfilePhotoSourceSheetContent(
    onCameraSourceSelected: () -> Unit,
    onGallerySourceSelected: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .sheetPadding()
    ) {
        Text(
            text = stringResource(R.string.profile_sheet_photo_title),
            style = MaterialTheme.typography.subtitle2,
            color = MaterialTheme.colors.onBackground
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
fun SelectProfilePhotoSourceSheetContentPreview() {
    NosediveTheme {
        SelectProfilePhotoSourceSheetContent(
            onCameraSourceSelected = {},
            onGallerySourceSelected = {}
        )
    }
}
