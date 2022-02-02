package com.soyvictorherrera.nosedive.presentation.ui.ratingAlert

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.component.modifier.contentPadding
import com.soyvictorherrera.nosedive.presentation.component.profile.UserPhoto
import com.soyvictorherrera.nosedive.presentation.component.rate.SmallRatingBar
import com.soyvictorherrera.nosedive.presentation.extensions.toUri
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
fun RatingAlertContentView(
    user: UserModel,
    rater: FriendModel,
    modifier: Modifier = Modifier
) = Scaffold(
    modifier = modifier,
    content = {
        RatingAlertContent(
            user = user
        )
    }
)

@Composable
fun RatingAlertContent(
    user: UserModel,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier.contentPadding(top = 64.dp)
) {
    RatingAlertContentIntro(user = user)
}

@Composable
fun RatingAlertContentIntro(
    user: UserModel,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    ProminentUserScore(score = user.score ?: 0.0)

    Spacer(modifier = Modifier.height(64.dp))

    Text(
        text = "Has sido Calificado",
        fontSize = 72.sp,
        textAlign = TextAlign.Center
    )
}

@Composable
fun RatingAlertContentDescription(
    user: UserModel,
    rater: FriendModel,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally
) {
    ProminentUserScore(score = user.score ?: 0.0)

    Spacer(modifier = Modifier.height(64.dp))

    RaterPreview(friend = rater)

    Spacer(modifier = Modifier.weight(1f))

    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            modifier = Modifier.size(64.dp),
            imageVector = Icons.Default.Close,
            contentDescription = null
        )
    }
}

@Composable
fun ProminentUserScore(
    score: Double,
    modifier: Modifier = Modifier
) = Row(modifier = modifier) {
    val scoreString = "%.3f".format(score)
    val big = scoreString.substring(0, 3)
    val little = scoreString.substring(3, 5)

    Text(
        text = big,
        fontSize = 72.sp,
        modifier = Modifier.alignByBaseline()
    )
    Text(
        text = little,
        fontSize = 36.sp,
        modifier = Modifier.alignByBaseline()
    )
}

@Composable
fun RaterPreview(
    friend: FriendModel,
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier,
    horizontalAlignment = Alignment.CenterHorizontally
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        UserPhoto(
            painter = friend.photoUrl.let { url ->
                if (url != null) rememberImagePainter(url.toUri())
                else painterResource(id = R.drawable.ic_launcher_foreground)
            },
            modifier = Modifier.height(40.dp),
            borderWidth = 0.dp,
            borderStrokeWidth = 1.dp
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = friend.name,
                fontSize = 18.sp
            )
            friend.score?.let { score ->
                Text(text = "%.1f".format(score))
            }
        }
    }

    friend.score?.let { score ->
        Spacer(modifier = Modifier.height(16.dp))

        SmallRatingBar(
            modifier = Modifier.height(24.dp),
            rating = score.toInt()
        )
    }
}

@Preview
@Composable
private fun RatingAlertContentIntroPreview() {
    NosediveTheme {
        Scaffold {
            RatingAlertContentIntro(
                user = UserModel(
                    name = "Víctor Herrera",
                    email = "",
                    score = 4.29
                )
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
                user = UserModel(
                    name = "Víctor Herrera",
                    email = "",
                    score = 4.29
                ),
                rater = FriendModel(
                    id = "",
                    name = "Irais Herrera",
                    score = 4.251
                )
            )
        }
    }
}
