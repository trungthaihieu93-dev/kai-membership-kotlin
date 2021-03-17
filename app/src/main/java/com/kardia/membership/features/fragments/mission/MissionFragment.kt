package com.kardia.membership.features.fragments.mission

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.Quest
import com.kardia.membership.data.entities.eventbus.GetListDailyMissionEvent
import com.kardia.membership.data.entities.eventbus.GetListMonthMissionEvent
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.features.fragments.wallet.TransactionAdapter
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.utils.DataConstants
import com.kardia.membership.features.utils.ViewPagerFragment
import com.kardia.membership.features.viewmodel.QuestViewModel
import kotlinx.android.synthetic.main.fragment_mission.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

class MissionFragment : BaseFragment() {
    private lateinit var questViewModel: QuestViewModel

    private var viewPagerFragment: ViewPagerFragment? = null

    override fun layoutId() = R.layout.fragment_mission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        questViewModel = viewModel(viewModelFactory) {
            observe(questsEntity, ::onReceiveQuestsEntity)
            observe(questsUserEntity, ::onReceiveQuestsUserEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        viewPagerFragment = ViewPagerFragment(childFragmentManager)

        viewPagerFragment?.addFragment(
            SubMissionFragment.newInstance(AppConstants.TYPE_QUEST_DAILY),
            getString(R.string.daily_mission)
        )
        viewPagerFragment?.addFragment(
            SubMissionFragment.newInstance(AppConstants.TYPE_QUEST_MONTH),
            getString(R.string.monthly_mission)
        )

        vpMission.adapter = viewPagerFragment
        vpMission.offscreenPageLimit = 2
        tlMission.setupWithViewPager(vpMission)
    }

    override fun initEvents() {

    }

    override fun loadData() {
        if (DataConstants.QUEST_ENTITY == null) {
            questViewModel.getQuests()
        } else {
            sendData()
        }
    }

    override fun reloadData() {

    }

    private fun onReceiveQuestsEntity(entity: QuestsEntity?) {
        hideProgress()
        DataConstants.QUEST_ENTITY = entity
        questViewModel.getQuestsUser()
    }

    private fun onReceiveQuestsUserEntity(entity: QuestsEntity?) {
        forceHide()
        entity?.data?.forEach { questUser ->
            DataConstants.QUEST_ENTITY?.data?.forEach { quest ->
                if (questUser.key == quest.key) {
                    quest.processing = questUser.process
                }
            }

        }
        sendData()
    }

    private fun sendData() {
        val daily = ArrayList<Quest>()
        val month = ArrayList<Quest>()
        DataConstants.QUEST_ENTITY?.data?.forEach { quest ->
            if (quest.type == AppConstants.TYPE_QUEST_DAILY) {
                daily.add(quest)
            } else {
                month.add(quest)
            }
        }
        EventBus.getDefault().postSticky(GetListDailyMissionEvent(daily))
        EventBus.getDefault().postSticky(GetListMonthMissionEvent(month))
    }
}