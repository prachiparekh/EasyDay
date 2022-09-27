package com.app.easyday.screens.activities.main.more

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.app.easyday.R
import com.app.easyday.databinding.FragmentMoreBinding
import com.app.easyday.screens.activities.main.home.HomeViewModel.Companion.userModel
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MoreFragment : Fragment() {

    companion object {
        const val TAG = "MoreFragment"
    }

    var binding: FragmentMoreBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        requireActivity().window?.statusBarColor = resources.getColor(R.color.green)
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_more, container, false)

        val info = requireContext().packageManager.getPackageInfo(
            requireContext().packageName, 0
        )
        binding?.versionName?.text =
            requireContext().resources.getString(R.string.version, info.versionName)

        if (userModel?.profileImage != null) {
            val options = RequestOptions()
            binding?.avatar?.clipToOutline = true
            binding?.avatar?.let {
                Glide.with(requireContext())
                    .load(userModel?.profileImage)
                    .apply(
                        options.centerCrop()
                            .skipMemoryCache(true)
                            .priority(Priority.HIGH)
                            .format(DecodeFormat.PREFER_ARGB_8888)
                    )
                    .into(it)
            }
        }

        return binding?.root
    }


}