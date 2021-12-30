package com.soyvictorherrera.nosedive.presentation.component.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun UserPhoto(
    painter: Painter,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    borderWidth: Dp = 20.dp,
    borderStrokeWidth: Dp = 2.dp
) {
    Surface(
        modifier = modifier
            .aspectRatio(1f),
        shape = CircleShape,
        color = backgroundColor,
        elevation = 2.dp
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(all = borderWidth),
            shape = CircleShape,
            border = BorderStroke(
                width = borderStrokeWidth,
                color = Color.White
            )
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            )
        }
    }
}

@Preview
@Composable
fun UserPhotoPreview() {
    NosediveTheme {
        UserPhoto(
            painter = painterResource(
                id = R.drawable.ic_launcher_foreground
            )
        )
    }
}

