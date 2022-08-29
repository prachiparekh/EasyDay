package com.civil.easyday.screens.activities.boarding

import android.app.Application
import android.content.Intent
import android.os.Handler
import android.os.Looper
import com.civil.easyday.screens.activities.auth.AuthActivity
import com.civil.easyday.screens.base.BaseViewModel
import com.civil.easyday.app.sources.local.prefrences.AppPreferencesDelegates
import com.civil.easyday.app.sources.remote.apis.EasyDayApi
import com.civil.easyday.navigation.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val app: Application,
    private val api: EasyDayApi
):BaseViewModel() {
    companion object {
        const val DEFAULT_SPLASH_TIME = 3000L
    }

    private val appPreferences = AppPreferencesDelegates.get()

    override fun onActivityCreated() {
        super.onActivityCreated()
        Handler(Looper.getMainLooper()).postDelayed({
            if (appPreferences.wasOnboardingSeen) {
                appPreferences.wasOnboardingSeen = true

                uiEventStream.postValue(
                    UiEvent.OpenIntent(
                        Intent(app, AuthActivity::class.java),
                        finishSourceActivity = true
                    )
                )
            } else {
                uiEventStream.postValue(
                    UiEvent.OpenIntent(
                        Intent(app, OnBoardingActivity::class.java),
                        finishSourceActivity = true
                    )
                )
            }
        }, DEFAULT_SPLASH_TIME)
    }
}