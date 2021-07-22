package com.soyvictorherrera.nosedive.presentation.ui.profile

import android.net.Uri

sealed class ProfilePhotoEvent {
    object RequestProfilePhotoChange : ProfilePhotoEvent()
    data class UpdateProfilePhoto(val uri: Uri): ProfilePhotoEvent()
}
