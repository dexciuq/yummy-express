package com.dexciuq.yummy_express.presentation.screen.profile.about_me

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.databinding.FragmentAboutMeBinding
import com.dexciuq.yummy_express.presentation.screen.profile.ProfileViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AboutMeFragment : Fragment() {

    private val binding by lazy { FragmentAboutMeBinding.inflate(layoutInflater) }
    private val viewModel: ProfileViewModel by activityViewModels()

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

        binding.saveButton.setOnClickListener {
            val user = viewModel.user.value ?: error("User not defined")

            val name = binding.nameLayout.editText?.text?.toString()
            val surname = binding.surnameLayout.editText?.text?.toString()
            val phone = binding.phoneLayout.editText?.text?.toString()

            if (validate(name, surname, phone)) {
                viewModel.updateUser(
                    user.copy(
                        name = name?.trim().orEmpty(),
                        surname = surname?.trim().orEmpty(),
                        phoneNumber = phone?.trim().orEmpty()
                    )
                )

                Snackbar.make(
                    binding.root,
                    "User has been updated with success",
                    Snackbar.LENGTH_SHORT
                ).show()

                findNavController().navigateUp()
            }
        }
    }

    private fun validate(
        name: String?,
        surname: String?,
        phone: String?,
    ): Boolean {
        var result = true

        if (name.isNullOrBlank()) {
            binding.nameLayout.isErrorEnabled = true
            binding.nameLayout.error = "Invalid name"
            result = false
        }

        if (surname.isNullOrBlank()) {
            binding.surnameLayout.isErrorEnabled = true
            binding.surnameLayout.error = "Invalid surname"
            result = false
        }

        if (phone.isNullOrBlank()) {
            binding.phoneLayout.isErrorEnabled = true
            binding.phoneLayout.error = "Invalid phone"
            result = false
        }

        return result
    }
}