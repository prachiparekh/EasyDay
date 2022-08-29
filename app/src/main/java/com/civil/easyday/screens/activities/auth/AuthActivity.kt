package com.civil.easyday.screens.activities.auth

import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<AuthViewModel>() {


    override fun getContentView()=R.layout.activity_auth

    override fun setupUi() {
    }

    override fun setupObservers() {
    }
}