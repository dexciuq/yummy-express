package com.dexciuq.yummy_express.presentation.screen.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentProfileBinding
import com.dexciuq.yummy_express.domain.model.User
import com.dexciuq.yummy_express.presentation.activity.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    private val viewModel: ProfileViewModel by activityViewModels()

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
                Timber.i(it.toString())
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
        binding.aboutMe.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToAboutMeFragment()
            )
        }

        binding.company.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToAboutCompanyFragment()
            )
        }

        binding.myOrders.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToMyOrdersFragment()
            )
        }

        binding.upcScanner.setOnClickListener {
            findNavController().navigate(
                ProfileFragmentDirections.actionProfileFragmentToUPCScannerFragment()
            )
        }

        binding.contact.setOnClickListener {
            val callIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:87474938382"))
            startActivity(callIntent)
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
}