package com.soyvictorherrera.nosedive.presentation.ui.rateUser

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
class RateUserViewModel @Inject constructor(
    private val observeUserProfileUseCase: ObserveUserProfileUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> get() = _navigateTo

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> get() = _user

    private val _currentRating = MutableLiveData<Int>()
    val currentRating: LiveData<Int> get() = _currentRating

    private lateinit var userId: String

    fun onUserIdChanged(userId: String) {
        this.userId = userId

        observeUserProfile(userId)
    }

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onRateChanged(newRate: Int) {
        _currentRating.value = newRate
    }

    fun onSubmitRating() {

    }

    private fun observeUserProfile(userId: String) {
        viewModelScope.launch {
            observeUserProfileUseCase.apply {
                this.userId = userId
            }.execute { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    Result.Loading -> Unit
                    is Result.Success -> {
                        _user.value = result.data!!
                    }
                }
            }
        }
    }

}
