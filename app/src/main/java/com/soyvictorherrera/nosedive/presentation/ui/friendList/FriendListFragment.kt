package com.soyvictorherrera.nosedive.presentation.ui.friendList

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
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.shared.UserDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class FriendListFragment : Fragment() {

    private val userDetailsViewModel: UserDetailsViewModel by activityViewModels()
    private val viewModel: FriendListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        userDetailsViewModel.friendList.observe(viewLifecycleOwner) { friendList ->
            viewModel.onFriendListChanged(friendList)
        }

        return ComposeView(requireContext()).apply {
            id = R.id.friendListFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                NosediveTheme {
                    val friendList by viewModel.friendList.observeAsState(initial = emptyList())

                    FriendListContentView(
                        friendList = friendList,
                        onFriendListEvent = ::onFriendListEvent
                    )
                }
            }
        }
    }

    private fun onFriendListEvent(event: FriendListEvent) {
        when (event) {
            is FriendListEvent.FriendSelected -> {
                Timber.i("friend [${event.friend.name}] selected")
            }
            FriendListEvent.NavigateBack -> {
                Timber.i("Navigate back")
            }
            FriendListEvent.SearchFriend -> {
                Timber.i("Search friend here")
            }
        }
    }

}
