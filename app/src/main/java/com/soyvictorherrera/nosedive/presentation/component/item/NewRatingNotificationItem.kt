package com.soyvictorherrera.nosedive.presentation.component.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.NewRatingNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NotificationModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.component.profile.UserPhoto
import com.soyvictorherrera.nosedive.presentation.component.rate.SmallRatingBar
import com.soyvictorherrera.nosedive.presentation.extensions.describeDuration
import com.soyvictorherrera.nosedive.presentation.extensions.toUri
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import java.time.LocalDateTime

@Composable
@ExperimentalMaterialApi
fun NewRatingNotificationItemList(
    notification: NewRatingNotificationModel,
    modifier: Modifier = Modifier,
    onItemClick: (notification: NotificationModel) -> Unit,
    onRateBackClick: (notification: NewRatingNotificationModel) -> Unit,
) = Card(
    modifier = modifier,
    shape = MaterialTheme.shapes.medium,
    backgroundColor = MaterialTheme.colors.surface,
    elevation = 2.dp,
    onClick = { onItemClick(notification) }
) {
    val who = notification.user!!
    ListItem(
        icon = {
            UserPhoto(
                modifier = Modifier.height(40.dp),
                painter = who.photoUrl.let { url ->
                    if (url != null) rememberImagePainter(url.toUri())
                    else painterResource(R.drawable.ic_launcher_foreground)
                },
                borderWidth = 0.dp,
                borderStrokeWidth = 1.dp
            )
        },
        text = {
            val dismissed = notification.seen != null
            val name = if (dismissed) who.name
            else "* ${who.name}"

            Text(
                text = "$name te ha calificado",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        secondaryText = {
            SmallRatingBar(
                rating = notification.ratingValue,
                modifier = Modifier
                    .height(18.dp)
                    .fillMaxWidth(0.5f)
            )
        },
        trailing = {
            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = notification.date.describeDuration(
                        context = LocalContext.current
                    ),
                    fontSize = 10.sp
                )
                TextButton(
                    onClick = { onRateBackClick(notification) }
                ) {
                    Text(
                        text = "Calificar",
                        fontSize = 10.sp
                    )
                }
            }
        }
    )
}

@Preview
@Composable
@ExperimentalMaterialApi
fun NewRatingNotificationItemListPreview() {
    NosediveTheme {
        NewRatingNotificationItemList(
            notification = NewRatingNotificationModel(
                id = "",
                date = LocalDateTime.now(),
                who = "",
                seen = LocalDateTime.now(),
                ratingValue = 1,
            ).apply {
                user = UserModel(
                    id = "",
                    name = "Jessica Herrera",
                    email = ""
                )
            },
            onItemClick = {},
            onRateBackClick = {}
        )
    }
}

@Preview
@Composable
@ExperimentalMaterialApi
fun NewRatingNotificationItemListPendingPreview() {
    NosediveTheme {
        NewRatingNotificationItemList(
            notification = NewRatingNotificationModel(
                id = "",
                date = LocalDateTime.now(),
                who = "",
                seen = null,
                ratingValue = 1
            ).apply {
                user = UserModel(
                    id = "",
                    name = "Jessica Herrera",
                    email = ""
                )
            },
            onItemClick = {},
            onRateBackClick = {}
        )
    }
}
