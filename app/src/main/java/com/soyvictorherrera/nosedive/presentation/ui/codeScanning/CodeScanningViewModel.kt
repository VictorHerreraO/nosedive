package com.soyvictorherrera.nosedive.presentation.ui.codeScanning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soyvictorherrera.nosedive.presentation.ui.Event
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CodeScanningViewModel @Inject constructor(

) : ViewModel() {

    private val _navigateTo = MutableLiveData<Event<Screen>>()
    val navigateTo: LiveData<Event<Screen>>
        get() = _navigateTo

    fun onNavigateBack() {
        _navigateTo.value = Event(Screen.Home)
    }

    fun onNavigateCodeShow() {
        _navigateTo.value = Event(Screen.CodeSharing)
    }

}
