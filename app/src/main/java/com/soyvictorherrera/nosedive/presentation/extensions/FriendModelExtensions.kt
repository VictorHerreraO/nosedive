package com.soyvictorherrera.nosedive.presentation.extensions

import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.domain.model.UserModel


/**
 * Create a [UserModel] from this [FriendModel]
 */
@Throws(NullPointerException::class)
fun FriendModel.toUserModel(email: String = ""): UserModel {
    return UserModel(
        id = id,
        name = name,
        photoUrl = photoUrl,
        score = score,
        email = email
    )
}
