package com.soyvictorherrera.nosedive.presentation.ui.profile

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.domain.usecase.user.UpdateUserPhotoUseCase
import com.soyvictorherrera.nosedive.domain.usecase.user.UpdateUserPasswordUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.FileUtil
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import java.net.URI
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val updateUserPhotoUseCase: UpdateUserPhotoUseCase,
    private val updateUserPasswordUseCase: UpdateUserPasswordUseCase,
    private val fileUtil: FileUtil
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel>
        get() = _user

    private val _profileState = MutableLiveData<ProfileState>(ProfileState.Idle)
    val profileState: LiveData<ProfileState>
        get() = _profileState

    private val _profilePhotoEvent = MutableLiveData<Event<ProfilePhotoEvent>>()
    val profilePhotoEvent: LiveData<Event<ProfilePhotoEvent>>
        get() = _profilePhotoEvent

    private val _profilePhotoState = MutableLiveData<ProfilePhotoState>()
    val profilePhotoState: LiveData<ProfilePhotoState>
        get() = _profilePhotoState

    private val _profileError = MutableLiveData<Event<ProfileError>>()
    val profileError: LiveData<Event<ProfileError>>
        get() = _profileError

    private var workingUri: Uri? = null

    init {
        viewModelScope.launch {
            observeCurrentUserUseCase.execute { result ->
                when (result) {
                    is Result.Success -> {
                        val user = result.data
                        _user.value = user
                        _profilePhotoState.value =
                            ProfilePhotoState.Idle(photoUri = user.photoUrl?.let { Uri.parse(it.toString()) })
                    }
                    is Result.Error -> {
                        _profileError.value = Event(ProfileError.UserNotFound)
                        Timber.e(result.exception, "")
                    }
                    Result.Loading -> {
                        Timber.d("loading")
                    }
                }
            }
        }
    }

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onUpdateUserPassword(password: String, newPassword: String) {
        viewModelScope.launch {
            updateUserPasswordUseCase.apply {
                this.password = password
                this.newPassword = newPassword
            }.execute { result ->
                when (result) {
                    is Result.Success -> {
                        _profileError.value = Event(ProfileError.PasswordUpdatedSuccessfully)
                        _profileState.value = ProfileState.Idle
                    }
                    is Result.Error -> {
                        Timber.e(result.exception, "error by:")
                        _profileError.value = Event(ProfileError.UnableToChangePassword)
                        _profileState.value = ProfileState.Idle
                    }
                    Result.Loading -> {
                        _profileState.value = ProfileState.UpdatingPassword
                    }
                }
            }
        }
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
        Timber.d("photo located at {$workingUri}")
        workingUri?.let { uri ->
            updateProfilePhoto(uri)
        }
    }

    fun onUserPhotoSelectedSuccessfully(fileUri: Uri) {
        Timber.d("file located at {$fileUri}")
        updateProfilePhoto(fileUri)
    }

    private fun updateProfilePhoto(fileUri: Uri) {
        viewModelScope.launch {
            updateUserPhotoUseCase.apply {
                this.fileUri = URI(fileUri.toString())
            }.execute { result ->
                when (result) {
                    is Result.Success -> {
                        val uriString = result.data.toString()
                        Timber.d("upload result {$uriString}")
                        _profilePhotoState.value = ProfilePhotoState.Idle(
                            photoUri = Uri.parse(uriString)
                        )
                    }
                    is Result.Error -> {
                        Timber.e(result.exception, "error by: ")
                        _profilePhotoState.value = ProfilePhotoState.Idle(null)
                        _profileError.value = Event(ProfileError.UnableToUploadPhoto)
                    }
                    Result.Loading -> {
                        _profilePhotoState.value = ProfilePhotoState.Loading(previewUri = fileUri)
                    }
                }
            }
        }
    }

}
