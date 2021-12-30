package com.soyvictorherrera.nosedive.presentation.ui.friendList

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FriendListFragment : Fragment() {

    private val viewModel: FriendListViewModel by viewModels()

}
