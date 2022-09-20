package com.civil.easyday.screens.activities.main.dashboard

import android.view.KeyEvent
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.civil.easyday.R
import com.civil.easyday.screens.activities.auth.LoginFragmentDirections
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

        requireView().isFocusableInTouchMode = true
        requireView().requestFocus()
        requireView().setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                requireActivity().finishAffinity()
                true
            } else false
        }

        openChildFragment(HomeFragment(), HomeFragment.TAG)
        bottomNavigationView.setOnItemSelectedListener {
            false
           /* when (it.itemId) {
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
            true*/
        }

        add.setOnClickListener {
           /* val action = DashboardFragmentDirections.dashboardToCreateTask()
            val nav: NavController = Navigation.findNavController(requireView())
            if (nav.currentDestination != null && nav.currentDestination?.id == R.id.dashboardFragment) {
                nav.navigate(action)
            }*/
        }
    }

    private fun openChildFragment(fragment: Fragment, tag: String?) {

        childFragmentManager.beginTransaction()
            .replace(R.id.childContent, fragment)
            .addToBackStack(tag)
            .commit()

    }

    override fun setObservers() {
        viewModel.userProfileData.observe(viewLifecycleOwner) { userData ->
            if (userData?.profileImage != null) {
                val options = RequestOptions()
                profile.clipToOutline = true
                Glide.with(requireContext())
                    .load(userData.profileImage)
                    .apply(
                        options.centerCrop()
                            .skipMemoryCache(true)
                            .priority(Priority.HIGH)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                    )
                    .into(profile)
            }
        }
    }

}