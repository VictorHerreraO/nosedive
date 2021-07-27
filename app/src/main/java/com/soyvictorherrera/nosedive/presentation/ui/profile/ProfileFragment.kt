package com.soyvictorherrera.nosedive.presentation.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private val stubUser = UserModel(name = "Bienvenido", email = "")

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                viewModel.onUserPhotoTakenSuccessfully()
            }
        }
    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                viewModel.onUserPhotoSelectedSuccessfully(uri)
            }
        }

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
                    val userState by viewModel.user.observeAsState(initial = stubUser)
                    val photoState by viewModel.profilePhotoState.observeAsState(
                        initial = ProfilePhotoState.Idle(
                            photoUri = userState.photoUrl?.let { Uri.parse(it.toString()) }
                        )
                    )
                    val sheetState = rememberModalBottomSheetState(initialValue = Hidden)
                    val scaffoldState = rememberScaffoldState()
                    val scope = rememberCoroutineScope()

                    // Observe profile photo events.
                    // Ej: request take photo from camera or choose from gallery
                    viewModel.profilePhotoEvent.observe(viewLifecycleOwner) { profilePhotoEvent ->
                        profilePhotoEvent.getContentIfNotHandled()?.let { event ->
                            when (event) {
                                ProfilePhotoEvent.RequestProfilePhotoChange -> {
                                    scope.launch {
                                        sheetState.show()
                                    }
                                }
                                is ProfilePhotoEvent.TakePhoto -> {
                                    takePhoto(event.destinationUri)
                                }
                                ProfilePhotoEvent.SelectPhoto -> {
                                    selectPhoto()
                                }
                            }
                        }
                    }

                    // Observe and display errors
                    viewModel.profileError.observe(viewLifecycleOwner) { errorEvent ->
                        errorEvent.getContentIfNotHandled()?.let { error ->
                            showError(
                                error = error,
                                scaffoldState = scaffoldState,
                                scope = scope
                            )
                        }
                    }

                    ProfileContentView(
                        user = userState,
                        profilePhotoState = photoState,
                        sheetState = sheetState,
                        scaffoldState = scaffoldState
                    ) { event ->
                        when (event) {
                            is ProfileEvent.UpdateUserPassword -> {
                                viewModel.onUpdateUserPassword(
                                    password = event.password,
                                    newPassword = event.newPassword
                                )
                            }
                            ProfileEvent.NavigateBack -> {
                                viewModel.onNavigateBack()
                            }
                            ProfileEvent.UpdateUserProfilePhoto -> {
                                viewModel.onUpdateUserProfilePhoto()
                            }
                            ProfileEvent.SelectPhotoFromCamera -> {
                                viewModel.onSelectPhotoFromCamera()
                                scope.launch { sheetState.hide() }
                            }
                            ProfileEvent.SelectPhotoFromGallery -> {
                                viewModel.onSelectPhotoFromGallery()
                                scope.launch { sheetState.hide() }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun takePhoto(destinationUri: Uri) {
        takeImageResult.launch(destinationUri)
    }

    private fun selectPhoto() {
        selectImageFromGalleryResult.launch("image/*")
    }

    private fun showError(
        error: ProfileError,
        scaffoldState: ScaffoldState,
        scope: CoroutineScope
    ) {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                when (error) {
                    ProfileError.UserNotFound -> {
                        getString(R.string.profile_error_user_not_found)
                    }
                    ProfileError.UnableToUploadPhoto -> {
                        getString(R.string.profile_error_unable_to_upload_photo)
                    }
                    ProfileError.UnableToChangePassword -> {
                        getString(R.string.profile_error_unable_to_change_password)
                    }
                }
            )
        }
    }

}
