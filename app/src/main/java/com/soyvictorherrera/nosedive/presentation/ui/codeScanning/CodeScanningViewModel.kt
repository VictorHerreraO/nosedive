package com.soyvictorherrera.nosedive.presentation.ui.codeScanning

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.SharingCodeModel
import com.soyvictorherrera.nosedive.domain.usecase.sharing.GetTextSharingCodeUseCase
import com.soyvictorherrera.nosedive.domain.usecase.sharing.ReadQrCodeUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CodeScanningViewModel @Inject constructor(
    private val readQrCodeUseCase: ReadQrCodeUseCase,
    private val getTextSharingCodeUseCase: GetTextSharingCodeUseCase
) : ViewModel() {

    companion object {
        const val QR_READINGS_THRESHOLD = 500L
    }

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _codeInputState = MutableLiveData<TextCodeInputState>()
    val codeInputState: LiveData<TextCodeInputState>
        get() = _codeInputState

    private val _codeScanState = MutableLiveData<CodeScanState>()
    val codeScanState: LiveData<CodeScanState>
        get() = _codeScanState

    private var lastReadMillis = 0L
    private val millisSinceLastRead: Long
        get() = System.currentTimeMillis() - lastReadMillis


    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onNavigateCodeShow() {
        _navigateTo.value = Event(Screen.CodeSharing)
    }

    fun onWriteCode() {
        Log.d(TAG, "onWriteCode() called")
        _codeInputState.value = TextCodeInputState.Ready(code = "", onCodeChange = { code ->
            if (code.length >= SharingCodeModel.LENGTH) {
                Log.d(TAG, "trigger code search with [$code]")
                _codeInputState.value = TextCodeInputState.Loading
                findByPublicSharingCode(publicCode = code)
            }
        })
    }

    fun onCameraPermissionRequestResult(isGranted: Boolean) {
        _codeScanState.value = if (isGranted) CodeScanState.Active else CodeScanState.Error
    }

    fun onQrCodeDetected(value: String) {
        if (millisSinceLastRead < QR_READINGS_THRESHOLD) return
        lastReadMillis = System.currentTimeMillis()

        viewModelScope.launch {
            readQrCodeUseCase.apply {
                rawValue = value
            }.execute { result ->
                when (result) {
                    is Result.Error -> {
                        Log.w(TAG, "Error al procesar el QR", result.exception)
                    }
                    Result.Loading -> Unit
                    is Result.Success -> {
                        Log.d(TAG, "aquí debería navegar a la pantalla del usuario ${result.data}")
                        navigateToFriendProfile(userId = result.data)
                    }
                }
            }
        }
    }

    private fun findByPublicSharingCode(publicCode: String) {
        if (publicCode.length < SharingCodeModel.LENGTH) return

        viewModelScope.launch {
            getTextSharingCodeUseCase.apply {
                this.publicCode = publicCode
            }.execute { result ->
                when (result) {
                    Result.Loading -> Unit
                    is Result.Error -> {
                        // TODO: 20/10/2021 mostrar feedback al usuario de que no se pudo encontrar el código
                        Timber.e(result.exception)
                    }
                    is Result.Success -> {
                        Timber.i("El código $publicCode apunta al usuario ${result.data.userId}")
                        navigateToFriendProfile(result.data.userId)
                    }
                }
            }
        }
    }

    private fun navigateToFriendProfile(userId: String) {
        _navigateTo.value = Event(
            Screen.FriendProfile(userId)
        )
    }

}
