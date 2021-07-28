package com.soyvictorherrera.nosedive.presentation.ui.profile

import android.net.Uri

sealed class ProfileState {
    object Idle : ProfileState()
    object Loading : ProfileState()
    object UpdatingPassword : ProfileState()
}

sealed class ProfilePhotoEvent {
    object RequestProfilePhotoChange : ProfilePhotoEvent()
    data class TakePhoto(val destinationUri: Uri) : ProfilePhotoEvent()
    object SelectPhoto : ProfilePhotoEvent()
}

sealed class ProfilePhotoState {
    data class Idle(val photoUri: Uri?) : ProfilePhotoState()
    data class Loading(val previewUri: Uri) : ProfilePhotoState()
}

sealed class ProfileError {
    object UserNotFound : ProfileError()
    object UnableToUploadPhoto : ProfileError()
    object UnableToChangePassword : ProfileError()
    object PasswordUpdatedSuccessfully : ProfileError() // notify user this way for now
}
