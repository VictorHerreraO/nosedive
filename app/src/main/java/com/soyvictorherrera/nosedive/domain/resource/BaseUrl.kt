package com.soyvictorherrera.nosedive.domain.resource

import java.io.Serializable
import java.net.URI

class BaseUrl(val value: URI): Serializable {

    fun append(path: String): URI {
        return value.resolve(path)
    }

}
