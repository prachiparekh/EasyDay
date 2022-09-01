package com.civil.easyday.screens.activities.boarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ActivityNavigator
import com.civil.easyday.R
import com.civil.easyday.app.sources.local.prefrences.AppPreferencesDelegates
import com.civil.easyday.screens.activities.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val appPreferences = AppPreferencesDelegates.get()

    companion object {
        const val DEFAULT_SPLASH_TIME = 3000L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

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