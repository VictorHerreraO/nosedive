package com.soyvictorherrera.nosedive.presentation.extensions

import android.net.Uri
import java.net.URI

/**
 * Creates an [Uri] from this [URI]
 *
 * @author Víctor Herrera
 */
fun URI.toUri(): Uri {
    return Uri.parse(this.toString())
}
