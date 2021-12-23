package com.soyvictorherrera.nosedive.presentation.extensions

import android.net.Uri
import com.soyvictorherrera.nosedive.domain.model.UserModel

/**
 * Devolver photoUrl como [android.net.Uri]
 */
fun UserModel.getPhotoUri(): Uri? {
    return this.photoUrl?.let { uri ->
        Uri.parse(uri.toString())
    }
}
