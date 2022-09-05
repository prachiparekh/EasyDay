package com.civil.easyday.screens.activities.auth

import android.util.Log
import com.civil.easyday.app.sources.remote.apis.EasyDayApi
import com.civil.easyday.app.sources.remote.model.VerifyOTPRespModel
import com.civil.easyday.navigation.SingleLiveEvent
import com.civil.easyday.screens.base.BaseViewModel
import com.civil.easyday.utils.DeviceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class OTPViewModel @Inject constructor(
    private val api: EasyDayApi
) : BaseViewModel() {

    val actionStream: SingleLiveEvent<ACTION> = SingleLiveEvent()

    sealed class ACTION {
        class VerifyOTP(val result: Boolean, val msg: String?) : ACTION()
        class IsNewUser(val model: VerifyOTPRespModel) : ACTION()
        class GetOTPMsg(val msg: String) : ACTION()
    }

    fun resendOTP(fullNumber: String) {
        DeviceUtils.showProgress()
        api.sendOTP(fullNumber)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                actionStream.value = ACTION.GetOTPMsg(resp.message.toString())
                DeviceUtils.dismissProgress()
            }, {

                DeviceUtils.dismissProgress()
            })
    }

    fun verifyOTP(phone_number: String, otp: String) {
        DeviceUtils.showProgress()
        api.verifyOTP(phone_number, otp)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                DeviceUtils.dismissProgress()
                if (resp?.success == true)
                    actionStream.value = resp.data?.let { ACTION.IsNewUser(it) }

                actionStream.value = resp?.message?.let { ACTION.VerifyOTP(resp.success, it) }
            }, {
                DeviceUtils.dismissProgress()
                actionStream.value = ACTION.VerifyOTP(false, null)
                Log.e("ex:", it.message.toString())
            })
    }
}