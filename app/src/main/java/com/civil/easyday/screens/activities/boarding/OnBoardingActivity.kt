package com.civil.easyday.screens.activities.boarding

import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_on_boarding.*
import ro.westaco.carhome.presentation.screens.onboarding.OnboardingItem

@AndroidEntryPoint
class OnBoardingActivity : BaseActivity<OnBoardingViewModel>(),
    OnBoardingAdapter.BoardingInterface {
    private lateinit var adapter: OnBoardingAdapter
    var items: List<OnboardingItem> = arrayListOf()
    override fun getContentView() = R.layout.activity_on_boarding

    override fun setupUi() {
        adapter = OnBoardingAdapter()
        pager.adapter = adapter

    }

    override fun setupObservers() {
        viewModel.pagerItems.observe(this) { items ->
            this.items = items
            adapter.setData(items, this)
        }
    }

    override fun onClickNext(nextPosition: Int) {

        if (nextPosition == items.size)
            viewModel.navigateToAuth()
        else
            pager.currentItem = nextPosition
    }
}