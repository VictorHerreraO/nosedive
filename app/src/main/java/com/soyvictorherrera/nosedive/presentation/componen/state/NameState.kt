package com.soyvictorherrera.nosedive.presentation.componen.state

const val MIN_NAME_LENGTH = 1
const val MAX_NAME_LENGTH = 40

class NameState(errorFor: (name: String) -> String) : TextFieldState(
    validator = ::isNameValid,
    errorFor = errorFor
)

private fun isNameValid(name: String) = name.length in MIN_NAME_LENGTH until MAX_NAME_LENGTH
