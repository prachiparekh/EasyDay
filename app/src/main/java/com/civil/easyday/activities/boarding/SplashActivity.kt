package com.civil.easyday.activities.boarding

import com.civil.easyday.R
import com.civil.easyday.activities.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun getContentView() = R.layout.activity_splash

    override fun setupUi() {
    }

    override fun setupObservers() {
    }
}