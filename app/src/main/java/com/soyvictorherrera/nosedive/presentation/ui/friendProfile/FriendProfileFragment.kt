package com.soyvictorherrera.nosedive.presentation.ui.friendProfile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import timber.log.Timber

class FriendProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Timber.d("user-id is ${arguments?.getString("user-id")}")

        return ComposeView(requireContext()).apply {
            id = R.id.friendProfileFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                NosediveTheme {
                    FriendProfileContentView(
                        user = UserModel(name = "Jessica Herrera", email = ""),
                        userStats = UserStatsModel(
                            followers = 17,
                            ratings = 20,
                            following = 11
                        ),
                        onActionEvent = { event ->

                        }
                    )
                }
            }
        }
    }

}
