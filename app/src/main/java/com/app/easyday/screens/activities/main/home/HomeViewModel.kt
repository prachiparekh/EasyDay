package com.app.easyday.screens.activities.main.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.easyday.app.sources.remote.apis.EasyDayApi
import com.app.easyday.app.sources.remote.model.ProjectRespModel
import com.app.easyday.app.sources.remote.model.UserModel
import com.app.easyday.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val api: EasyDayApi
) : BaseViewModel() {

    val projectList = MutableLiveData<ArrayList<ProjectRespModel>?>()
    companion object{
        var userModel:UserModel?=null
    }
    var userProfileData= MutableLiveData<UserModel?>()

    override fun onFragmentCreated() {
        super.onFragmentCreated()
        getProfile()
        getProjects()
    }

    private fun getProfile() {
        api.getProfile()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                userModel=resp.data
                userProfileData.value = resp.data
            }, {
                userProfileData.value = null
            })
    }

    private fun getProjects() {
        api.getAllProject()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                Log.e("project_resp:", resp?.data.toString())
                projectList.value = resp.data

            }, {
                Log.e("project_resp_ex:", it.message.toString())
                projectList.value = null
            })
    }
}