package com.soyvictorherrera.nosedive.ui.content.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.ui.Screen
import com.soyvictorherrera.nosedive.ui.content.ViewModelFactory
import com.soyvictorherrera.nosedive.ui.navigateOutTo
import com.soyvictorherrera.nosedive.ui.theme.NosediveTheme

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels { ViewModelFactory() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            id = R.id.homeFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                NosediveTheme {
                    val userState = viewModel.user.observeAsState()
                    val scaffoldState = rememberScaffoldState()

                    userState.value?.let { user ->
                        HomeContentView(
                            userName = user.name ?: "no-name",
                            scaffoldState = scaffoldState
                        ) { event ->

                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("onViewCreated:", "called!")
        viewModel.sessionState.observe(viewLifecycleOwner) { sessionState ->
            Log.d("onViewCreated:", "sessionState change")
            if (sessionState == SessionState.SignedOut) {
                navigateOutTo(Screen.SignIn, Screen.Home)
            }
        }
    }

}
