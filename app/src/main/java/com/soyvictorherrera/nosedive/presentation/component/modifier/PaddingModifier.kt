package com.soyvictorherrera.nosedive.presentation.component.modifier

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.contentPadding(
    start: Dp = 32.dp,
    end: Dp = 32.dp,
    top: Dp = 16.dp,
    bottom: Dp = 64.dp
) = this.padding(
    start = start,
    end = end,
    top = top,
    bottom = bottom
)

fun Modifier.sheetPadding(
    start: Dp = 16.dp,
    end: Dp = 16.dp,
    top: Dp = 32.dp,
    bottom: Dp = 64.dp
) = this.padding(
    start = start,
    end = end,
    top = top,
    bottom = bottom
)

fun Modifier.listPadding(
    horizontal: Dp = 16.dp
) = this.padding(horizontal = horizontal)
