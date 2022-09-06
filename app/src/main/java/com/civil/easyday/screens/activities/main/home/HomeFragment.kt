package com.civil.easyday.screens.activities.main.home

import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import me.toptas.fancyshowcase.FancyShowCaseView


@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel>() {

    companion object {
        const val TAG = "HomeFragment"
    }

    override fun getContentView() = R.layout.fragment_home

    override fun initUi() {

        FancyShowCaseView.Builder(requireActivity())
            .focusOn(cta)
            .customView(R.layout.showcaseview1,null)
            .build()
            .show()
    }

    override fun setObservers() {
    }

}
