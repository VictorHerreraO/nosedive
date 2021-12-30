package com.soyvictorherrera.nosedive.presentation.component.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.presentation.component.profile.UserPhoto
import com.soyvictorherrera.nosedive.presentation.extensions.toUri
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.theme.Wild_Watermelon

private const val SCORE_FORMAT = "%.1f"

@Composable
@ExperimentalMaterialApi
fun FriendListItem(
    user: FriendModel,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier,
): Unit = Card(
    modifier = modifier.clickable(onClick = onItemClick),
    shape = MaterialTheme.shapes.medium,
    backgroundColor = MaterialTheme.colors.surface,
    elevation = 2.dp,
) {
    ListItem(
        icon = {
            UserPhoto(
                painter = user.photoUrl.let { url ->
                    if (url != null) rememberImagePainter(url.toUri())
                    else painterResource(R.drawable.ic_launcher_foreground)
                },
                modifier = Modifier.size(40.dp),
                borderWidth = 0.dp,
                borderStrokeWidth = 1.dp
            )
        },
        text = {
            Text(
                text = user.name,
                color = MaterialTheme.colors.onSurface,
                style = MaterialTheme.typography.body1
            )
        },
        trailing = {
            val score = user.score
            if (score != null) {
                Text(
                    text = SCORE_FORMAT.format(score),
                    color = Wild_Watermelon,
                    fontSize = 20.sp
                )
            }
        }
    )
}

@Preview
@Composable
@ExperimentalMaterialApi
fun FriendListItemPreview() {
    NosediveTheme {
        FriendListItem(
            user = FriendModel(
                id = "",
                name = "Jessica Herrera",
                score = 4.333
            ),
            onItemClick = {}
        )
    }
}
