package com.soyvictorherrera.nosedive.presentation.ui.codeSharing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import com.soyvictorherrera.nosedive.presentation.ui.popUpTo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CodeSharingFragment : Fragment() {

    private val viewModel: CodeSharingViewModel by viewModels()
    private val stubUser = UserModel(name = "", email = "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigationEvent ->
            navigationEvent.getContentIfNotHandled()?.let { event ->
                when (event) {
                    Screen.Home -> popUpTo(Screen.Home)
                    else -> navigateInTo(event, Screen.CodeSharing)
                }
            }
        }

        return ComposeView(requireContext()).apply {
            id = R.id.codeSharingFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                NosediveTheme {
                    val user by viewModel.user.observeAsState(initial = stubUser)

                    CodeSharingContentView(
                        user = user,
                        scaffoldState = rememberScaffoldState()
                    ) { event ->
                        when (event) {
                            CodeSharingEvent.GenerateSharingCode -> {
                                viewModel.onGenerateSharingCode()
                            }
                            CodeSharingEvent.NavigateBack -> {
                                viewModel.onNavigateBack()
                            }
                            CodeSharingEvent.ScanSharingCode -> {
                                viewModel.onScanSharingCode()
                            }
                        }
                    }
                }
            }
        }
    }

}
