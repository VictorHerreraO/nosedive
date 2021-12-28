package com.soyvictorherrera.nosedive.data.source.userStats

import com.google.firebase.database.Exclude

data class UserStatsEntity(
    @get:Exclude var userId: String? = null,
    var following: Int? = null,
    var followers: Int? = null,
    var ratings: Int? = null
)
