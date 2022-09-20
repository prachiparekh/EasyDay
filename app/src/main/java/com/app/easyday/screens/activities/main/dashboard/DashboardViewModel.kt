package com.app.easyday.screens.activities.main.dashboard

import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.app.easyday.app.sources.remote.apis.EasyDayApi
import com.app.easyday.app.sources.remote.model.UserModel
import com.app.easyday.navigation.SingleLiveEvent
import com.app.easyday.screens.activities.main.home.HomeFragment
import com.app.easyday.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    val api:EasyDayApi
) :BaseViewModel(){

    var userProfileData= MutableLiveData<UserModel?>()

    override fun onFragmentCreated() {
        super.onFragmentCreated()
        getProfile()
    }

    private fun getProfile() {
        api.getProfile()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                userProfileData.value = resp.data

            }, {
                userProfileData.value = null
            })
    }
}