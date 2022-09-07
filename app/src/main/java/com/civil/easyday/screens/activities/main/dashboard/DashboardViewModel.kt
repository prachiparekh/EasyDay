package com.civil.easyday.screens.activities.main.dashboard

import androidx.fragment.app.Fragment
import com.civil.easyday.app.sources.remote.apis.EasyDayApi
import com.civil.easyday.navigation.SingleLiveEvent
import com.civil.easyday.screens.activities.main.home.HomeFragment
import com.civil.easyday.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val api:EasyDayApi
) :BaseViewModel(){

    val actionStream: SingleLiveEvent<ACTION> = SingleLiveEvent()

    sealed class ACTION {
        class OpenChildFragment(val fragment: Fragment, val tag: String?) : ACTION()
    }

    override fun onFragmentCreated() {
        super.onFragmentCreated()
        actionStream.value = ACTION.OpenChildFragment(HomeFragment(), HomeFragment.TAG)
    }
}