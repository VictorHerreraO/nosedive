package com.soyvictorherrera.nosedive.presentation.component.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

const val SCORE_FORMAT = "%.5f"

@Composable
fun UserScoreCompact(
    score: Double,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.End
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        val first: String
        val last: String
        SCORE_FORMAT.format(score).let {
            first = it.substring(0, it.length - 3)
            last = it.substring(it.length - 3, it.length - 1)
        }

        Text(text = first, style = MaterialTheme.typography.h4)
        Text(text = last, style = MaterialTheme.typography.subtitle1)
    }
}

@Preview
@Composable
fun UserScoreCompactPreview() {
    NosediveTheme {
        UserScoreCompact(score = 4.307992)
    }
}
