package com.soyvictorherrera.nosedive.ui.content.signUp

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

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels { SignUpViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigate(navigateTo, Screen.SignUp)
            }
        }

        return ComposeView(requireContext()).apply {
            id = R.id.signUpFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                NosediveTheme {
                    SignUpContent(onNavigationEvent = { event ->
                        when (event) {
                            is SignUpEvent.SignUp -> {
                                viewModel.signUp(
                                    name = event.name,
                                    email = event.email,
                                    password = event.password
                                )
                            }
                            SignUpEvent.SignIn -> {
                                viewModel.signIn()
                            }
                            SignUpEvent.NavigateBack -> {
                                viewModel.signIn()
                            }
                        }
                    })
                }
            }
        }
    }

}
