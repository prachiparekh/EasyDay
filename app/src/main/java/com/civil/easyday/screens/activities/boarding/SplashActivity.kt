package com.civil.easyday.screens.activities.boarding

import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun getContentView() = R.layout.activity_splash

    override fun setupUi() {
    }

    override fun setupObservers() {
    }
}