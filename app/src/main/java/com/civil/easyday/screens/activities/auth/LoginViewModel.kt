package com.civil.easyday.screens.activities.auth

import android.util.Log
import com.civil.easyday.app.sources.remote.apis.EasyDayApi
import com.civil.easyday.navigation.SingleLiveEvent
import com.civil.easyday.screens.base.BaseViewModel
import com.civil.easyday.utils.DeviceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val api: EasyDayApi
) : BaseViewModel() {

    val actionStream: SingleLiveEvent<ACTION> = SingleLiveEvent()

    sealed class ACTION {
        class GetOTPMsg(val msg: String) : ACTION()
    }

    fun sendOTP(fullNumber: String) {
        DeviceUtils.showProgress()

        api.sendOTP(fullNumber)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({resp->
                actionStream.value=ACTION.GetOTPMsg(resp.message.toString())
                DeviceUtils.dismissProgress()
            },{

                DeviceUtils.dismissProgress()
            })
    }
}