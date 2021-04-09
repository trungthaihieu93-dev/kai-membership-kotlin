package com.kardia.membership.features.fragments.mission

import android.content.Context
import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.eventbus.GetListDailyMissionEvent
import com.kardia.membership.data.entities.eventbus.GetListMonthMissionEvent
import com.kardia.membership.features.utils.AppConstants
import kotlinx.android.synthetic.main.fragment_mission_sub.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class SubMissionFragment : BaseFragment() {
    @Inject
    lateinit var questAdapter: QuestAdapter

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        EventBus.getDefault().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    fun onEventReloadDaily(getListDailyMissionEvent: GetListDailyMissionEvent) {
        if (type == AppConstants.TYPE_QUEST_DAILY) {
            questAdapter.collection = getListDailyMissionEvent.list
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
            questAdapter.collection = getListMonthMissionEvent.list
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
        rvMission.adapter = questAdapter
    }

    override fun initEvents() {

    }

    override fun loadData() {

    }

    override fun reloadData() {

    }
}