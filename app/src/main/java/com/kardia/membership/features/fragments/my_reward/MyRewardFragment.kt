package com.kardia.membership.features.fragments.my_reward

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.History
import com.kardia.membership.domain.entities.user.ChangePasswordEntity
import com.kardia.membership.domain.entities.user.HistoryEntity
import com.kardia.membership.features.fragments.mission.QuestAdapter
import com.kardia.membership.features.fragments.new_passcode.ChangePasswordSuccessBottomSheet
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.fragment_my_reward.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

class MyRewardFragment : BaseFragment() {
    @Inject
    lateinit var historyAdapter: HistoryAdapter

    private lateinit var userViewModel: UserViewModel

    override fun layoutId() = R.layout.fragment_my_reward

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        userViewModel = viewModel(viewModelFactory) {
            observe(historyEntity, ::onReceiveHistoryEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()
        flPaddingTop.visible()

        rvHistory.adapter = historyAdapter
    }

    override fun initEvents() {
        ivBack.setOnClickListener {
            close()
        }

        btGoToMission.setOnClickListener {
            mNavigator.showMainWithTab(activity, R.id.navigation_mission)
        }
    }

    override fun loadData() {
        showProgress()
        userViewModel.getHistory()
    }

    override fun reloadData() {
        userViewModel.getHistory()
    }

    private fun onReceiveHistoryEntity(entity: HistoryEntity?) {
        hideProgress()
        llDataReward.gone()
        llNoDataReward.visible()
        rlFillTheForm.gone()
        entity?.data?.let {
            if (it.isNotEmpty()) {
                llDataReward.visible()
                llNoDataReward.gone()
                it.forEach { history ->
                    history.rewardName?.let { name ->
                        if (name.contains("Loa")) {
                            rlFillTheForm.visible()
                        }
                    }
                }
                historyAdapter.collection = it as ArrayList<History>
            }
        }
    }
}