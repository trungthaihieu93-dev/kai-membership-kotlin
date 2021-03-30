package com.kardia.membership.features.fragments.news

import android.os.Bundle
import com.kardia.membership.R
import com.kardia.membership.core.extension.observe
import com.kardia.membership.core.extension.viewModel
import com.kardia.membership.core.platform.BaseFragment
import com.kardia.membership.core.platform.OnItemClickListener
import com.kardia.membership.data.entities.NewsFeature
import com.kardia.membership.data.entities.NewsLatest
import com.kardia.membership.domain.entities.news.FeatureNewsEntity
import com.kardia.membership.domain.entities.news.LatestNewsEntity
import com.kardia.membership.features.fragments.new_passcode.ChangePasswordSuccessBottomSheet
import com.kardia.membership.features.utils.DataConstants
import com.kardia.membership.features.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import javax.inject.Inject

class NewsFragment : BaseFragment() {
    @Inject
    lateinit var newsAdapter: NewsAdapter

    @Inject
    lateinit var latestNewsAdapter: LatestNewsAdapter

    private lateinit var newsViewModel: NewsViewModel

    override fun layoutId(): Int {
        return R.layout.fragment_news
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        newsViewModel = viewModel(viewModelFactory) {
            observe(featureNewsEntity, ::onReceiveFeatureNewsEntity)
            observe(latestNewsEntity, ::onReceiveLatestNewsEntity)
            observe(failureData, ::handleFailure)
        }
    }

    override fun initViews() {
        rvNews.adapter = newsAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {
                    val news = item as NewsFeature
                    val callback = object : DetailNewsBottomSheet.CallBack {
                        override fun onDismiss() {
                        }
                    }
                    mNavigator.showDetailNews(activity, news.guid, callback)
                }
            }
        }

        rvLatestNews.adapter = latestNewsAdapter.apply {
            onItemClickListener = object : OnItemClickListener {
                override fun onItemClick(item: Any?, position: Int) {
                    val news = item as NewsLatest
                    val link = "https://twitter.com/KardiaChain/status/" + news.id_str
                    val callback = object : DetailNewsBottomSheet.CallBack {
                        override fun onDismiss() {
                        }
                    }
                    mNavigator.showDetailNews(activity, link, callback)
                }
            }
        }
    }

    override fun initEvents() {
    }

    override fun loadData() {
        if (DataConstants.FEATURE_NEWS_ENTITY == null) {
            newsViewModel.getFeatureNews()
        } else {
            onReceiveFeatureNewsEntity(DataConstants.FEATURE_NEWS_ENTITY)
        }

        if (DataConstants.LATEST_NEWS_ENTITY == null) {
            newsViewModel.getLatestNews()
        } else {
            onReceiveLatestNewsEntity(DataConstants.LATEST_NEWS_ENTITY)
        }
    }

    override fun reloadData() {
    }

    private fun onReceiveFeatureNewsEntity(entity: FeatureNewsEntity?) {
        hideProgress()
        DataConstants.FEATURE_NEWS_ENTITY = entity
        entity?.items?.let {
            newsAdapter.collection = it as ArrayList<NewsFeature>
        }
    }

    private fun onReceiveLatestNewsEntity(entity: LatestNewsEntity?) {
        hideProgress()
        DataConstants.LATEST_NEWS_ENTITY = entity
        entity?.statuses?.let {
            latestNewsAdapter.collection = it as ArrayList<NewsLatest>
        }
    }
}