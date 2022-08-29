package com.civil.easyday.screens.base

import androidx.lifecycle.ViewModel
import com.civil.easyday.navigation.SingleLiveEvent
import com.civil.easyday.navigation.UiEvent

open class BaseViewModel : ViewModel() {

    val uiEventStream: SingleLiveEvent<UiEvent> = SingleLiveEvent()

    open fun onActivityCreated() {

    }

    open fun onFragmentCreated() {

    }

}