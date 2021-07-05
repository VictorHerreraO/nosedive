package com.soyvictorherrera.nosedive.data.source.factory

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

object FirebaseReferenceFactory {
    private val database = FirebaseDatabase.getInstance()

    fun getUserReference(): DatabaseReference {
        return database.getReference("user")
    }

}
