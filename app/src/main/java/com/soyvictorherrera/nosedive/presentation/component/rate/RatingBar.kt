package com.soyvictorherrera.nosedive.presentation.component.rate

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.presentation.theme.Dark_Pink
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.theme.Wild_Watermelon

@Composable
fun RatingBar(
    rating: Int,
    modifier: Modifier = Modifier,
    maxValue: Int = 5,
    onRateChange: (newRate: Int) -> Unit
) = Row(
    modifier = modifier
) {
    (1..maxValue).forEach { step ->
        RatingStar(
            filled = step <= rating,
            modifier = Modifier
                .aspectRatio(
                    ratio = 1f,
                    matchHeightConstraintsFirst = true
                )
                .weight(1f)
                .padding(all = 8.dp)
                .clickable {
                    onRateChange(step)
                }
        )
    }
}

@Composable
fun SmallRatingBar(
    rating: Int,
    modifier: Modifier = Modifier,
    maxValue: Int = 5
) = Row(modifier = modifier) {
    (1..maxValue).forEach { step ->
        RatingStar(
            filled = step <= rating,
            modifier = Modifier
                .aspectRatio(
                    ratio = 1f,
                    matchHeightConstraintsFirst = true
                )
        )
    }
}

@Composable
fun RatingStar(
    filled: Boolean,
    modifier: Modifier
) {
    Icon(
        imageVector = Icons.Sharp.Star,
        modifier = modifier,
        contentDescription = null,
        tint = if (filled) Wild_Watermelon else Dark_Pink
    )
}

@Preview
@Composable
fun RatingBarPreview() {
    NosediveTheme {
        RatingBar(
            rating = 4,
            maxValue = 10,
            onRateChange = {}
        )
    }
}

@Preview(name = "SmallRatingBar")
@Composable
fun SmallRatingBarPreview() {
    NosediveTheme {
        SmallRatingBar(
            rating = 4,
            Modifier.height(16.dp)
        )
    }
}
