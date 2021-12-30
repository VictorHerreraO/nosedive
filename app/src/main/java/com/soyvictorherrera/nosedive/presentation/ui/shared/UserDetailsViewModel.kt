package com.soyvictorherrera.nosedive.presentation.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveUserFriendListUseCase
import com.soyvictorherrera.nosedive.util.PreferenceUtil
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * @author Víctor Herrera
 */
@HiltViewModel
class UserDetailsViewModel @Inject constructor(
    private val preferences: PreferenceUtil,
    private val observeUserFriendListUseCase: ObserveUserFriendListUseCase
) : ViewModel() {

    private val _friendList = MutableLiveData<List<FriendModel>>()
    val friendList: LiveData<List<FriendModel>>
        get() = _friendList

    private val jobs = mutableListOf<Job>()

    init {
        Timber.v("new instance")
        observeUserIdPreference()
    }

    private fun observeUserIdPreference() {
        preferences.observeUserId(null).onEach { id ->
            cancelJobs()
            id?.let { onUserIdChanged(it) }
        }.launchIn(viewModelScope)
    }

    private fun cancelJobs() {
        jobs.apply {
            forEach { it.cancel() }
            clear()
        }
    }

    private fun onUserIdChanged(userId: String) {
        Timber.i("user id changed")
        observeUserFriendList(userId).also { jobs.add(it) }
    }

    private fun observeUserFriendList(userId: String): Job = viewModelScope.launch {
        observeUserFriendListUseCase.apply {
            this.userId = userId
        }.execute { result ->
            when (result) {
                is Result.Error -> Timber.e(result.exception)
                Result.Loading -> Unit
                is Result.Success -> {
                    Timber.i("user friend list updated")
                    _friendList.value = result.data!!
                }
            }
        }
    }


}
