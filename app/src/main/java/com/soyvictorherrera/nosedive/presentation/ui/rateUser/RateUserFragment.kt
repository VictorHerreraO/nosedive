package com.soyvictorherrera.nosedive.presentation.ui.rateUser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import com.soyvictorherrera.nosedive.presentation.ui.popUpTo
import com.soyvictorherrera.nosedive.presentation.ui.shared.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RateUserFragment : Fragment() {

    private val sessionViewModel: SessionViewModel by activityViewModels()
    private val viewModel: RateUserViewModel by viewModels()
    private val stubUser = UserModel(name = "", email = "")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.getString("user-id")?.let { id ->
            viewModel.onUserIdChanged(id)
        }

        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { screen ->
                if (screen == Screen.Home) popUpTo(screen)
                else navigateInTo(to = screen, from = Screen.RateUser(""))
            }
        }

        sessionViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.onCurrentUserChanged(user)
        }

        return ComposeView(requireContext()).apply {
            id = R.id.rateUserFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                val user by viewModel.user.observeAsState(initial = stubUser)
                val currentRating by viewModel.currentRating.observeAsState(initial = 0)

                NosediveTheme {
                    RateUserContentView(
                        user = user,
                        currentRating = currentRating,
                        onRateUserEvent = ::onRateUserEvent
                    )
                }
            }
        }
    }

    private fun onRateUserEvent(event: RateUserEvent) {
        when (event) {
            RateUserEvent.NavigateBack -> viewModel.onNavigateBack()
            is RateUserEvent.RateChanged -> viewModel.onRateChanged(event.newRate)
            RateUserEvent.SubmitRating -> viewModel.onSubmitRating()
        }
    }

}
