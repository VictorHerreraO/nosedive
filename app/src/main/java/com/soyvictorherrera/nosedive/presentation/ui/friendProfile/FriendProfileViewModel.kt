package com.soyvictorherrera.nosedive.presentation.ui.friendProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveUserProfileUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FriendProfileViewModel @Inject constructor(
    private val observeUserProfileUseCase: ObserveUserProfileUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _friendModel = MutableLiveData<UserModel>()
    val friendModel: LiveData<UserModel>
        get() = _friendModel

    fun onUserIdChanged(userId: String) {
        viewModelScope.launch {
            observeUserProfileUseCase.apply {
                this.userId = userId
            }.execute { result ->
                when (result) {
                    is Result.Error -> {
                        Timber.e(result.exception)
                    }
                    Result.Loading -> {
                        Timber.d("onUserIdChanged: loading...")
                    }
                    is Result.Success -> {
                        _friendModel.value = result.data!!
                    }
                }
            }
        }
    }

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onRateUser() {

    }

}
