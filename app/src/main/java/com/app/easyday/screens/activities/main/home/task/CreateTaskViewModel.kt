package com.app.easyday.screens.activities.main.home.task

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.easyday.app.sources.remote.apis.EasyDayApi
import com.app.easyday.app.sources.remote.model.AttributeResponse
import com.app.easyday.app.sources.remote.model.ProjectParticipantsModel
import com.app.easyday.app.sources.remote.model.UserModel
import com.app.easyday.navigation.SingleLiveEvent
import com.app.easyday.screens.activities.main.home.project.ProjectViewModel
import com.app.easyday.screens.base.BaseViewModel
import com.app.easyday.utils.DeviceUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CreateTaskViewModel @Inject constructor(
    val api: EasyDayApi
) :BaseViewModel(){

    val actionStream: SingleLiveEvent<ACTION> = SingleLiveEvent()
    val projectParticipantsData = MutableLiveData<ProjectParticipantsModel?>()

    sealed class ACTION {
        class getAttributes(val attributeList: List<AttributeResponse>?,val type: Int) : ACTION()
    }

    fun getAttributes(projectId:Int,type:Int){
        DeviceUtils.showProgress()
        api.getAttributes(projectId,type)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                actionStream.value=ACTION.getAttributes(resp.data,type)

                DeviceUtils.dismissProgress()
            }, {
                Log.e("resp_ex:", it.message.toString())
                DeviceUtils.dismissProgress()
            })
    }

    fun addAttributes(projectId:Int,type:Int,attributeName:String){
        DeviceUtils.showProgress()
        api.addAttribute(type,attributeName,projectId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                getAttributes(projectId,type)
                DeviceUtils.dismissProgress()
            }, {
                Log.e("resp_ex:", it.message.toString())
                DeviceUtils.dismissProgress()
            })
    }

    fun getProjectParticipants(projectId:Int){
        DeviceUtils.showProgress()
        api.getProjectParticipants(projectId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe({ resp ->
                projectParticipantsData.value=resp?.data
                Log.e("resp:", resp.data.toString())
                DeviceUtils.dismissProgress()
            }, {
                Log.e("resp_ex:", it.message.toString())

                DeviceUtils.dismissProgress()
            })
    }
}