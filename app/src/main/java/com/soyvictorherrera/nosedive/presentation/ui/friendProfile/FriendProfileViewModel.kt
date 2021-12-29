package com.soyvictorherrera.nosedive.presentation.ui.friendProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.model.UserScoreModel
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveUserProfileUseCase
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveUserScoreUseCase
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveUserStatsUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FriendProfileViewModel @Inject constructor(
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val observeUserStatsUseCase: ObserveUserStatsUseCase,
    private val observeUserScoreUseCase: ObserveUserScoreUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _friendModel = MutableLiveData<UserModel>()
    val friendModel: LiveData<UserModel>
        get() = _friendModel

    private val _friendStats = MutableLiveData<UserStatsModel>()
    val friendStats: LiveData<UserStatsModel>
        get() = _friendStats

    private val _friendAvgScore = MutableLiveData<Double?>()
    val friendAvgScore: LiveData<Double?>
        get() = _friendAvgScore

    fun onUserIdChanged(userId: String) {
        observeUserProfile(userId)
        observeUserStats(userId)
        observeUserScore(userId)
    }

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onRateUser() {

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
                        Timber.i("friend profile updated")
                        _friendModel.value = result.data!!
                    }
                }
            }
        }
    }

    private fun observeUserStats(userId: String) {
        viewModelScope.launch {
            observeUserStatsUseCase.apply {
                this.userId = userId
            }.execute { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    Result.Loading -> Unit
                    is Result.Success -> {
                        Timber.i("friend stats updated")
                        _friendStats.value = result.data!!
                    }
                }
            }
        }
    }

    private fun observeUserScore(userId: String) {
        viewModelScope.launch {
            observeUserScoreUseCase.apply {
                this.userId = userId
            }.execute { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    Result.Loading -> Unit
                    is Result.Success -> {
                        Timber.i("friend score updated")
                        val score = result.data
                        _friendAvgScore.value = if (score.count >= UserScoreModel.REQUIRED_COUNT) {
                            score.average
                        } else null
                    }
                }
            }
        }
    }

}
