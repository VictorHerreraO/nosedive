package com.soyvictorherrera.nosedive.presentation.ui.rateUser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.RatingModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.rating.RateUserUseCase
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveUserProfileUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class RateUserViewModel @Inject constructor(
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val rateUserUseCase: RateUserUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>> get() = _navigateTo

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel> get() = _user

    private val _currentRating = MutableLiveData<Int>()
    val currentRating: LiveData<Int> get() = _currentRating

    private val _rateUserState = MutableLiveData<RateUserState>()
    val rateUserState: LiveData<RateUserState> get() = _rateUserState

    private lateinit var userId: String
    private lateinit var currentUser: UserModel

    fun onUserIdChanged(userId: String) {
        this.userId = userId

        observeUserProfile(userId)
    }

    fun onCurrentUserChanged(user: UserModel) {
        currentUser = user
    }

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onRateChanged(newRate: Int) {
        _currentRating.value = newRate
    }

    fun onSubmitRating() {
        val rating = _currentRating.value ?: 0
        if (rating <= 0) {
            return Timber.e("[rating] must be greater than 0")
        }
        rateUser(
            ratedUserId = userId,
            currentUserId = currentUser.id!!,
            rating = rating
        )
    }

    private fun observeUserProfile(userId: String) {
        viewModelScope.launch {
            _rateUserState.value = RateUserState.Loading
            observeUserProfileUseCase.apply {
                this.userId = userId
            }.execute { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    Result.Loading -> _rateUserState.value = RateUserState.Loading
                    is Result.Success -> result.data.let {
                        _user.value = it
                        _rateUserState.value = RateUserState.Idle
                    }
                }
            }
        }
    }

    private fun rateUser(
        ratedUserId: String,
        currentUserId: String,
        rating: Int
    ) {
        _rateUserState.value = RateUserState.Loading
        viewModelScope.launch {
            rateUserUseCase.apply {
                this.ratedUserId = ratedUserId
                this.rating = RatingModel(
                    id = "",
                    value = rating,
                    date = Instant.now().toEpochMilli(),
                    who = currentUserId,
                    multiplier = 1f
                )
            }.execute().let { result ->
                _rateUserState.value = RateUserState.Idle
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    Result.Loading -> Unit
                    is Result.Success -> {
                        onNavigateBack()
                    }
                }
            }
        }
    }

}
