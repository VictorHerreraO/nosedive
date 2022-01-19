package com.soyvictorherrera.nosedive.presentation.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.NewFollowNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NewRatingNotificationModel
import com.soyvictorherrera.nosedive.domain.model.NotificationModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.notification.DismissNotificationUseCase
import com.soyvictorherrera.nosedive.domain.usecase.notification.ObserveUserNotificationListUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val observeUserNotificationListUseCase: ObserveUserNotificationListUseCase,
    private val dismissNotificationUseCase: DismissNotificationUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> get() = _navigateTo

    private val _notificationList = MutableLiveData<List<NotificationModel>>()
    val notificationList: LiveData<List<NotificationModel>> get() = _notificationList

    private var userId: String? = null

    fun onUserChanged(user: UserModel) {
        userId = user.id
        observeUserNotificationList(userId = userId!!)
    }

    private fun observeUserNotificationList(userId: String) {
        viewModelScope.launch {
            observeUserNotificationListUseCase.apply {
                this.userId = userId
            }.execute { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    Result.Loading -> Unit
                    is Result.Success -> result.data.let { list ->
                        _notificationList.value = list.sortedByDescending {
                            it.date
                        }
                    }
                }
            }
        }
    }

    fun navigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onFollowBack(notification: NewFollowNotificationModel) {
        dismissNotification(notificationId = notification.id)
        _navigateTo.value = Event(Screen.FriendProfile(userId = notification.who))
    }

    fun onRateBack(notification: NewRatingNotificationModel) {
        dismissNotification(notificationId = notification.id)
        _navigateTo.value = Event(Screen.RateUser(userId = notification.who))
    }

    fun onNotificationClick(notification: NotificationModel) {
        when (notification) {
            is NewFollowNotificationModel -> Unit
            is NewRatingNotificationModel -> {
                // When notification clicked navigate to user profile
                onFollowBack(
                    notification = NewFollowNotificationModel(
                        id = notification.id,
                        date = notification.date,
                        who = notification.who,
                        seen = notification.seen
                    )
                )
            }
        }
    }

    private fun dismissNotification(notificationId: String) {
        viewModelScope.launch {
            dismissNotificationUseCase.apply {
                this.userId = this@NotificationViewModel.userId
                this.notificationId = notificationId
            }.execute().let { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    Result.Loading -> Unit
                    is Result.Success -> Timber.d("listo!")
                }
            }
        }
    }

}
