package com.kardia.membership.features.fragments.introduce

import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.kardia.membership.R
import com.kardia.membership.core.extension.gone
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.features.utils.ViewPagerFragment
import kotlinx.android.synthetic.main.fragment_introduce_management.*
import javax.inject.Inject

class IntroduceManagementFragment : BaseFragment() {
    private var viewPagerFragment: ViewPagerFragment? = null
    private var currentPage = 0
    override fun layoutId() = R.layout.fragment_introduce_management

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        viewPagerFragment = ViewPagerFragment(childFragmentManager)
        viewPagerFragment?.addFragment(
            IntroduceFragment.newInstance(1),
            ""
        )
        viewPagerFragment?.addFragment(
            IntroduceFragment.newInstance(2),
            ""
        )
        viewPagerFragment?.addFragment(
            IntroduceFragment.newInstance(3),
            ""
        )
        vpIntroduce.adapter = viewPagerFragment
        vpIntroduce.offscreenPageLimit = 3
        dots_indicator.setViewPager(vpIntroduce)
    }

    override fun initEvents() {
        fabNext.setOnClickListener {
            vpIntroduce.currentItem = currentPage + 1
        }
        vpIntroduce.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                currentPage = position
                when (position) {
                    2 -> {
                        fabNext.gone()
                        dots_indicator.gone()
                    }
                    else -> {
                        fabNext.visible()
                        dots_indicator.visible()
                    }
                }
            }
        })
    }

    override fun loadData() {
    }

    override fun reloadData() {
    }

}