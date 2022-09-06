package com.civil.easyday.screens.activities.main

import android.os.Bundle
import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainViewModel>() {

    override fun getContentView() = R.layout.activity_main

    override fun setupUi() {
    }

    override fun setupObservers() {
    }
}