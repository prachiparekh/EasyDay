package com.civil.easyday.screens.activities.main.home.task

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.civil.easyday.R
import com.civil.easyday.screens.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_task.*

@AndroidEntryPoint
class CreateTaskFragment : BaseFragment<CreateTaskViewModel>() {


    override fun getContentView()=R.layout.fragment_create_task

    override fun initUi() {
        requireActivity().window?.statusBarColor = resources.getColor(R.color.light_black)

        close.setOnClickListener {
            Navigation.findNavController(requireView()).popBackStack()
        }
    }

    override fun setObservers() {

    }


}