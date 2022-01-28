package com.soyvictorherrera.nosedive.presentation.ui.ratingAlert

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.soyvictorherrera.nosedive.presentation.component.modifier.contentPadding
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun RatingAlertContentView(
    modifier: Modifier = Modifier
) = Scaffold(
    modifier = modifier,
    content = {
        RatingAlertContent(

        )
    }
)

@Composable
fun RatingAlertContent(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier.contentPadding(top = 64.dp)
) {
    RatingAlertContentIntro()
}

@Composable
fun RatingAlertContentIntro(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Row {
        Text(
            text = "4.2",
            fontSize = 48.sp,
            modifier = Modifier.alignByBaseline()
        )
        Text(
            text = "51",
            fontSize = 18.sp,
            modifier = Modifier.alignByBaseline()
        )
    }

    Spacer(modifier = Modifier.height(64.dp))

    Text(
        text = "Has sido Calificado",
        fontSize = 48.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun RatingAlertContentDescription() {

}

@Preview
@Composable
private fun RatingAlertContentIntroPreview() {
    NosediveTheme {
       Scaffold {
           RatingAlertContentIntro(

           )
       }
    }
}


@Preview
@Composable
private fun RatingAlertContentDescriptionPreview() {
    NosediveTheme {
        Scaffold {
            RatingAlertContentDescription(

            )
        }
    }
}
