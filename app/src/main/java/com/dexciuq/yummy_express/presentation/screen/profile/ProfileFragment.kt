package com.dexciuq.yummy_express.presentation.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.aboutMe.setOnArrowRightClickListener {
            toast(binding.name.text.toString())
        }

        binding.myOrders.setOnArrowRightClickListener {
            toast("This feature will be available soon...")
        }

        return binding.root
    }
}