package com.soyvictorherrera.nosedive.presentation.ui.rateUser

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.ExpandLess
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.component.common.NoTitleTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.profile.UserPhoto
import com.soyvictorherrera.nosedive.presentation.component.rate.RatingBar
import com.soyvictorherrera.nosedive.presentation.theme.Grey
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import timber.log.Timber
import kotlin.math.roundToInt

@Composable
fun RateUserContentView(
    modifier: Modifier = Modifier
) = Scaffold(modifier = modifier,
    topBar = {
        NoTitleTopAppBar(onNavigateBack = { /*TODO*/ })
    },
    content = {
        RateUserContent(
            modifier = Modifier.padding(
                start = 32.dp,
                end = 32.dp,
                top = 16.dp,
                bottom = 64.dp
            )
        )
    }
)

@Composable
fun RateUserContent(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    UserPhoto(
        painter = painterResource(id = R.drawable.ic_launcher_foreground),
        modifier = Modifier.size(162.dp)
    )

    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = "¿Cómo calificas a Jessica?",
        fontSize = 24.sp
    )

    Spacer(modifier = Modifier.weight(1f))

    var rating by remember { mutableStateOf(0) }
    RateUserBar(
        rating = rating,
        onRateChange = { newRate ->
            rating = newRate.also {
                Timber.d("new rate is [$it]")
            }
        },
        onRateSubmit = {
            Timber.d("Submit new rate here")
        }
    )
}

@Composable
fun RateUserBar(
    rating: Int,
    modifier: Modifier = Modifier,
    onRateChange: (newRate: Int) -> Unit,
    onRateSubmit: () -> Unit
) {
    var offsetY by remember { mutableStateOf(0f) }
    val threshold = with(LocalDensity.current) { 64.dp.toPx() * -1 }
    val dragProgress = (offsetY / threshold)
    val hintAlpha = (1f - dragProgress)
    Column(
        modifier = modifier
            .offset { IntOffset(0, offsetY.roundToInt()) }
            .draggable(
                orientation = Orientation.Vertical,
                state = rememberDraggableState { delta ->
                    val offset = offsetY + delta
                    // Allow drag upwards only
                    if (offset < 0) {
                        offsetY = (if (offset < threshold) threshold else offset)
                    }
                },
                onDragStopped = {
                    // Submit rating if view is full dragged
                    if (offsetY <= threshold) onRateSubmit()
                    offsetY = 0f
                }
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Sharp.ExpandLess,
            tint = Grey,
            contentDescription = "",
            modifier = Modifier
                .size(24.dp)
                .alpha(hintAlpha)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Selecciona y desliza hacia arriba",
            fontSize = 12.sp,
            color = Grey,
            modifier = Modifier.alpha(hintAlpha)
        )

        Spacer(modifier = Modifier.height(16.dp))

        RatingBar(
            rating = rating,
            modifier = Modifier
                .height(64.dp)
                .fillMaxWidth(),
            onRateChange = { onRateChange(it) }
        )

        Spacer(modifier = Modifier.height(8.dp))

        CircularProgressIndicator(
            progress = dragProgress,
            color = Grey,
            modifier = Modifier
                .size(32.dp)
                .alpha(dragProgress)
        )
    }
}

@Preview
@Composable
fun RateUserContentViewPreview() {
    NosediveTheme {
        RateUserContentView()
    }
}
