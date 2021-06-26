package com.soyvictorherrera.nosedive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.soyvictorherrera.nosedive.ui.content.login.LoginActivityContent
import com.soyvictorherrera.nosedive.ui.content.signUp.SignUpContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // LoginActivityContent()
            SignUpContent()
        }
    }
}
