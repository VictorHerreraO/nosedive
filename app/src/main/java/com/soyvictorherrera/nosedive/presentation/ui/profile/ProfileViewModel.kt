package com.soyvictorherrera.nosedive.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel>
        get() = _user

    private val _profilePhotoEvent = MutableLiveData<Event<ProfilePhotoEvent>>()
    val profilePhotoEvent: LiveData<Event<ProfilePhotoEvent>>
        get() = _profilePhotoEvent

    init {
        viewModelScope.launch {
            observeCurrentUserUseCase.execute { result ->
                when (result) {
                    is Result.Success -> {
                        _user.value = result.data!!
                    }
                    is Result.Error -> {
                        Log.e(TAG, "", result.exception)
                    }
                    Result.Loading -> {
                        Log.d(TAG, "loading")
                    }
                }
            }
        }
    }

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onUpdateUserProfilePhoto() {
        _profilePhotoEvent.value = Event(ProfilePhotoEvent.RequestProfilePhotoChange)
    }

    fun onUpdateUserPassword(newPassword: String) {

    }

}
