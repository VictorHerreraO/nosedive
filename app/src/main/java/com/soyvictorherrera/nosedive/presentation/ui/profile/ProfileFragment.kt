package com.soyvictorherrera.nosedive.presentation.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private val stubUser = UserModel(name = "", email = "")

    @ExperimentalMaterialApi
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
                    val photoUriState by remember { mutableStateOf(userState.photoUrl) }
                    val sheetState = rememberModalBottomSheetState(initialValue = Hidden)
                    val scope = rememberCoroutineScope()

                    viewModel.profilePhotoEvent.observe(viewLifecycleOwner) { profilePhotoEvent ->
                        profilePhotoEvent.getContentIfNotHandled()?.let { event ->
                            when (event) {
                                ProfilePhotoEvent.RequestProfilePhotoChange -> {
                                    scope.launch {
                                        sheetState.show()
                                    }
                                }
                                is ProfilePhotoEvent.UpdateProfilePhoto -> {

                                }
                            }
                        }
                    }

                    ProfileContentView(
                        user = userState,
                        sheetState = sheetState
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