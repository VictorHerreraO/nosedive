package com.soyvictorherrera.nosedive.presentation.component.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.presentation.component.profile.UserPhoto
import com.soyvictorherrera.nosedive.presentation.extensions.toUri
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun RateFriendItem(
    friend: FriendModel,
    onItemClick: () -> Unit,
    modifier: Modifier = Modifier
): Unit = Card(
    modifier = modifier.clickable(onClick = onItemClick),
    backgroundColor = MaterialTheme.colors.surface,
    elevation = 2.dp,
    shape = MaterialTheme.shapes.medium
) {
    ListItem(
        modifier = modifier,
        icon = {
            UserPhoto(
                modifier = Modifier.size(40.dp),
                borderWidth = 0.dp,
                borderStrokeWidth = 1.dp,
                painter = friend.photoUrl.let { url ->
                    if (url != null) rememberImagePainter(url.toUri())
                    else painterResource(R.drawable.ic_launcher_foreground)
                }
            )
        },
        text = {
            Text(text = friend.name)
        },
        secondaryText = {

        },
        trailing = {
            IconButton(
                onClick = { onItemClick() }
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    )
}

@Preview
@Composable
@ExperimentalMaterialApi
fun RateFriendItemPreview() {
    NosediveTheme {
        RateFriendItem(
            friend = FriendModel(
                id = "",
                name = "Jessica Herrera"
            ),
            onItemClick = {}
        )
    }
}
