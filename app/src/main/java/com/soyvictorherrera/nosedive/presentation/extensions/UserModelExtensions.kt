package com.soyvictorherrera.nosedive.presentation.extensions

import com.soyvictorherrera.nosedive.domain.model.FriendModel
import com.soyvictorherrera.nosedive.domain.model.UserModel

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
