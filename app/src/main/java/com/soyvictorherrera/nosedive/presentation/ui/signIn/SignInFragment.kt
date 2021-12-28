package com.soyvictorherrera.nosedive.presentation.ui.signIn

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
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigateInTo(
                    to = navigateTo,
                    from = Screen.SignIn,
                    clearStack = true
                )
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
                    Timber.d("recomposing...")

                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()
                    val uiState = viewModel.signInState.observeAsState(SignInState.Idle)

                    viewModel.signInError.observe(viewLifecycleOwner) { errorEvent ->
                        errorEvent.getContentIfNotHandled()?.let { error ->
                            showError(
                                error = error,
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
                        getString(R.string.login_error_unknown)
                    }
                    SignInError.WrongCredentials -> {
                        getString(R.string.login_error_wrong_credentials)
                    }
                    SignInError.NotImplemented -> {
                        getString(R.string.login_error_not_implemented)
                    }
                }
            )
        }
    }

}