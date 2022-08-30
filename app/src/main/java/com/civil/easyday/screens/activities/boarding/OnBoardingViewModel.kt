package com.civil.easyday.screens.activities.boarding

import android.app.Application
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import com.civil.easyday.EasyDayApplication
import com.civil.easyday.R
import com.civil.easyday.navigation.UiEvent
import com.civil.easyday.screens.activities.auth.AuthActivity
import com.civil.easyday.screens.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ro.westaco.carhome.presentation.screens.onboarding.OnboardingItem
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val app:Application): BaseViewModel() {

    val pagerItems = MutableLiveData<List<OnboardingItem>>()

    init {
        pagerItems.value = getOnBoardingItems()
    }

    private fun getOnBoardingItems() = arrayListOf<OnboardingItem>().apply {

        add(OnboardingItem(R.string.boarding_title1, R.drawable.boarding_img1))
        add(OnboardingItem(R.string.boarding_title2, R.drawable.boarding_img2))
        add(OnboardingItem(R.string.boarding_title3, R.drawable.boarding_img3))
    }

    fun navigateToAuth(){
        uiEventStream.postValue(
            UiEvent.OpenIntent(
                Intent(app, AuthActivity::class.java),
                finishSourceActivity = true
            )
        )
    }
}