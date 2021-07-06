package com.soyvictorherrera.nosedive.ui.content.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.soyvictorherrera.nosedive.data.Result
import com.soyvictorherrera.nosedive.data.source.user.UserEntity
import com.soyvictorherrera.nosedive.domain.usecase.GetCurrentUserUseCase
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getCurrentUserUseCase: GetCurrentUserUseCase
) : ViewModel() {

    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity>
        get() = _user

    init {
        viewModelScope.launch {
            getCurrentUserUseCase().let { result ->
                when (result) {
                    is Result.Success -> {
                        _user.value = result.data
                    }
                    is Result.Error -> {
                        Log.e("getCurrentUser", "error by:", result.exception)
                    }
                    else -> {
                        Log.d("getCurrentUser", "else")
                    }
                }
            }
        }
    }

}
