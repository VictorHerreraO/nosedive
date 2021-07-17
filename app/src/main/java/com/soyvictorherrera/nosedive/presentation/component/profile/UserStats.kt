package com.soyvictorherrera.nosedive.presentation.component.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun UserStats(
    followers: Int,
    ratings: Int,
    following: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colors.surface,
        elevation = 0.dp
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            StatItem(
                count = followers,
                label = stringResource(R.string.user_followers)
            )

            Spacer(modifier = Modifier.weight(1f))

            StatItem(
                count = ratings,
                label = stringResource(R.string.user_ratings)
            )

            Spacer(modifier = Modifier.weight(1f))

            StatItem(
                count = following,
                label = stringResource(R.string.user_following)
            )
        }
    }
}

@Composable
fun StatItem(
    count: Int,
    label: String,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment
    ) {
        Text(
            text = count.toString(),
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
        Text(
            text = label,
            fontWeight = FontWeight.Light,
            fontSize = 10.sp
        )
    }
}

@Preview
@Composable
fun UserStatsPreview() {
    NosediveTheme {
        UserStats(
            followers = 0,
            ratings = 2,
            following = 1
        )
    }
}
