package com.soyvictorherrera.nosedive.presentation.ui.codeScanning

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soyvictorherrera.nosedive.domain.model.SharingCodeModel
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CodeScanningViewModel @Inject constructor(

) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _codeInputState = MutableLiveData<TextCodeInputState>()
    val codeInputState: LiveData<TextCodeInputState>
        get() = _codeInputState

    private val _codeScanState = MutableLiveData<CodeScanState>()
    val codeScanState: LiveData<CodeScanState>
        get() = _codeScanState

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
            }
        })
    }

    fun onCameraPermissionRequestResult(isGranted: Boolean) {
        _codeScanState.value = if (isGranted) CodeScanState.Active else CodeScanState.Error
    }

}
