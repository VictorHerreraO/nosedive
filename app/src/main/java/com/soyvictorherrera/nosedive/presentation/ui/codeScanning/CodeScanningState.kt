package com.soyvictorherrera.nosedive.presentation.ui.codeScanning

sealed class TextCodeInputState {
    object Idle : TextCodeInputState()
    object Loading : TextCodeInputState()
    data class Error(val error: TextCodeError) : TextCodeInputState()
    class Ready(
        code: String,
        private val onCodeChange: (value: String) -> Unit = {}
    ) : TextCodeInputState() {
        var code = code
            set(value) {
                field = value
                onCodeChange(value)
            }
    }
}

sealed class TextCodeError {
    object Unknown : TextCodeError()
    object NotFound : TextCodeError()
}

sealed class CodeScanState {
    object Idle: CodeScanState()
    object Active: CodeScanState()
    object Error: CodeScanState()
}
