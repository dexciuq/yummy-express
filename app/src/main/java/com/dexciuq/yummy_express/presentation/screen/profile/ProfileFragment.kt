package com.dexciuq.yummy_express.presentation.screen.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentProfileBinding
import com.dexciuq.yummy_express.presentation.activity.auth.AuthActivity
import com.dexciuq.yummy_express.presentation.screen.profile.language.LanguageBottomSheetFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.user.observe(viewLifecycleOwner) {
            if (it == null) {
                // containers
                binding.emptyContainer.show()
                binding.userContainer.hide()

                // settings
                binding.aboutMe.hide()
                binding.myOrders.hide()
                binding.signOut.hide()

            } else {
                // containers
                binding.emptyContainer.hide()
                binding.userContainer.show()

                // settings
                binding.aboutMe.show()
                binding.myOrders.show()
                binding.signOut.show()

                // fields
                binding.name.text = it.name
                binding.email.text = it.email
            }
        }

        viewModel.logoutCompleted.observe(viewLifecycleOwner) {
            navigateToAuthActivity()
        }
    }

    private fun setupListeners() {
        binding.aboutMe.setOnArrowRightClickListener {
            toast(binding.email.text.toString())
        }

        binding.myOrders.setOnArrowRightClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToMyOrdersFragment()
            )
        }

        binding.language.setOnArrowRightClickListener {
            showLanguageBottomSheetFragment()
        }

        binding.authButton.setOnClickListener {
            navigateToAuthActivity()
        }

        binding.signOut.setOnClickListener {
            viewModel.logout()
        }
    }

    private fun navigateToAuthActivity() {
        val intent = Intent(requireActivity(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun showLanguageBottomSheetFragment() {
        val languageBottomSheetFragment = LanguageBottomSheetFragment()
        languageBottomSheetFragment.show(parentFragmentManager, languageBottomSheetFragment.tag)
    }
}