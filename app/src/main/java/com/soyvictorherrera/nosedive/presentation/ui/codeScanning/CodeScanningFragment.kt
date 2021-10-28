package com.soyvictorherrera.nosedive.presentation.ui.codeScanning

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import com.soyvictorherrera.nosedive.presentation.ui.popUpTo
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.Executors

@AndroidEntryPoint
class CodeScanningFragment : Fragment() {

    private val viewModel: CodeScanningViewModel by viewModels()
    private val cameraxViewModel: CameraXViewModel by viewModels()

    // QR Code scan related
    private val cameraSelector: CameraSelector = CameraSelector.Builder()
        .requireLensFacing(CameraSelector.LENS_FACING_BACK)
        .build()
    private var cameraProvider: ProcessCameraProvider? = null
    private var previewUseCase: Preview? = null
    private var previewView: PreviewView? = null
    private var analysisUseCase: ImageAnalysis? = null

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            viewModel.onCameraPermissionRequestResult(isGranted)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (isCameraPermissionGranted()) {
            viewModel.onCameraPermissionRequestResult(true)
        } else {
            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        }

        viewModel.navigateTo.observe(viewLifecycleOwner) { navigationEvent ->
            navigationEvent.getContentIfNotHandled()?.let { screen ->
                when (screen) {
                    Screen.Home -> {
                        popUpTo(Screen.Home)
                    }
                    is Screen.FriendProfile -> {
                        navigateInTo(
                            to = screen,
                            from = Screen.CodeScanning,
                            args = bundleOf("user-id" to screen.userId)
                        )
                    }
                    else -> navigateInTo(to = screen, from = Screen.CodeScanning)
                }
            }
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
                    val scanState by viewModel.codeScanState.observeAsState(initial = CodeScanState.Waiting)

                    CodeScanningContentView(
                        inputState = inputState,
                        scanState = scanState,
                        onNavigationEvent = { event ->
                            when (event) {
                                CodeScanningEvent.NavigateBack -> {
                                    viewModel.onNavigateBack()
                                }
                                CodeScanningEvent.NavigateCodeShow -> {
                                    viewModel.onNavigateCodeShow()
                                }
                            }
                        },
                        onActionEvent = { event ->
                            when (event) {
                                CodeScanningActionEvent.WriteCode -> {
                                    viewModel.onWriteCode()
                                }
                                is CodeScanningActionEvent.QrPreviewCreated -> {
                                    previewView = event.view
                                    setupCamera()
                                }
                            }
                        }
                    )
                }
            }
        }
    }

    private fun isCameraPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun setupCamera() {
        cameraxViewModel.processCameraProvider.observe(viewLifecycleOwner) { provider ->
            cameraProvider = provider
            if (isCameraPermissionGranted()) {
                bindUseCases()
            } else {
                viewModel.onCameraPermissionRequestResult(isGranted = false)
            }
        }
    }

    private fun bindUseCases() {
        bindPreviewUseCase()
        bindAnalyseUseCase()
    }

    private fun bindPreviewUseCase() {
        val provider = cameraProvider ?: return

        previewUseCase?.let {
            provider.unbind(it)
        }

        previewUseCase = Preview.Builder()
            // .setTargetAspectRatio()
            // .setTargetRotation()
            .build()
            .also { preview ->
                previewView?.let {
                    preview.setSurfaceProvider(it.surfaceProvider)
                }
            }

        try {
            provider.bindToLifecycle(
                viewLifecycleOwner,
                cameraSelector,
                previewUseCase
            )
        } catch (ex: Exception) {
            Log.e(TAG, "error", ex)
        }
    }

    private fun bindAnalyseUseCase() {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val barcodeScanner = BarcodeScanning.getClient(options)
        val provider = cameraProvider ?: return

        analysisUseCase?.let {
            provider.unbind(it)
        }

        analysisUseCase = ImageAnalysis.Builder()
            // .setTargetAspectRatio()
            // .setTargetRotation()
            .build()

        val cameraExecutor = Executors.newSingleThreadExecutor()

        analysisUseCase?.setAnalyzer(cameraExecutor) { imageProxy ->
            processImageProxy(barcodeScanner, imageProxy)
        }

        provider.bindToLifecycle(
            viewLifecycleOwner,
            cameraSelector,
            analysisUseCase
        )
    }

    @SuppressLint("UnsafeOptInUsageError")
    private fun processImageProxy(barcodeScanner: BarcodeScanner, imageProxy: ImageProxy) {
        val inputImage = InputImage.fromMediaImage(
            imageProxy.image!!,
            imageProxy.imageInfo.rotationDegrees
        )
        barcodeScanner.process(inputImage)
            .addOnSuccessListener { barcodes ->
                barcodes.forEach { barcode ->
                    barcode.rawValue?.let { value ->
                        viewModel.onQrCodeDetected(value)
                    }
                }
            }
            .addOnFailureListener {
                Log.e(TAG, it.message ?: "no message")
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }

}
