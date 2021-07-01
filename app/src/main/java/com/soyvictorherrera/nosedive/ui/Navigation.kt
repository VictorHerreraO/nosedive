package com.soyvictorherrera.nosedive.ui

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.soyvictorherrera.nosedive.R

enum class Screen { SignIn, SignUp, Home }

fun Fragment.navigate(to: Screen, from: Screen) {
    if (to == from) throw IllegalArgumentException("Can't navigate to ")

    when (to) {
        Screen.SignIn -> {
            findNavController().navigate(R.id.signInFragment)
        }
        Screen.SignUp -> {
            findNavController().navigate(R.id.signUpFragment)
        }
        Screen.Home -> {
            TODO("Not implemented")
        }
    }
}
