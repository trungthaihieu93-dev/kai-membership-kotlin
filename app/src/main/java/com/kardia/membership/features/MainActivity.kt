package com.kardia.membership.features

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kardia.membership.R
import com.kardia.membership.core.extension.*
import com.kardia.membership.core.platform.BaseActivity
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.domain.entities.user.UserInfoEntity
import com.kardia.membership.features.fragments.games.GamesFragment
import com.kardia.membership.features.fragments.mission.MissionFragment
import com.kardia.membership.features.fragments.news.NewsFragment
import com.kardia.membership.features.fragments.utilities.UtilitiesFragment
import com.kardia.membership.features.fragments.wallet.WalletFragment
import com.kardia.membership.features.viewmodel.UserViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {
    private lateinit var userViewModel: UserViewModel

    companion object {
        fun callingIntent(context: Context) = Intent(context, MainActivity::class.java)
        const val TAB = "tab"
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
        setContentView(R.layout.activity_main)

        bottom_navigation.setOnNavigationItemSelectedListener(this)
        changeColorStatusBarMain(true)
        userViewModel.getUserInfo()
        tvHeader.text = getString(R.string.text_navigation_news)

        ivProfileMain.setOnClickListener {
            mNavigator.showProfile(this)
        }

        ivAvatarMain.setOnClickListener {
            mNavigator.showProfile(this)
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData(){
        ivAvatarMain.gone()
        userInfoCache.get()?.user_info?.avatar?.let{
            ivAvatarMain.visible()
            ivAvatarMain.loadFromUrlRounded(it,8f)
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
            R.id.navigation_games -> {
                if (!getCurrentFragmentName().equals(
                        GamesFragment::class.java.simpleName,
                        true
                    )
                ) {
                    BaseFragment.setFragment(supportFragmentManager, GamesFragment())
                    tvHeader.text = getString(R.string.text_navigation_games)
                    tvHeader.setTextColor(getColor(R.color.color_DE000000))
                    changeColorStatusBarMain(true)
                }
                return true
            }
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
            loadData()
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
}