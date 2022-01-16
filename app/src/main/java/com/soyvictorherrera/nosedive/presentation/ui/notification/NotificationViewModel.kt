package com.soyvictorherrera.nosedive.presentation.ui.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.NotificationModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.notification.ObserveUserNotificationListUseCase
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val observeUserNotificationListUseCase: ObserveUserNotificationListUseCase
) : ViewModel() {

    private val _notificationList = MutableLiveData<List<NotificationModel>>()
    val notificationList: LiveData<List<NotificationModel>> get() = _notificationList

    fun onUserChanged(user: UserModel) {
        observeUserNotificationList(userId = user.id!!)
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

}
