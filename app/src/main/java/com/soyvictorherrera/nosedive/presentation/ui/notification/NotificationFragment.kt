package com.soyvictorherrera.nosedive.presentation.ui.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import com.soyvictorherrera.nosedive.presentation.ui.popUpTo
import com.soyvictorherrera.nosedive.presentation.ui.shared.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationFragment : Fragment() {

    private val sessionViewModel: SessionViewModel by activityViewModels()
    private val viewModel: NotificationViewModel by viewModels()

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { screen ->
                if (screen == Screen.Home) {
                    popUpTo(screen)
                } else navigateInTo(
                    to = screen,
                    from = Screen.Notification,
                    args = Bundle().apply {
                        if (screen is Screen.FriendProfile) {
                            putString("user-id", screen.userId)
                        } else if (screen is Screen.RateUser) {
                            putString("user-id", screen.userId)
                        }
                    }
                )
            }
        }

        sessionViewModel.user.observe(viewLifecycleOwner) { user ->
            viewModel.onUserChanged(user)
        }

        return ComposeView(requireContext()).apply {
            id = R.id.notificationFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                NosediveTheme {

                    val notificationList by viewModel.notificationList.observeAsState(
                        initial = emptyList()
                    )

                    NotificationContentView(
                        notificationList = notificationList,
                        onNotificationEvent = ::onNotificationEvent
                    )
                }
            }
        }
    }

    private fun onNotificationEvent(event: NotificationEvent) {
        when (event) {
            NotificationEvent.NavigateBack -> {
                viewModel.navigateBack()
            }
            is NotificationEvent.FollowBack -> {
                viewModel.onFollowBack(event.notification)
            }
            is NotificationEvent.RateBack -> {
                viewModel.onRateBack(event.notification)
            }
            is NotificationEvent.NotificationClick -> {
                viewModel.onNotificationClick(event.notification)
            }
        }
    }

}
