package com.civil.easyday.screens.activities.main.dashboard

import androidx.fragment.app.Fragment
import com.civil.easyday.R
import com.civil.easyday.screens.activities.main.home.HomeFragment
import com.civil.easyday.screens.activities.main.inbox.InboxFragment
import com.civil.easyday.screens.activities.main.more.MoreFragment
import com.civil.easyday.screens.activities.main.reports.ReportsFragment
import com.civil.easyday.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dashboard.*

@AndroidEntryPoint
class DashboardFragment : BaseFragment<DashboardViewModel>() {


    override fun getContentView() = R.layout.fragment_dashboard

    override fun initUi() {

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    openChildFragment(HomeFragment(), HomeFragment.TAG)
                }
                R.id.inbox -> {
                    openChildFragment(InboxFragment(), InboxFragment.TAG)
                }
                R.id.reports -> {
                    openChildFragment(ReportsFragment(), ReportsFragment.TAG)
                }
                R.id.more -> {
                    openChildFragment(InboxFragment(), MoreFragment.TAG)
                }
            }
            true
        }
    }

    private fun openChildFragment(fragment: Fragment, tag: String?) {

        childFragmentManager.beginTransaction()
            .replace(R.id.childContent, fragment)
            .addToBackStack(tag)
            .commit()

    }

    override fun setObservers() {
        viewModel.actionStream.observe(viewLifecycleOwner) {
            when (it) {
                is DashboardViewModel.ACTION.OpenChildFragment -> {
                    openChildFragment(it.fragment, it.tag)
                }
            }
        }
    }
}