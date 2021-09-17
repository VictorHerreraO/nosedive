package com.soyvictorherrera.nosedive.presentation.ui.codeScanning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.codeScanning.TextCodeInputState.Idle
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import com.soyvictorherrera.nosedive.presentation.ui.popUpTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CodeScanningFragment : Fragment() {

    private val viewModel: CodeScanningViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigationEvent ->
            navigationEvent.getContentIfNotHandled()?.let { screen ->
                if (screen == Screen.Home) {
                    popUpTo(Screen.Home)
                } else navigateInTo(to = screen, from = Screen.CodeScanning)
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
                    val inputState by viewModel.codeInputState.observeAsState(initial = Idle)

                    CodeScanningContentView(
                        inputState = inputState
                    ) { event ->
                        when (event) {
                            CodeScanningEvent.NavigateBack -> {
                                viewModel.onNavigateBack()
                            }
                            CodeScanningEvent.NavigateCodeShow -> {
                                viewModel.onNavigateCodeShow()
                            }
                            CodeScanningEvent.WriteCode -> {
                                viewModel.onWriteCode()
                            }
                        }
                    }
                }
            }
        }
    }

}
