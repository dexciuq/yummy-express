package com.dexciuq.yummy_express.presentation.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentProfileBinding
import com.dexciuq.yummy_express.presentation.screen.profile.language.LanguageBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.aboutMe.setOnArrowRightClickListener {
            toast("This feature will be available soon...")
        }

        binding.myOrders.setOnArrowRightClickListener {
           findNavController().navigate(
               ProfileFragmentDirections.actionProfileFragmentToMyOrdersFragment()
           )
        }

        binding.camera.setOnClickListener {
            toast("This feature will be available soon...")
        }

        binding.language.setOnArrowRightClickListener {
            showLanguageBottomSheetFragment()
        }

        return binding.root
    }


    private fun showLanguageBottomSheetFragment() {
        val languageBottomSheetFragment = LanguageBottomSheetFragment()
        languageBottomSheetFragment.show(parentFragmentManager, languageBottomSheetFragment.tag)
    }
}