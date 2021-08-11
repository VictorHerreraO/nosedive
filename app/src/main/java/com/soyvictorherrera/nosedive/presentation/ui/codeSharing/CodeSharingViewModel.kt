package com.soyvictorherrera.nosedive.presentation.ui.codeSharing

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CodeSharingViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase
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
        viewModelScope.launch {
            observeCurrentUserUseCase.execute { result ->
                when (result) {
                    is Result.Success -> {
                        _user.value = result.data!!
                    }
                    is Result.Error -> {
                    }
                    Result.Loading -> {
                    }
                }
            }
        }

        viewModelScope.launch {
            delay(3000)

            _imageCode.value = ImageCodeState.Generated(
                codeUri = Uri.parse("https://upload.wikimedia.org/wikipedia/commons/d/d7/Commons_QR_code.png")
            )

            delay(3000)

            _imageCode.value = ImageCodeState.Error
        }

        viewModelScope.launch {
            delay(3000)

            _textCode.value = TextCodeState.None

            delay(3000)

            _textCode.value = TextCodeState.Loading

            delay(3000)

            _textCode.value = TextCodeState.Generated(code = "123456")

            delay(3000)

            _textCode.value = TextCodeState.Error
        }
    }

    fun onGenerateSharingCode() {

    }

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onScanSharingCode() {

    }


}
