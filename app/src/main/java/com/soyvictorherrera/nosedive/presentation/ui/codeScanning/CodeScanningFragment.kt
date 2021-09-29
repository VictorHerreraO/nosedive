package com.soyvictorherrera.nosedive.presentation.ui.codeScanning

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import com.soyvictorherrera.nosedive.presentation.ui.popUpTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CodeScanningFragment : Fragment() {

    private val viewModel: CodeScanningViewModel by viewModels()

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            viewModel.onCameraPermissionRequestResult(isGranted)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.onCameraPermissionRequestResult(true)
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        viewModel.navigateTo.observe(viewLifecycleOwner) { navigationEvent ->
            navigationEvent.getContentIfNotHandled()?.let { screen ->
                if (screen == Screen.Home) {
                    popUpTo(Screen.Home)
                } else navigateInTo(to = screen, from = Screen.CodeScanning)
            }
        }

        viewModel.codeScanState.observe(viewLifecycleOwner) {
            Log.d(TAG, "onCreateView: state is = [$it]")
        }

        return ComposeView(requireContext()).apply {
            id = R.id.codeScanningFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                NosediveTheme {
                    val inputState by viewModel.codeInputState.observeAsState(initial = TextCodeInputState.Idle)
                    val scanState by viewModel.codeScanState.observeAsState(initial = CodeScanState.Idle)

                    CodeScanningContentView(
                        inputState = inputState,
                        scanState = scanState
                    ) { event ->
                        when (event) {
                            CodeScanningEvent.NavigateBack -> {
                                viewModel.onNavigateBack()
                            }
                            CodeScanningEvent.NavigateCodeShow -> {
                                viewModel.onNavigateCodeShow()
                            }
                            CodeScanningEvent.WriteCode -> {
                                viewModel.onWriteCode()
                            }
                        }
                    }
                }
            }
        }
    }

}
