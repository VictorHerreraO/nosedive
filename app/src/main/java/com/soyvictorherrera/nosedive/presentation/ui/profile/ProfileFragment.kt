package com.soyvictorherrera.nosedive.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import com.soyvictorherrera.nosedive.presentation.ui.popUpTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private val stubUser = UserModel(name = "", email = "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigationEvent ->
            navigationEvent.getContentIfNotHandled()?.let { navigateTo ->
                when (navigateTo) {
                    Screen.Home -> {
                        popUpTo(Screen.Home)
                    }
                    else -> {
                        navigateInTo(navigateTo, Screen.Profile)
                    }
                }
            }
        }

        return ComposeView(requireContext()).apply {
            id = R.id.profileFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                NosediveTheme {
                    val userState by viewModel.user.observeAsState(stubUser)

                    ProfileContentView(
                        user = userState
                    ) { event ->
                        when (event) {
                            is ProfileEvent.UpdateUserPassword -> {
                                viewModel.onUpdateUserPassword(event.newPassword)
                            }
                            ProfileEvent.NavigateBack -> {
                                viewModel.onNavigateBack()
                            }
                            ProfileEvent.UpdateUserProfilePhoto -> {
                                viewModel.onUpdateUserProfilePhoto()
                            }
                        }
                    }
                }
            }
        }
    }

}
