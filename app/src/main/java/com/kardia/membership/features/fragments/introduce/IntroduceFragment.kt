package com.kardia.membership.features.fragments.introduce

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.kardia.membership.R
import com.kardia.membership.core.extension.gone
import com.kardia.membership.core.extension.invisible
import com.kardia.membership.core.extension.visible
import com.kardia.membership.core.platform.BaseFragment
import kotlinx.android.synthetic.main.fragment_introduce.*

class IntroduceFragment : BaseFragment() {
    private var page = 1
    override fun layoutId() = R.layout.fragment_introduce

    companion object {
        const val PAGE = "PAGE"

        fun newInstance(page: Int): IntroduceFragment {
            val fragment = IntroduceFragment()
            val args = Bundle()
            args.putInt(PAGE, page)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        arguments?.let {
            page = it.getInt(PAGE, 1)
        }
    }

    override fun initViews() {
        llButton.invisible()
        when (page) {
            1 -> {
                ivBackground.setImageResource(R.drawable.background_introduce_1)
                tvTitle.text = getString(R.string.title_content_introduce_1)
                tvDesc.text = getString(R.string.desc_content_introduce_1)
            }
            2 -> {
                ivBackground.setImageResource(R.drawable.background_introduce_2)
                rlMain.setBackgroundColor(getColorDrawable(R.color.color_9A77FF))
                tvTitle.text = getString(R.string.title_content_introduce_2)
                tvDesc.text = getString(R.string.desc_content_introduce_2)
            }
            3 -> {
                llButton.visible()
                rlMain.background = getImageDrawable(R.drawable.background_introduce_3)
                tvTitle.text = getString(R.string.title_content_introduce_3)
                tvDesc.text = getString(R.string.desc_content_introduce_3)
            }
        }
    }

    override fun initEvents() {
        btSkip.setOnClickListener {
            finish()
            mNavigator.showSelectAccount(activity)
        }
        btSignIn.setOnClickListener {
            finish()
            mNavigator.showLogin(activity)
        }
    }

    override fun loadData() {
    }

    override fun reloadData() {
    }
}