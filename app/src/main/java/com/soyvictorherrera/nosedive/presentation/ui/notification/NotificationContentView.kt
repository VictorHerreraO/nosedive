package com.soyvictorherrera.nosedive.presentation.ui.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.soyvictorherrera.nosedive.domain.model.NewFollowNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NewRatingNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NotificationModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.component.common.DefaultProminentTopAppBar
import com.soyvictorherrera.nosedive.presentation.component.item.NewFollowerNotificationListItem
import com.soyvictorherrera.nosedive.presentation.component.item.NewRatingNotificationItemList
import com.soyvictorherrera.nosedive.presentation.component.modifier.listPadding
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import java.time.LocalDateTime

sealed class NotificationEvent {
    object NavigateBack : NotificationEvent()
    data class FollowBack(val notification: NewFollowNotificationModel) : NotificationEvent()
    data class RateBack(val notification: NewRatingNotificationModel) : NotificationEvent()
}

@Composable
@ExperimentalMaterialApi
fun NotificationContentView(
    notificationList: List<NotificationModel>,
    modifier: Modifier = Modifier,
    onNotificationEvent: (event: NotificationEvent) -> Unit
): Unit = Scaffold(
    modifier = modifier,
    topBar = {
        DefaultProminentTopAppBar(
            title = "Notificaciones",
            navigationIcon = {
                IconButton(onClick = { onNotificationEvent(NotificationEvent.NavigateBack) }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        )
    },
    content = {
        if (notificationList.isEmpty()) {
            NotificationEmptyContent()
        } else NotificationContent(
            notificationList = notificationList,
            onNotificationEvent = onNotificationEvent
        )
    }
)

@Composable
fun NotificationEmptyContent(
    modifier: Modifier = Modifier
) = Column(
    modifier = modifier.fillMaxSize(),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally,
) {
    Text(
        text = "Aún no tienes notificaciones",
        style = MaterialTheme.typography.caption
    )
}

@Composable
@ExperimentalMaterialApi
fun NotificationContent(
    notificationList: List<NotificationModel>,
    modifier: Modifier = Modifier,
    onNotificationEvent: (event: NotificationEvent) -> Unit
) = LazyColumn(
    modifier = modifier.listPadding(),
    verticalArrangement = Arrangement.spacedBy(1.dp)
) {
    items(notificationList) { notification ->
        when (notification) {
            is NewFollowNotificationModel -> NewFollowerNotificationListItem(
                notification = notification,
                onFollowBackClick = {
                    onNotificationEvent(NotificationEvent.FollowBack(it))
                }
            )
            is NewRatingNotificationModel -> {
                NewRatingNotificationItemList(
                    notification = notification,
                    onRateBackClick = {
                        onNotificationEvent(NotificationEvent.RateBack(it))
                    }
                )
            }
        }
    }
}

@Preview
@Composable
@ExperimentalMaterialApi
private fun NotificationContentViewPreview() {
    NosediveTheme {
        NotificationContentView(
            notificationList = listOf(
                NewRatingNotificationModel(
                    id = "",
                    date = LocalDateTime.now(),
                    who = "",
                    ratingValue = 1
                ).apply {
                    user = UserModel(
                        id = "",
                        name = "Jessica Herrera",
                        email = ""
                    )
                },
                NewFollowNotificationModel(
                    id = "",
                    date = LocalDateTime.now(),
                    who = "",
                    seen = LocalDateTime.now()
                ).apply {
                    user = UserModel(
                        id = "",
                        name = "Jessica Herrera",
                        email = ""
                    )
                }
            )
        ) {}
    }
}

@Preview(name = "Empty list")
@Composable
@ExperimentalMaterialApi
private fun NotificationContentViewEmptyPreview() {
    NosediveTheme {
        NotificationContentView(
            notificationList = emptyList()
        ) {}
    }
}