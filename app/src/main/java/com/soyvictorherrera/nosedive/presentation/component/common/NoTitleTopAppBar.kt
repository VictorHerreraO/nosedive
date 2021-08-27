package com.soyvictorherrera.nosedive.presentation.component.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun NoTitleTopAppBar(
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

@Preview
@Composable
fun NoTitleTopAppBarPreview() {
    NosediveTheme {
        NoTitleTopAppBar(onNavigateBack = {})
    }
}
