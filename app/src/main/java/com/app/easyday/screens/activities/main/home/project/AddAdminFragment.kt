package com.app.easyday.screens.activities.main.home.project

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.easyday.R
import com.app.easyday.app.sources.local.model.ContactModel
import com.app.easyday.databinding.FragmentAddAdminBinding
import com.app.easyday.screens.activities.main.home.project.adapter.AdminAdapter
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddAdminFragment : Fragment() {

    var binding: FragmentAddAdminBinding? = null
    var adapter: AdminAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_admin, container, false)
        var selectedParticipantList =
            arguments?.getParcelableArrayList<ContactModel>("selectedParticipantList")

        adapter = selectedParticipantList?.let {
            AdminAdapter(
                requireContext(),
                it
            )
        }
        binding?.adminRV?.adapter = adapter

        binding?.mSearch?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.isEmpty()) {
                    binding?.searchImageView?.visibility = View.VISIBLE
                    binding?.adminLL?.isVisible=true
                }
                else {
                    binding?.searchImageView?.visibility = View.INVISIBLE
                    binding?.adminLL?.isVisible=false
                }
                adapter?.filter?.filter(newText)
                return true
            }
        })

        binding?.cta?.setOnClickListener {


        }
        Log.e("selectedList", selectedParticipantList.toString())
        return binding?.root
    }

}