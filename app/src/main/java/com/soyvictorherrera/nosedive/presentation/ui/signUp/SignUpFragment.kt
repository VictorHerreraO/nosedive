package com.soyvictorherrera.nosedive.presentation.ui.signUp

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
import com.soyvictorherrera.nosedive.presentation.ui.popUpTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                if (navigateTo == Screen.SignIn) popUpTo(navigateTo)
                else navigateInTo(
                    to = navigateTo,
                    from = Screen.SignUp,
                    clearStack = true
                )
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
                    val uiState = viewModel.signUpState.observeAsState(SignUpState.Idle)
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()

                    viewModel.signUpError.observe(viewLifecycleOwner) { errorEvent ->
                        errorEvent.getContentIfNotHandled()?.let { error ->
                            showError(
                                error = error,
                                scaffoldState = scaffoldState,
                                scope = scope
                            )
                        }
                    }

                    SignUpContent(
                        signUpState = uiState.value,
                        scaffoldState = scaffoldState,
                        onNavigationEvent = { event ->
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

    private fun showError(
        error: SignUpError,
        scaffoldState: ScaffoldState,
        scope: CoroutineScope
    ) {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                when (error) {
                    SignUpError.ErrorUnknown -> {
                        getString(R.string.login_error_unknown)
                    }
                    SignUpError.UnableToCreateAccount -> {
                        getString(R.string.login_unable_to_create_account)
                    }
                }
            )
        }
    }

}
