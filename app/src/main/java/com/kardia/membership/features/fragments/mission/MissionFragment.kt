package com.kardia.membership.features.fragments.mission

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import com.google.android.play.core.review.ReviewManagerFactory
import com.kardia.membership.R
import com.kardia.membership.core.extension.gone
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.Quest
import com.kardia.membership.data.entities.eventbus.ReloadMissionEvent
import com.kardia.membership.data.entities.eventbus.ShowDialogReceiveSpinEvent
import com.kardia.membership.domain.entities.passcode.VerifyPasscodeEntity
import com.kardia.membership.domain.entities.quest.CheckProgressMissionEntity
import com.kardia.membership.domain.entities.quest.QuestsEntity
import com.kardia.membership.domain.entities.quest.UpdateProgressMissionEntity
import com.kardia.membership.domain.usecases.passcode.PostVerifyPasscodeUseCase
import com.kardia.membership.domain.usecases.quest.PostCheckProgressMission
import com.kardia.membership.domain.usecases.quest.PostUpdateProgressMission
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.utils.AppLog
import com.kardia.membership.features.utils.DataConstants
import com.kardia.membership.features.utils.ViewPagerFragment
import com.kardia.membership.features.viewmodel.PasscodeViewModel
import com.kardia.membership.features.viewmodel.QuestViewModel
import kotlinx.android.synthetic.main.fragment_mission.*
import kotlinx.android.synthetic.main.fragment_mission.rvMission
import kotlinx.android.synthetic.main.fragment_mission_sub.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


class MissionFragment : BaseFragment() {
    @Inject
    lateinit var questAdapter: QuestAdapter

    private lateinit var questViewModel: QuestViewModel
    private lateinit var passcodeViewModel: PasscodeViewModel

    private var reviewInfo: ReviewInfo? = null
    private lateinit var reviewManager: ReviewManager
    private var isVerifyEmail = false
    private var isChangeToVefiryEmail = false
    override fun layoutId() = R.layout.fragment_mission

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        questViewModel = viewModel(viewModelFactory) {
            observe(questsEntity, ::onReceiveQuestsEntity)
            observe(questsUserEntity, ::onReceiveQuestsUserEntity)
            observe(updateProgressMissionEntity, ::onReceiveUpdateProgressMissionEntity)
            observe(checkProgressMissionEntity, ::onReceiveCheckProgressMissionEntity)
            observe(checkProgressMissionOnlyEntity, ::onReceiveCheckProgressMissionOnlyEntity)
            observe(failureData, ::handleFailure)
        }
        passcodeViewModel = viewModel(viewModelFactory) {
            observe(verifyEntity, ::onReceiveVerifyEmailEntity)
            observe(failureData, ::handleFailure)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        swipeRefreshLayout = srlMission
        rvMission.adapter = questAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {
                    val quest = item as Quest
                    quest.key?.let { key ->
                        handleQuest(key)
                    }
                }
            }
        }
    }

    override fun initEvents() {

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
    fun onEventReload(reloadMissionEvent: ReloadMissionEvent) {
        reloadData()
        val eventReload =
            EventBus.getDefault().getStickyEvent(ReloadMissionEvent::class.java)
        eventReload?.let {
            EventBus.getDefault().removeStickyEvent(it)
        }
    }

    override fun loadData() {
        if (DataConstants.QUEST_ENTITY == null) {
            questViewModel.getQuests()
        } else {
            sendData()
        }
    }

    override fun reloadData() {
        questViewModel.getQuests()
    }

    private fun onReceiveQuestsEntity(entity: QuestsEntity?) {
        hideProgress()
        DataConstants.QUEST_ENTITY = entity
        if (isUserLogin) {
            questViewModel.getQuestsUser()
        } else {
            sendData()
        }
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
        sflMission.stopShimmer()
        sflMission.gone()
        DataConstants.QUEST_ENTITY?.data?.let {
            questAdapter.collection = it as ArrayList<Quest>
        }
    }

    private fun handleQuest(key: String) {
        when (key) {
            AppConstants.KEY_RATE_APP -> {
                activity?.let { activity ->
                    reviewManager = ReviewManagerFactory.create(activity)
                    val request = reviewManager.requestReviewFlow()
                    request.addOnCompleteListener { request ->
                        if (request.isSuccessful) {
                            // We got the ReviewInfo object
                            reviewInfo = request.result
                        } else {
                            // There was some problem, continue regardless of the result.
                            reviewInfo = null
                        }
                    }
                    reviewInfo?.let {
                        val flow = reviewManager.launchReviewFlow(activity, it)
                        flow.addOnSuccessListener {
//                            updateProgressMission(AppConstants.KEY_RATE_APP)
                            AppLog.d("ReviewApp", "Success")
                        }
                        flow.addOnFailureListener { exception ->
                            exception.message?.let { message ->
                                AppLog.d("ReviewApp", message)
                            }
                        }
                        flow.addOnCompleteListener {
                            AppLog.d("ReviewApp", "Complete")
                        }
                    }
                }

            }
            AppConstants.KEY_SEND_KAI -> {
                if (isUserLogin) {
                    mNavigator.showSend(activity)
                } else {
                    showLogin(activity)
                }
            }
            AppConstants.KEY_VERIFY_EMAIL -> {
                if (isUserLogin) {
                    showProgress()
                    questViewModel.checkProgressMission(
                        PostCheckProgressMission.Params(
                            userInfoCache.get()?.user_info?._id,
                            AppConstants.KEY_VERIFY_EMAIL
                        )
                    )
                } else {
                    showLogin(activity)
                }
            }
            AppConstants.KEY_FOLLOW_TWITTER -> {
                if (isUserLogin) {
                    configCache.get()?.data?.forEach { config ->
                        if (config.group_name == AppConstants.GROUP_NAME_TWITTER_URL) {
                            if (!config.config.isNullOrEmpty()) {
                                try {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(config.config[0].value)
                                        )
                                    )
                                    updateProgressMission(AppConstants.KEY_FOLLOW_TWITTER)
                                } catch (e: Exception) {
                                }
                                return@forEach
                            }
                        }
                    }
                } else {
                    showLogin(activity)
                }
            }
            AppConstants.KEY_LIKE_FACEBOOK -> {
                if (isUserLogin) {
                    configCache.get()?.data?.forEach { config ->
                        if (config.group_name == AppConstants.GROUP_NAME_FACEBOOK_URL) {
                            if (!config.config.isNullOrEmpty()) {
                                try {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(config.config[0].value)
                                        )
                                    )
                                    updateProgressMission(AppConstants.KEY_LIKE_FACEBOOK)
                                } catch (e: Exception) {
                                }
                                return@forEach
                            }
                        }
                    }
                } else {
                    showLogin(activity)
                }
            }
            AppConstants.KEY_JOIN_TELEGRAM -> {
                if (isUserLogin) {
                    configCache.get()?.data?.forEach { config ->
                        if (config.group_name == AppConstants.GROUP_NAME_TELEGRAM_URL) {
                            if (!config.config.isNullOrEmpty()) {
                                try {
                                    startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse(config.config[0].value)
                                        )
                                    )
                                    updateProgressMission(AppConstants.KEY_JOIN_TELEGRAM)
                                } catch (e: Exception) {
                                }
                                return@forEach
                            }
                        }
                    }
                } else {
                    showLogin(activity)
                }
            }
        }
    }

    private fun updateProgressMission(key: String) {
        questViewModel.updateProgressMission(
            PostUpdateProgressMission.Params(
                key,
                1
            )
        )
    }

    override fun onResume() {
        super.onResume()
        if (AppConstants.DONE_QUEST) {
            EventBus.getDefault().postSticky(ShowDialogReceiveSpinEvent())
            AppConstants.DONE_QUEST = false
        }
        if (isChangeToVefiryEmail) {
            isChangeToVefiryEmail = false
            questViewModel.checkProgressMissionOnly(
                PostCheckProgressMission.Params(
                    userInfoCache.get()?.user_info?._id,
                    AppConstants.KEY_VERIFY_EMAIL
                )
            )
        }
    }

    private fun onReceiveUpdateProgressMissionEntity(entity: UpdateProgressMissionEntity?) {
        entity?.data?.is_completed?.let {
            if (it) {
                AppConstants.DONE_QUEST = true
                questViewModel.getQuests()
            }
        }
    }

    private fun onReceiveVerifyEmailEntity(entity: VerifyPasscodeEntity?) {
        forceHide()
        mNavigator.showCheckMailActivity(activity, userInfoCache.get()?.kai_info?.email)
        isChangeToVefiryEmail = true
    }

    private fun onReceiveCheckProgressMissionEntity(entity: CheckProgressMissionEntity?) {
        hideProgress()
        entity?.data?.let {
            if (!it) {
                showProgress()
                passcodeViewModel.verifyPasscode(PostVerifyPasscodeUseCase.Params(userInfoCache.get()?.kai_info?.email))
            }
        }
    }

    private fun onReceiveCheckProgressMissionOnlyEntity(entity: CheckProgressMissionEntity?) {
        hideProgress()
        entity?.data?.let {
            if (it) {
                questViewModel.getQuests()
            }
        }
    }
}