package com.kardia.membership.features.fragments.kai_starter

import android.os.Bundle
import android.view.View
import com.kardia.membership.R
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.NewsFeature
import com.kardia.membership.features.fragments.news.DetailNewsBottomSheet
import kotlinx.android.synthetic.main.fragment_kai_starter.*
import kotlinx.android.synthetic.main.layout_toolbar.*
import javax.inject.Inject

class KAIStarterFragment : BaseFragment() {
    @Inject
    lateinit var ongoingProjectAdapter: ProjectAdapter

    @Inject
    lateinit var lockingProjectAdapter: ProjectAdapter

    @Inject
    lateinit var completedProjectAdapter: ProjectAdapter

    override fun layoutId() = R.layout.fragment_kai_starter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun initViews() {
        ivBack.visible()

        rvOngoingProject.adapter = ongoingProjectAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {
                }
            }
        }

        rvLockingProject.adapter = lockingProjectAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {
                }
            }
        }

        rvCompletedProjects.adapter = completedProjectAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {
                }
            }
        }
    }

    override fun initEvents() {
        ivBack.setOnClickListener {
            finish()
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {

    }

}