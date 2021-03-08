package com.kardia.membership.features.fragments.news

import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.User
import com.kardia.membership.features.fragments.select_account.SelectAccountAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment : BaseFragment() {
    @Inject
    lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var latestNewsAdapter: LatestNewsAdapter

    override fun layoutId(): Int {
        return R.layout.fragment_news
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun initViews() {
        rvNews.adapter = newsAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {

                }
            }
        }

        rvLatestNews.adapter = latestNewsAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {

                }
            }
        }
    }

    override fun initEvents() {
    }

    override fun loadData() {
    }

    override fun reloadData() {
    }
}