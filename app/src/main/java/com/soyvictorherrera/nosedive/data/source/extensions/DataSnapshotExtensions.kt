package com.soyvictorherrera.nosedive.data.source.extensions

import com.google.firebase.database.DataSnapshot

fun DataSnapshot.forEach(action: (snapshot: DataSnapshot) -> Unit) {
    for (child in children) {
        action(child)
    }
}
