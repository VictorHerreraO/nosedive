package com.soyvictorherrera.nosedive.presentation.ui.friendProfile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel
import com.soyvictorherrera.nosedive.domain.usecase.friend.AddUserFriendUseCase
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveUserProfileUseCase
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveUserStatsUseCase
import com.soyvictorherrera.nosedive.presentation.extensions.toFriendModel
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.PreferenceUtil
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FriendProfileViewModel @Inject constructor(
    private val preferences: PreferenceUtil,
    private val observeUserProfileUseCase: ObserveUserProfileUseCase,
    private val observeUserStatsUseCase: ObserveUserStatsUseCase,
    private val addUserFriendUseCase: AddUserFriendUseCase
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

    private val _canFollowFriend = MutableLiveData<Boolean>()
    val canFollowFriend: LiveData<Boolean>
        get() = _canFollowFriend

    private lateinit var friendUserId: String
    private var currentUserFriendList = emptyList<FriendModel>()
        set(value) {
            field = value
            onCurrentUserFriendListUpdated(value)
        }

    fun onFriendUserIdChanged(userId: String) {
        this.friendUserId = userId

        observeFriendUserProfile(userId)
        observeFriendUserStats(userId)
    }

    fun onCurrentUserFriendListChanged(friendList: List<FriendModel>) {
        this.currentUserFriendList = friendList
    }

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onRateUser() {
        _navigateTo.value = Event(Screen.RateUser(userId = friendUserId))
    }

    fun onFollowUser() {
        addUserFriend()
    }

    private fun observeFriendUserProfile(userId: String) {
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

    private fun observeFriendUserStats(userId: String) {
        viewModelScope.launch {
            observeUserStatsUseCase.apply {
                this.userId = userId
            }.execute { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    Result.Loading -> Unit
                    is Result.Success -> result.data.let { stats ->
                        Timber.i("friend stats updated")
                        _friendStats.value = stats
                        _friendAvgScore.value = if (stats.hasRequiredRatingsCount) {
                            stats.averageScore
                        } else null
                    }
                }
            }
        }
    }

    private fun onCurrentUserFriendListUpdated(friendList: List<FriendModel>) {
        friendList.asSequence()
            .filter { !it.anonymous }
            .firstOrNull { it.id == friendUserId }
            .let {
                _canFollowFriend.value = it == null
            }
    }

    private fun addUserFriend() {
        val canFollow = canFollowFriend.value ?: return
        val model = friendModel.value ?: return

        if (!canFollow) return

        viewModelScope.launch {
            addUserFriendUseCase.apply {
                this.userId = preferences.getUserId()
                this.friend = model.toFriendModel()
            }.execute().let { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    Result.Loading -> Unit
                    is Result.Success -> {
                        Timber.i("now following [${model.id}]")
                    }
                }
            }
        }
    }

}
