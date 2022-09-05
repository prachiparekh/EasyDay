package com.civil.easyday.screens.activities.auth

import com.civil.easyday.app.sources.remote.apis.EasyDayApi
import com.civil.easyday.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    val api: EasyDayApi
) : BaseViewModel() {

    fun createUser(fullName: String,profession: String,phoneNumber: String){
        api.createUser(fullName,profession,phoneNumber)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->

            },{

            })
    }

}