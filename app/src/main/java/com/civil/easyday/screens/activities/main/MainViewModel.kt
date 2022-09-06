package com.civil.easyday.screens.activities.main

import com.civil.easyday.app.sources.remote.apis.EasyDayApi
import com.civil.easyday.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val api : EasyDayApi
):BaseViewModel(){
}