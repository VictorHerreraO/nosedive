package com.soyvictorherrera.nosedive.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.soyvictorherrera.nosedive.R
import dagger.hilt.android.AndroidEntryPoint

const val TAG = "AppDebug"

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
