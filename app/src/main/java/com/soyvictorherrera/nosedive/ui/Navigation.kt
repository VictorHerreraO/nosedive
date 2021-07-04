package com.soyvictorherrera.nosedive.ui

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.soyvictorherrera.nosedive.R

enum class Screen { SignIn, SignUp, Home }

fun Fragment.navigateInTo(to: Screen, from: Screen) {
    if (to == from) throw IllegalArgumentException("Can't navigate in to $to")

    when (to) {
        Screen.SignIn -> {
            findNavController().navigate(
                resId = R.id.signInFragment,
                args = null,
                navOptions = defaultNavInOptions()
            )
        }
        Screen.SignUp -> {
            findNavController().navigate(
                resId = R.id.signUpFragment,
                args = null,
                navOptions = defaultNavInOptions()
            )
        }
        Screen.Home -> {
            TODO("Not implemented")
        }
    }
}

fun Fragment.navigateOutTo(to: Screen, from: Screen) {
    if (to == from) throw IllegalArgumentException("Can't navigate out to $to")

    when (to) {
        Screen.SignIn -> {
            findNavController().navigate(
                resId = 0,
                args = null,
                navOptions = defaultNavOutOptions(target = R.id.signInFragment)
            )
        }
        Screen.SignUp -> {
            findNavController().navigate(
                resId = 0,
                args = null,
                navOptions = defaultNavOutOptions(target = R.id.signUpFragment)
            )
        }
        Screen.Home -> {
            TODO("Not implemented")
        }
    }
}

private fun defaultNavInOptions() = navOptions {
    anim {
        enter = android.R.anim.fade_in
        exit = android.R.anim.fade_out
    }
}

private fun defaultNavOutOptions(@IdRes target: Int) = navOptions {
    anim {
        popEnter = android.R.anim.slide_out_right
        popExit = android.R.anim.slide_in_left
    }
    popUpTo(
        id = target,
        popUpToBuilder = { inclusive = false }
    )
}
