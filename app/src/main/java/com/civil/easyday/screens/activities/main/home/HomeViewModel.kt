package com.civil.easyday.screens.activities.main.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.civil.easyday.app.sources.remote.apis.EasyDayApi
import com.civil.easyday.app.sources.remote.model.ProjectRespModel
import com.civil.easyday.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val api: EasyDayApi
) : BaseViewModel() {

    val projectList = MutableLiveData<ArrayList<ProjectRespModel>?>()

    override fun onFragmentCreated() {
        super.onFragmentCreated()
        getProjects()
    }

    private fun getProjects() {
        api.getAllProject()
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                projectList.value = resp.data

            }, {
                projectList.value = null
            })
    }
}