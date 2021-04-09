package com.kardia.membership.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.data.entities.eventbus.ReloadMissionEvent
import com.kardia.membership.data.entities.eventbus.ShowDialogReceiveSpinEvent
import com.kardia.membership.domain.entities.quest.UpdateProgressMissionEntity
import com.kardia.membership.domain.entities.tracking.TrackingActivityEntity
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.domain.usecases.quest.PostUpdateProgressMission
import com.kardia.membership.domain.usecases.tracking.PostTrackingActivityUseCase
import com.kardia.membership.features.fragments.mission.MissionFragment
import com.kardia.membership.features.fragments.news.NewsFragment
import com.kardia.membership.features.fragments.utilities.UtilitiesFragment
import com.kardia.membership.features.fragments.wallet.WalletFragment
import com.kardia.membership.features.utils.AppConstants
import com.kardia.membership.features.viewmodel.QuestViewModel
import com.kardia.membership.features.viewmodel.TrackingViewModel
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var userViewModel: UserViewModel
    private lateinit var trackingViewModel: TrackingViewModel
    private lateinit var questViewModel: QuestViewModel
    private var isFirst = false
    private var slide_down: Animation? = null
    private var slide_up: Animation? = null
    private var trackingActivityEntity: TrackingActivityEntity? = null
    private var isShowSpin = false
    companion object {
        fun callingIntent(context: Context) = Intent(context, MainActivity::class.java)
        const val TAB = "tab"
        const val IS_SHOW_SPIN = "isShowSpin"
    }

    override fun fragment(): BaseFragment? {
        return NewsFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        userViewModel = viewModel(viewModelFactory) {
            observe(getUserInfoEntity, ::onReceiveUserInfoEntity)
        }
        trackingViewModel = viewModel(viewModelFactory) {
            observe(trackingActivityEntity, ::onReceiveTrackingActivityEntity)
        }
        questViewModel = viewModel(viewModelFactory) {
            observe(updateProgressMissionEntity, ::onReceiveUpdateProgressMissionEntity)
        }

        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener(this)
        changeColorStatusBarMain(true)
        tvHeader.text = getString(R.string.text_navigation_news)

        ivProfileMain.setOnClickListener {
            moveProfile()
        }

        ivAvatarMain.setOnClickListener {
            moveProfile()
        }

        val tab = intent.getIntExtra(TAB, 0)
        if (tab != 0) {
            Handler().postDelayed({
                selectNavigation(tab)
            }, 500)
        }


//        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
//        collapsingToolbarLayout.setTitle("");
        slide_down = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_down_noti
        )

        slide_up = AnimationUtils.loadAnimation(
            applicationContext,
            R.anim.slide_up_noti
        )

        userTokenCache.get()?.isFirst?.let {
            isFirst = it
        }
        loadData()

        if(intent.getBooleanExtra(IS_SHOW_SPIN,false)){
            mNavigator.showSpin(this)
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
    fun onEventShow(showDialogReceiveSpinEvent: ShowDialogReceiveSpinEvent) {
        Handler().postDelayed({
            showDialogReceiveSpin(getString(R.string.content_noti_receive_spin, 1))
        }, 500)
        val eventReload =
            EventBus.getDefault().getStickyEvent(ShowDialogReceiveSpinEvent::class.java)
        eventReload?.let {
            EventBus.getDefault().removeStickyEvent(it)
        }
    }

    private fun moveProfile() {
        if (isUserLogin) {
            mNavigator.showProfile(this)
        } else {
            mNavigator.showLogin(this)
        }
    }

    override fun onResume() {
        super.onResume()
        userViewModel.getUserInfo()
    }

    private fun loadData() {
        if (isUserLogin) {
            questViewModel.updateProgressMission(
                PostUpdateProgressMission.Params(
                    AppConstants.KEY_SIGN_IN,
                    1
                )
            )
        } else {
//            if (!isTrackingLogin) {
            trackingViewModel.trackingActivity(
                PostTrackingActivityUseCase.Params(
                    "guest",
                    AppConstants.DEVICE_ID
                )
            )
//            }
        }
    }

    private fun setDataUser() {
        ivAvatarMain.gone()
        ivSpinWallet.gone()
        userInfoCache.get()?.user_info?.let { user ->
            user.avatar?.let {
                ivAvatarMain.visible()
                ivAvatarMain.loadFromUrlRounded(it, 8f)
            }
            ivSpinWallet.visible()
            ivSpinWallet.setImageResource(R.drawable.ic_spin_main)
            ivSpinWallet.setOnClickListener {
            }
            user.spinturn?.let {
                if (it > 0) {
                    ivSpinWallet.setImageResource(R.drawable.ic_spin_active_main)
                    ivSpinWallet.setOnClickListener {
                        mNavigator.showSpin(this)
                    }
                } else {
                    ivSpinWallet.setImageResource(R.drawable.ic_spin_main)
                    ivSpinWallet.setOnClickListener {
                    }
                }
            }
        }
    }

    fun selectNavigation(menuID: Int) {
        onNavigationItemSelected(bottom_navigation.menu.findItem(menuID))
        bottom_navigation.selectedItemId = menuID
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_news -> {
                if (!getCurrentFragmentName().equals(
                        NewsFragment::class.java.simpleName,
                        true
                    )
                ) {
                    BaseFragment.setFragment(supportFragmentManager, NewsFragment())
                    tvHeader.text = getString(R.string.text_navigation_news)
                    tvHeader.setTextColor(getColor(R.color.color_DE000000))
                    changeColorStatusBarMain(true)
                }
                return true
            }
            R.id.navigation_mission -> {
                if (!getCurrentFragmentName().equals(
                        MissionFragment::class.java.simpleName,
                        true
                    )
                ) {
                    BaseFragment.setFragment(supportFragmentManager, MissionFragment())
                    tvHeader.text = getString(R.string.text_navigation_mission)
                    tvHeader.setTextColor(getColor(R.color.white))
                    changeColorStatusBarMain(false)
                }
                return true
            }
//            R.id.navigation_games -> {
//                if (!getCurrentFragmentName().equals(
//                        GamesFragment::class.java.simpleName,
//                        true
//                    )
//                ) {
//                    BaseFragment.setFragment(supportFragmentManager, GamesFragment())
//                    tvHeader.text = getString(R.string.text_navigation_games)
//                    tvHeader.setTextColor(getColor(R.color.color_DE000000))
//                    changeColorStatusBarMain(true)
//                }
//                return true
//            }
            R.id.navigation_wallet -> {
                if (!getCurrentFragmentName().equals(
                        WalletFragment::class.java.simpleName,
                        true
                    )
                ) {
                    BaseFragment.setFragment(supportFragmentManager, WalletFragment())
                    tvHeader.text = getString(R.string.text_navigation_wallet)
                    tvHeader.setTextColor(getColor(R.color.color_DE000000))
                    changeColorStatusBarMain(true)
                }
                return true
            }
            R.id.navigation_utilities -> {
                if (!getCurrentFragmentName().equals(
                        UtilitiesFragment::class.java.simpleName,
                        true
                    )
                ) {
                    BaseFragment.setFragment(supportFragmentManager, UtilitiesFragment())
                    tvHeader.text = getString(R.string.text_navigation_utilities)
                    tvHeader.setTextColor(getColor(R.color.color_DE000000))
                    changeColorStatusBarMain(true)
                }
                return true
            }
        }
        return false
    }

    private fun onReceiveUserInfoEntity(entity: UserInfoEntity?) {
        entity?.data?.let {
            userInfoCache.put(it)
            setDataUser()
            if (trackingActivityEntity == null) {
                trackingViewModel.trackingActivity(
                    PostTrackingActivityUseCase.Params(
                        it.user_info?._id,
                        AppConstants.DEVICE_ID
                    )
                )
            }
        }
    }

    private fun onReceiveTrackingActivityEntity(entity: TrackingActivityEntity?) {
        trackingActivityEntity = entity
    }

    private fun onReceiveUpdateProgressMissionEntity(entity: UpdateProgressMissionEntity?) {
        entity?.data?.is_completed?.let {
            when {
                it -> {
                    showDialogReceiveSpin(getString(R.string.content_noti_receive_spin, 2))
                }
                isFirst -> {
                    showDialogReceiveSpin(getString(R.string.content_noti_receive_spin, 1))
                }
                else -> {
                }
            }
        }
    }

    private fun changeColorStatusBarMain(isChange: Boolean) {
        if (isChange) {
            val decor = window.decorView
            decor.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = ContextCompat.getColor(this, R.color.color_background_app)
            //                StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.white));
        } else {
            val decor = window.decorView
            decor.systemUiVisibility = 0
            window.statusBarColor = ContextCompat.getColor(this, R.color.color_0A1F44)
            //                StatusBarUtil.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimary));
        }
    }

    private fun showDialogReceiveSpin(message: String) {
        tvContentReceiveSpin.text = message
        rlNotification.visible()
        rlNotification.startAnimation(slide_down)
        Handler().postDelayed({
            rlNotification.startAnimation(slide_up)
            rlNotification.clearAnimation()
            rlNotification.gone()
        }, 3000)
    }
}