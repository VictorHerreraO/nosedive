package com.soyvictorherrera.nosedive.ui.content.signIn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.ui.Screen
import com.soyvictorherrera.nosedive.ui.content.ViewModelFactory
import com.soyvictorherrera.nosedive.ui.navigateInTo
import com.soyvictorherrera.nosedive.ui.theme.NosediveTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigateInTo(navigateTo, Screen.SignIn)
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
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()
                    val uiState = viewModel.signInState.observeAsState(SignInState.Idle)

                    viewModel.signInError.observe(viewLifecycleOwner) { error ->
                        error?.let {
                            showError(
                                error = it,
                                scaffoldState = scaffoldState,
                                scope = scope
                            )
                        }
                    }

                    SignInContent(
                        signInState = uiState.value,
                        scaffoldState = scaffoldState,
                        onNavigationEvent = { event ->
                            when (event) {
                                is SignInEvent.SignIn -> {
                                    viewModel.signIn(
                                        email = event.email,
                                        password = event.password
                                    )
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


    private fun showError(
        error: SignInError,
        scaffoldState: ScaffoldState,
        scope: CoroutineScope
    ) {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                when (error) {
                    SignInError.ErrorUnknown -> {
                        "Error desconocido"
                    }
                    SignInError.WrongCredentials -> {
                        "Correo o contraseña incorrectos"
                    }
                }
            )
        }
    }

}