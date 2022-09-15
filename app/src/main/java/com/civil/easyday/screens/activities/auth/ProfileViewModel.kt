package com.civil.easyday.screens.activities.auth

import androidx.lifecycle.MutableLiveData
import com.civil.easyday.app.sources.remote.apis.EasyDayApi
import com.civil.easyday.app.sources.remote.model.UserModel
import com.civil.easyday.navigation.SingleLiveEvent
import com.civil.easyday.screens.base.BaseViewModel
import com.civil.easyday.utils.ErrorUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val api: EasyDayApi
) : BaseViewModel() {

    val userData = MutableLiveData<UserModel?>()
    val actionStream: SingleLiveEvent<ACTION> = SingleLiveEvent()

    sealed class ACTION {
        class onAddUpdateUser(val userData: UserModel?) : ACTION()
        class onError(val msg: String?) : ACTION()
    }

    fun createUser(fullName: String, profession: String, phoneNumber: String) {
        api.createUser(fullName, profession, phoneNumber)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                actionStream.value = ACTION.onAddUpdateUser(resp.data)
            }, { throwable ->
                actionStream.value = ACTION.onError(ErrorUtil.onError(throwable))
            })
    }

    fun getProfile() {
        api.getProfile()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                userData.value = resp.data
            }, {

            })
    }

}