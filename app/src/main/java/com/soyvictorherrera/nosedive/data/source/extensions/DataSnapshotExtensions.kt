package com.soyvictorherrera.nosedive.data.source.extensions

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ktx.getValue
import com.soyvictorherrera.nosedive.data.source.IdentifiableEntity

fun DataSnapshot.forEach(action: (snapshot: DataSnapshot) -> Unit) {
    for (child in children) {
        action(child)
    }
}

/**
 * Convierte el contenido del DataSnapshot en un listado de POJOs
 *
 * Usa [IdentifiableEntity] para poder asignar el ID al pojo
 *
 * Únicamente agrega snpashots cuyo valor != null
 *
 * @see [DataSnapshot.getValue]
 */
inline fun <reified T : IdentifiableEntity> DataSnapshot.getValues(): List<T> {
    return mutableListOf<T>().apply {
        this@getValues.forEach { child ->
            child.getValue<T>()
                ?.apply value@{ this@value.id = child.key }
                ?.also { add(it) }
        }
    }
}
