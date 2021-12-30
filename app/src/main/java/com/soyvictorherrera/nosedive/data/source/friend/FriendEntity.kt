package com.soyvictorherrera.nosedive.data.source.friend.firebase

import com.google.firebase.database.Exclude

data class FriendEntity(
    @get:Exclude var id: String? = null,
    var name: String? = null,
    var photoUrl: String? = null,
    var lastRated: Long? = null
)
