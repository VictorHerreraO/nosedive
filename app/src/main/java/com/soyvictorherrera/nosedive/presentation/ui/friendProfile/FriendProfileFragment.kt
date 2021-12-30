package com.soyvictorherrera.nosedive.presentation.ui.friendProfile

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
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import com.soyvictorherrera.nosedive.presentation.ui.popUpTo
import com.soyvictorherrera.nosedive.presentation.ui.shared.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FriendProfileFragment : Fragment() {

    private val userDetailsViewModel: UserDetailsViewModel by activityViewModels()
    private val viewModel: FriendProfileViewModel by viewModels()

    //region Fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setupNavigation()

        arguments?.getString("user-id")?.let { id ->
            Timber.d("user-id is $id")
            viewModel.onFriendUserIdChanged(id)
        }

        userDetailsViewModel.friendList.observe(viewLifecycleOwner) { friendList ->
            viewModel.onCurrentUserFriendListChanged(friendList)
        }

        return ComposeView(requireContext()).apply {
            id = R.id.friendProfileFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            viewModel.canFollowFriend.observe(viewLifecycleOwner) { canFollow ->
                Timber.e("new value for canFollow is [$canFollow]")
            }

            setContent {
                val friendModel by viewModel.friendModel.observeAsState()
                val friendStats by viewModel.friendStats.observeAsState(UserStatsModel())
                val friendAvgScore by viewModel.friendAvgScore.observeAsState()
                val canFollowFriend by viewModel.canFollowFriend.observeAsState(initial = false)

                NosediveTheme {
                    FriendProfileContentView(
                        user = friendModel ?: return@NosediveTheme,
                        userStats = friendStats,
                        userScore = friendAvgScore,
                        canFollowUser = canFollowFriend,
                        onNavigationEvent = this@FriendProfileFragment::onNavigationEvent,
                        onActionEvent = this@FriendProfileFragment::onActionEvent
                    )
                }
            }
        }
    }
    //endregion

    private fun setupNavigation() {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigationEvent ->
            navigationEvent.getContentIfNotHandled()?.let { screen ->
                if (screen == Screen.Home) popUpTo(screen)
                else navigateInTo(
                    to = screen,
                    from = Screen.FriendProfile("")
                )
            }
        }
    }

    private fun onNavigationEvent(event: FriendProfileEvent) {
        when (event) {
            FriendProfileEvent.NavigateBack -> {
                viewModel.onNavigateBack()
            }
        }
    }

    private fun onActionEvent(event: FriendProfileActionEvent) {
        when (event) {
            FriendProfileActionEvent.RateUser -> {
                viewModel.onRateUser()
            }
            FriendProfileActionEvent.FollowUser -> {
                viewModel.onFollowUser()
            }
        }
    }

}
