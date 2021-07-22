package com.soyvictorherrera.nosedive.presentation.ui.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.usecase.ObserveCurrentUserUseCase
import com.soyvictorherrera.nosedive.presentation.ui.TAG
import com.soyvictorherrera.nosedive.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val observeCurrentUserUseCase: ObserveCurrentUserUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserModel>()
    val user: LiveData<UserModel>
        get() = _user

    init {
        viewModelScope.launch {
            observeCurrentUserUseCase.execute { result ->
                when (result) {
                    is Result.Success -> {
                        val user = result.data!!
                        Log.d(TAG, "user loaded: " + user)
                        _user.value = user
                    }
                    is Result.Error -> {
                        Log.e(TAG, "", result.exception)
                    }
                    Result.Loading -> {
                        Log.d(TAG, "loading")
                    }
                }
            }
        }
    }

}
