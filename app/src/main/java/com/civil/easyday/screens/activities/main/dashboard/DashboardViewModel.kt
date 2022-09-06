package com.civil.easyday.screens.activities.main.dashboard

import com.civil.easyday.app.sources.remote.apis.EasyDayApi
import com.civil.easyday.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val api:EasyDayApi
) :BaseViewModel(){
}