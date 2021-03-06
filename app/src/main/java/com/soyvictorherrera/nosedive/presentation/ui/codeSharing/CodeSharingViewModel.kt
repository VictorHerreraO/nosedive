package com.soyvictorherrera.nosedive.presentation.ui.codeSharing

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.user.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.domain.usecase.sharing.DeleteTextSharingCodeUseCase
import com.soyvictorherrera.nosedive.domain.usecase.sharing.GenerateQrCodeUseCase
import com.soyvictorherrera.nosedive.domain.usecase.sharing.GenerateTextSharingCodeUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CodeSharingViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase,
    private val generateQrCodeUseCase: GenerateQrCodeUseCase,
    private val generateTextSharingCodeUseCase: GenerateTextSharingCodeUseCase,
    private val deleteTextSharingCodeUseCase: DeleteTextSharingCodeUseCase
) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel>
        get() = _user

    private val _imageCode = MutableLiveData<ImageCodeState>()
    val imageCode: LiveData<ImageCodeState>
        get() = _imageCode

    private val _textCode = MutableLiveData<TextCodeState>()
    val textCode: LiveData<TextCodeState>
        get() = _textCode

    init {
        observeCurrentUser()
    }

    fun onGenerateSharingCode() {
        generateTextSharingCode()
    }

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onScanSharingCode() {
        _navigateTo.value = Event(Screen.CodeScanning)
    }

    fun onSharingEnd() {
        deleteTextSharingCode()
    }

    private fun observeCurrentUser() {
        viewModelScope.launch {
            observeCurrentUserUseCase.execute { result ->
                when (result) {
                    is Result.Success -> {
                        val currentUser: UserModel = result.data
                        _user.value = currentUser
                        generateQrCode(user = currentUser)
                    }
                    is Result.Error -> {
                        _imageCode.value = ImageCodeState.Error
                    }
                    Result.Loading -> {
                        _imageCode.value = ImageCodeState.Loading
                    }
                }
            }
        }
    }

    private fun generateQrCode(user: UserModel) {
        viewModelScope.launch {
            val userId = user.id
            if (userId.isNullOrEmpty()) {
                _imageCode.value = ImageCodeState.Error
                Timber.e("user id was empty or null")
            } else {
                Timber.d("generating QrCode for ID = $userId")
                generateQrCodeUseCase.apply {
                    qrContent = userId
                }.execute { result ->
                    when (result) {
                        is Result.Error -> {
                            _imageCode.value = ImageCodeState.Error
                            Timber.e(result.exception, "error generating QR code")
                        }
                        Result.Loading -> {
                            _imageCode.value = ImageCodeState.Loading
                        }
                        is Result.Success -> {
                            val codeUri = Uri.parse(result.data.toString())
                            _imageCode.value = ImageCodeState.Generated(codeUri = codeUri)
                            Timber.d("codeUri is = $codeUri")
                        }
                    }
                }
            }
        }
    }

    private fun generateTextSharingCode() {
        _user.value?.let { user ->
            val uId = user.id

            if (uId.isNullOrEmpty()) {
                _textCode.value = TextCodeState.Error
            } else {
                _textCode.value = TextCodeState.Loading

                viewModelScope.launch {
                    generateTextSharingCodeUseCase.apply {
                        userId = uId
                    }.execute { result ->
                        when (result) {
                            is Result.Error -> {
                                _textCode.value = TextCodeState.Error
                                Timber.e("error al generar el c??digo de texto")
                            }
                            Result.Loading -> {
                                _textCode.value = TextCodeState.Loading
                            }
                            is Result.Success -> {
                                val sharingCode = result.data
                                Timber.d("El c??digo generado es ${sharingCode.publicCode} para el usuario ${sharingCode.userId}")
                                _textCode.value =
                                    TextCodeState.Generated(code = sharingCode.publicCode)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun deleteTextSharingCode() {
        Timber.d("deleting text sharing code")
        val textCode = textCode.value
        if (textCode !is TextCodeState.Generated) return

        // Launch coroutine in a non-viewmodel-dependent  scope
        CoroutineScope(IO).launch {
            Timber.d("launching coroutine")
            deleteTextSharingCodeUseCase.apply {
                publicSharingCode = textCode.code
            }.execute { result ->
                when (result) {
                    is Result.Error -> {
                        Timber.d(result.exception, "error deleting code")
                    }
                    Result.Loading -> {
                        Timber.d("loading")
                    }
                    is Result.Success -> {
                        Timber.d("success deleting code")
                    }
                }
            }
            Timber.d("coroutine done")
        }
    }

}
