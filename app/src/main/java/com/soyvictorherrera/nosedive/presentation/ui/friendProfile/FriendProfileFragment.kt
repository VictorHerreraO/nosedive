package com.soyvictorherrera.nosedive.presentation.ui.friendProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import timber.log.Timber

class FriendProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Timber.d("user-id is ${arguments?.getString("user-id")}")

        return super.onCreateView(inflater, container, savedInstanceState)
    }

}
