package com.soyvictorherrera.nosedive.presentation.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.soyvictorherrera.nosedive.R
import com.soyvictorherrera.nosedive.domain.model.UserModel
import com.soyvictorherrera.nosedive.domain.model.UserStatsModel
import com.soyvictorherrera.nosedive.presentation.theme.NosediveTheme
import com.soyvictorherrera.nosedive.presentation.ui.Screen
import com.soyvictorherrera.nosedive.presentation.ui.navigateInTo
import com.soyvictorherrera.nosedive.presentation.ui.navigateOutTo
import com.soyvictorherrera.nosedive.presentation.ui.shared.SessionViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val sessionViewModel: SessionViewModel by activityViewModels()
    private val viewModel: HomeViewModel by viewModels()
    private val stubUser = UserModel(name = "", email = "")

    @ExperimentalMaterialApi
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.navigateTo.observe(viewLifecycleOwner) { navigateToEvent ->
            navigateToEvent.getContentIfNotHandled()?.let { navigateTo ->
                navigateInTo(
                    to = navigateTo,
                    from = Screen.Home
                )
            }
        }

        return ComposeView(requireContext()).apply {
            id = R.id.homeFragment

            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )

            setContent {
                NosediveTheme {
                    val userState by sessionViewModel.user.observeAsState(stubUser)
                    val scope = rememberCoroutineScope()
                    val sheetState = rememberModalBottomSheetState(initialValue = Hidden)
                    val scaffoldState = rememberScaffoldState()

                    viewModel.bottomSheetEvent.observe(viewLifecycleOwner) { sheetEvent ->
                        sheetEvent.getContentIfNotHandled()?.let { event ->
                            when (event) {
                                BottomSheetEvent.ShowAddFriendBottomSheet -> {
                                    scope.launch {
                                        sheetState.show()
                                    }
                                }
                            }
                        }
                    }

                    HomeContentView(
                        user = userState,
                        userStats = UserStatsModel(),
                        sheetState = sheetState,
                        scaffoldState = scaffoldState,
                    ) { event ->
                        when (event) {
                            HomeEvent.AddFriend -> {
                                viewModel.addFriend()
                            }
                            HomeEvent.NewRate -> {
                            }
                            HomeEvent.ViewFriends -> {
                                viewModel.viewFriendList()
                            }
                            HomeEvent.ViewNotifications -> {
                            }
                            HomeEvent.ViewProfile -> {
                                viewModel.viewProfile()
                            }
                            HomeEvent.CodeScan -> {
                                scope.launch { sheetState.hide() }
                                viewModel.codeScan()
                            }
                            HomeEvent.CodeShare -> {
                                scope.launch { sheetState.hide() }
                                viewModel.codeShare()
                            }
                        }
                    }
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionViewModel.state.observe(viewLifecycleOwner) { sessionState ->
            Timber.i("sessionState change")
            if (sessionState == SessionState.SignedOut) {
                navigateOutTo(Screen.SignIn, Screen.Home)
            }
        }
    }

}
