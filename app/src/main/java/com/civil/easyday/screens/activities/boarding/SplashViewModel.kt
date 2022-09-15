package com.civil.easyday.screens.activities.boarding

import androidx.lifecycle.MutableLiveData
import com.civil.easyday.app.sources.remote.apis.EasyDayApi
import com.civil.easyday.app.sources.remote.model.UserModel
import com.civil.easyday.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    val api: EasyDayApi
) : BaseViewModel() {

    var userProfileData= MutableLiveData<UserModel?>()

    override fun onActivityCreated() {
        super.onActivityCreated()
        api.getProfile()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({resp->
                userProfileData.value=resp.data
            }, {
                userProfileData.value=null
            })
    }
}