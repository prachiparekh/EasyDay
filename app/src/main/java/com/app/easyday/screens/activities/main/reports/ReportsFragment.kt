package com.app.easyday.screens.activities.main.reports

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.easyday.R
import com.app.easyday.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReportsFragment : BaseFragment<ReportsViewModel>() {


    companion object{
        const val TAG = "ReportsFragment"
    }

    override fun getContentView()=R.layout.fragment_reports

    override fun initUi() {
    }

    override fun setObservers() {
    }


}