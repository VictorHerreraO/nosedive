package com.soyvictorherrera.nosedive.presentation.ui.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.BuildConfig
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import com.soyvictorherrera.nosedive.presentation.ui.popUpTo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.io.File

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: ProfileViewModel by viewModels()
    private val stubUser = UserModel(name = "", email = "")

    private val takeImageResult =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { isSuccess ->
            if (isSuccess) {
                viewModel.onUserPhotoTakenSuccessfully()
            }
        }
    private val selectImageFromGalleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            viewModel.onUserPhotoSelectedSuccessfully(uri)
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
                            photoUri = userState.photoUrl
                        )
                    )
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
                                is ProfilePhotoEvent.TakePhoto -> {
                                    takePhoto(event.destinationUri)
                                }
                                ProfilePhotoEvent.SelectPhoto -> {
                                    selectPhoto()
                                }
                            }
                        }
                    }

                    ProfileContentView(
                        user = userState,
                        profilePhotoState = photoState,
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
                            ProfileEvent.SelectPhotoFromCamera -> {
                                viewModel.onSelectPhotoFromCamera(getTmpFileUri())
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

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("tmp_image_file", ".png", null).apply {
            createNewFile()
            deleteOnExit()
        }

        return FileProvider.getUriForFile(
            requireContext().applicationContext,
            "${BuildConfig.APPLICATION_ID}.provider",
            tmpFile
        )
    }

    private fun takePhoto(destinationUri: Uri) {
        takeImageResult.launch(destinationUri)
    }

    private fun selectPhoto() {
        selectImageFromGalleryResult.launch("image/*")
    }

}
