package com.soyvictorherrera.nosedive.presentation.extensions

import android.net.Uri
import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.domain.model.UserModel

/**
 * Devolver photoUrl como [android.net.Uri]
 */
fun UserModel.getPhotoUri(): Uri? {
    return this.photoUrl?.let { uri ->
        Uri.parse(uri.toString())
    }
}

/**
 * Create a [FriendModel] from this [UserModel]
 *
 * @throws NullPointerException in case [UserModel.id] is null
 */
@Throws(NullPointerException::class)
fun UserModel.toFriendModel(): FriendModel {
    return FriendModel(
        id = id!!,
        name = name,
        photoUrl = photoUrl
    )
}
