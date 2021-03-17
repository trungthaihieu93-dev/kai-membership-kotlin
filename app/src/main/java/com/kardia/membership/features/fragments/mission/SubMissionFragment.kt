package com.kardia.membership.features.fragments.mission

import android.os.Bundle
import android.os.Handler
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.Quest
import com.kardia.membership.data.entities.Transaction
import com.kardia.membership.data.entities.eventbus.GetListDailyMissionEvent
import com.kardia.membership.data.entities.eventbus.GetListMonthMissionEvent
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.utils.DataConstants
import com.kardia.membership.features.viewmodel.QuestViewModel
import kotlinx.android.synthetic.main.fragment_mission_sub.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class SubMissionFragment : BaseFragment() {
    @Inject
    lateinit var missionAdapter: MissionAdapter

    private var type: String? = null

    override fun layoutId() = R.layout.fragment_mission_sub

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

    }

    companion object {
        const val TYPE = "TYPE"

        fun newInstance(status: String?): SubMissionFragment {
            val fragment = SubMissionFragment()
            val args = Bundle()
            args.putString(TYPE, status)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEventReloadDaily(getListDailyMissionEvent: GetListDailyMissionEvent) {
        if (type == AppConstants.TYPE_QUEST_DAILY) {
            missionAdapter.collection = getListDailyMissionEvent.list
            val eventReload =
                EventBus.getDefault().getStickyEvent(GetListDailyMissionEvent::class.java)
            eventReload?.let {
                EventBus.getDefault().removeStickyEvent(it)
            }
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEventReloadMonth(getListMonthMissionEvent: GetListMonthMissionEvent) {
        if (type == AppConstants.TYPE_QUEST_MONTH) {
            missionAdapter.collection = getListMonthMissionEvent.list
            val eventReload =
                EventBus.getDefault().getStickyEvent(GetListMonthMissionEvent::class.java)
            eventReload?.let {
                EventBus.getDefault().removeStickyEvent(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.let {
            type = it.getString(TYPE)
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        rvMission.adapter = missionAdapter
    }

    override fun initEvents() {

    }

    override fun loadData() {

    }

    override fun reloadData() {

    }
}