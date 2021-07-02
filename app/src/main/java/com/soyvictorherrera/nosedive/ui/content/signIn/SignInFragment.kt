package com.soyvictorherrera.nosedive.ui.content.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.ui.Screen
import com.soyvictorherrera.nosedive.ui.navigate
import com.soyvictorherrera.nosedive.ui.theme.NosediveTheme

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels { SignInViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.SignIn)
            }
        }

        return ComposeView(requireContext()).apply {
            id = R.id.signInFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                NosediveTheme {
                    SignInContent(onNavigationEvent = { event ->
                        when (event) {
                            is SignInEvent.SignIn -> {
                                viewModel.signIn(email = event.email, password = event.password)
                            }
                            SignInEvent.SignUp -> {
                                viewModel.signUp()
                            }
                            SignInEvent.ResetPassword -> {
                                viewModel.resetPassword()
                            }
                        }
                    })
                }
            }
        }
    }

}