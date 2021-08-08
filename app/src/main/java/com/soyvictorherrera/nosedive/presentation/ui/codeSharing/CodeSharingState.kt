package com.soyvictorherrera.nosedive.presentation.ui.codeSharing

import android.net.Uri

sealed class ImageCodeState {
    object Loading : ImageCodeState()
    data class Generated(val codeUri: Uri) : ImageCodeState()
    object Error : ImageCodeState()
}

sealed class TextCodeState {
    object None: TextCodeState()
    object Loading : TextCodeState()
    data class Generated(val code: String) : TextCodeState()
    object Error : TextCodeState()
}
