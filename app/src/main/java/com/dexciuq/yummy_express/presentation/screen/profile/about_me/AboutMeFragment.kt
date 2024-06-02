package com.dexciuq.yummy_express.presentation.screen.profile.about_me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.databinding.FragmentAboutMeBinding
import com.dexciuq.yummy_express.presentation.screen.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutMeFragment : Fragment() {

    private val binding by lazy { FragmentAboutMeBinding.inflate(layoutInflater) }
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
        viewModel.user.observe(viewLifecycleOwner) {
            binding.nameLayout.editText?.setText(it?.name)
            binding.surnameLayout.editText?.setText(it?.surname)
            binding.phoneLayout.editText?.setText(it?.phoneNumber)
            binding.emailLayout.editText?.setText(it?.email)
        }
    }
}