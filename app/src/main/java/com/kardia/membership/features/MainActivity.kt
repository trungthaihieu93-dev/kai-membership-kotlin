package com.kardia.membership.features

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kardia.membership.R
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
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
            observe(userInfoEntity, ::onReceiveUserInfoEntity)
        }
        setContentView(R.layout.activity_main)
        changeColorStatusBar()

        bottom_navigation.setOnNavigationItemSelectedListener(this)

        userViewModel.getUserInfo()
        tvHeader.text = getString(R.string.text_navigation_news)
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
                }
                return true
            }
        }
        return false
    }

    private fun onReceiveUserInfoEntity(entity: UserInfoEntity?) {
        entity?.data?.let {
            userInfoCache.put(it)
        }
    }
}