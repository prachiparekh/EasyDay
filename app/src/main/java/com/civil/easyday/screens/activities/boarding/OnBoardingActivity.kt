package com.civil.easyday.screens.activities.boarding

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.prefrences.AppPreferencesDelegates
import com.civil.easyday.app.sources.local.model.OnboardingItem
import com.civil.easyday.screens.activities.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_on_boarding.*

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity(),
    OnBoardingAdapter.BoardingInterface {

    private lateinit var adapter: OnBoardingAdapter
    var items: List<OnboardingItem> = arrayListOf()

    init {
        items = getOnBoardingItems()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        adapter = OnBoardingAdapter()
        pager.adapter = adapter
        adapter.setData(items, this)
    }

    private fun getOnBoardingItems() = arrayListOf<OnboardingItem>().apply {

        add(OnboardingItem(R.string.boarding_title1, R.drawable.boarding_img1))
        add(OnboardingItem(R.string.boarding_title2, R.drawable.boarding_img2))
        add(OnboardingItem(R.string.boarding_title3, R.drawable.boarding_img3))
    }

    override fun onClickNext(nextPosition: Int) {

        if (nextPosition == items.size) {
            AppPreferencesDelegates.get().wasOnboardingSeen = true
            val activityNavigator = ActivityNavigator(baseContext)
            activityNavigator.navigate(
                activityNavigator.createDestination().setIntent(
                    Intent(
                        baseContext,
                        AuthActivity::class.java
                    )
                ), null, null, null
            )
            finish()
        } else
            pager.currentItem = nextPosition
    }
}