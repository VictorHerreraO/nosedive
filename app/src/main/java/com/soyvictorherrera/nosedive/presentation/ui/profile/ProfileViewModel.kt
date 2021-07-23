package com.soyvictorherrera.nosedive.presentation.ui.profile

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.domain.usecase.UpdateProfilePhotoUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import com.soyvictorherrera.nosedive.util.FileUtil
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val updateProfilePhotoUseCase: UpdateProfilePhotoUseCase,
    private val fileUtil: FileUtil
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

    private val _profilePhotoState = MutableLiveData<ProfilePhotoState>()
    val profilePhotoState: LiveData<ProfilePhotoState>
        get() = _profilePhotoState

    private var workingUri: Uri? = null

    init {
        viewModelScope.launch {
            observeCurrentUserUseCase.execute { result ->
                when (result) {
                    is Result.Success -> {
                        val user = result.data!!
                        _user.value = user
                        _profilePhotoState.value = ProfilePhotoState.Idle(photoUri = user.photoUrl)
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

    fun onUpdateUserPassword(newPassword: String) {

    }

    fun onUpdateUserProfilePhoto() {
        _profilePhotoEvent.value = Event(ProfilePhotoEvent.RequestProfilePhotoChange)
    }

    fun onSelectPhotoFromCamera() {
        val photoDestination = Uri.parse(fileUtil.getTmpImageUri().toString()).also {
            workingUri = it
        }
        _profilePhotoEvent.value = Event(
            ProfilePhotoEvent.TakePhoto(
                destinationUri = photoDestination
            )
        )
    }

    fun onSelectPhotoFromGallery() {
        _profilePhotoEvent.value = Event(
            ProfilePhotoEvent.SelectPhoto
        )
    }

    fun onUserPhotoTakenSuccessfully() {
        Log.d(TAG, "photo located at {$workingUri}")
        workingUri?.let { uri ->
            updateProfilePhoto(uri)
        }
    }

    fun onUserPhotoSelectedSuccessfully(fileUri: Uri) {
        Log.d(TAG, "file located at {$fileUri}")
        updateProfilePhoto(fileUri)
    }

    private fun updateProfilePhoto(fileUri: Uri) {
        _profilePhotoState.value = ProfilePhotoState.Loading(previewUri = fileUri)

        viewModelScope.launch {
            updateProfilePhotoUseCase.apply {
                this.fileUri = URI(fileUri.toString())
            }.execute { result ->
                when (result) {
                    is Result.Success -> {
                        _profilePhotoState.value = ProfilePhotoState.Idle(
                            photoUri = Uri.parse(result.data.toString())
                        )
                    }
                    is Result.Error -> {
                        _profilePhotoState.value = ProfilePhotoState.Idle(null)
                    }
                    Result.Loading -> {
                        /* Do nothing */
                    }
                }
            }
        }
    }

}
