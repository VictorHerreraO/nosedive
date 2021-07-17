package com.soyvictorherrera.nosedive.presentation.componen.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun UserDetails(
    userName: String,
    modifier: Modifier = Modifier,
    userScore: Double? = null,
    userPhotoPainter: Painter? = null,
    userPhotoBackgroundColor: Color = MaterialTheme.colors.surface
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Spacer(modifier = Modifier.weight(1f))
            UserPhoto(
                painter = userPhotoPainter
                    ?: painterResource(id = R.drawable.ic_launcher_foreground),
                modifier = Modifier.weight(2f),
                backgroundColor = userPhotoBackgroundColor
            )
            if (userScore != null) {
                UserScoreCompact(
                    score = userScore,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier.weight(1f))
            }
        }

        Spacer(Modifier.height(16.dp))

        Text(
            text = userName,
            style = MaterialTheme.typography.h6
        )
    }
}

@Preview
@Composable
fun UserDetailsPreview() {
    NosediveTheme {
        UserDetails(
            userName = "Víctor Herrera",
            userScore = 4.30799
        )
    }
}

