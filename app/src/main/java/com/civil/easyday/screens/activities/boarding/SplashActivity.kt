package com.civil.easyday.screens.activities.boarding

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.navigation.ActivityNavigator
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.prefrences.AppPreferencesDelegates
import com.civil.easyday.screens.activities.auth.AuthActivity
import com.civil.easyday.screens.activities.main.MainActivity
import com.civil.easyday.screens.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel>() {

    private val appPreferences = AppPreferencesDelegates.get()

    companion object {
        const val DEFAULT_SPLASH_TIME = 3000L
    }

    override fun getContentView() = R.layout.activity_splash

    override fun setupObservers() {
        viewModel.userProfileData.observe(this) { userData ->

            if (userData != null) {
                startActivity(
                    Intent(
                        this@SplashActivity,
                        MainActivity::class.java
                    )
                )
                finish()
            } else {
                val activityNavigator = ActivityNavigator(baseContext)

                Handler(Looper.getMainLooper()).postDelayed({

                    if (appPreferences.wasOnboardingSeen) {

                        activityNavigator.navigate(
                            activityNavigator.createDestination().setIntent(
                                Intent(
                                    baseContext,
                                    AuthActivity::class.java
                                )
                            ), null, null, null
                        )
                        finish()

                    } else {
                        activityNavigator.navigate(
                            activityNavigator.createDestination().setIntent(
                                Intent(
                                    baseContext,
                                    OnBoardingActivity::class.java
                                )
                            ), null, null, null
                        )
                        finish()
                    }
                }, DEFAULT_SPLASH_TIME)
            }
        }
    }

    override fun setupUi() {
        window?.statusBarColor = resources.getColor(R.color.green)
    }

}