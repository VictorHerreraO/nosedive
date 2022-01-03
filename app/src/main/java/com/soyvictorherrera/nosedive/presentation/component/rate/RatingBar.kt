package com.soyvictorherrera.nosedive.presentation.component.rate

import androidx.compose.foundation.gestures.*
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Star
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.presentation.theme.Dark_Pink
import com.soyvictorherrera.nosedive.presentation.theme.Grey
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.theme.Wild_Watermelon
import timber.log.Timber

@Composable
@ExperimentalMaterialApi
fun RatingBar(
    rating: Int,
    modifier: Modifier = Modifier,
    maxValue: Int = 5
) {
    // TODO: 03/01/2022 cambiar esto por draggable para tener acceso a los valores
    //  https://developer.android.com/jetpack/compose/gestures#dragging
    val swipeableState = rememberSwipeableState(0)
    var size by remember { mutableStateOf(Size.Zero) }
    val anchors = mapOf(0f to 0, size.width to 1)
    Row(
        modifier
            .onSizeChanged {
                size = Size(width = it.width.toFloat(), height = it.height.toFloat())
            }
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                orientation = Orientation.Horizontal
            )
    ) {
        (1..maxValue).forEach { step ->
            RatingStar(
                filled = step <= rating,
                modifier = Modifier
                    .size(64.dp)
                    .padding(all = 8.dp)
            )
        }
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
            rating = 4
        )
    }
}
