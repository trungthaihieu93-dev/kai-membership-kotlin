package com.kardia.membership.features.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*

class ViewPagerFragment(manager: FragmentManager) : FragmentPagerAdapter(manager, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val arrayFragmentList =
        ArrayList<Fragment>()
    private val arrayListTitle = ArrayList<String>()
    override fun getItem(position: Int): Fragment {
        return arrayFragmentList[position]
    }

    override fun getCount(): Int {
        return arrayFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return arrayListTitle[position]
    }

    fun addFragment(fragment: Fragment, title: String) {
        arrayFragmentList.add(fragment)
        arrayListTitle.add(title)
    }
}