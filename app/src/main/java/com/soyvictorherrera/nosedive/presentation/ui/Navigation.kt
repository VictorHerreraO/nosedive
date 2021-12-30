package com.soyvictorherrera.nosedive.presentation.ui

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import com.soyvictorherrera.nosedive.R

sealed class Screen(@IdRes val resId: Int) {
    object SignIn : Screen(resId = R.id.signInFragment)
    object SignUp : Screen(resId = R.id.signUpFragment)
    object Home : Screen(resId = R.id.homeFragment)
    object Profile : Screen(resId = R.id.profileFragment)
    object CodeSharing : Screen(resId = R.id.codeSharingFragment)
    object CodeScanning : Screen(resId = R.id.codeScanningFragment)
    data class FriendProfile(val userId: String) : Screen(resId = R.id.friendProfileFragment)
    object FriendList : Screen(resId = R.id.friendListFragment)
}

fun Fragment.navigateInTo(
    to: Screen,
    from: Screen,
    clearStack: Boolean = false,
    args: Bundle? = null
) {
    if (to == from) throw IllegalArgumentException("Can't navigate in to $to")

    findNavController().navigate(
        resId = to.resId,
        args = args,
        navOptions = navOptions {
            anim {
                enter = android.R.anim.fade_in
                exit = android.R.anim.fade_out
            }
            if (clearStack) {
                popUpTo(
                    id = from.resId,
                    popUpToBuilder = { inclusive = true }
                )
            }
        }
    )
}

fun Fragment.navigateOutTo(to: Screen, from: Screen) {
    if (to == from) throw IllegalArgumentException("Can't navigate out to $to")

    findNavController().navigate(
        resId = to.resId,
        args = null,
        navOptions = navOptions {
            anim {
                popEnter = android.R.anim.slide_out_right
                popExit = android.R.anim.slide_in_left
            }
            popUpTo(
                id = from.resId,
                popUpToBuilder = { inclusive = true }
            )
        }
    )
}

fun Fragment.popUpTo(to: Screen) {
    findNavController().navigate(
        resId = 0,
        null,
        navOptions = navOptions {
            anim {
                popEnter = android.R.anim.slide_out_right
                popExit = android.R.anim.slide_in_left
            }
            popUpTo(
                id = to.resId,
                popUpToBuilder = { inclusive = false }
            )
        }
    )
}
