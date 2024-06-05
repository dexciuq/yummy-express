package com.dexciuq.yummy_express.presentation.screen.auth.forgot_password

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.databinding.FragmentResetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private val binding by lazy { FragmentResetPasswordBinding.inflate(layoutInflater) }
    private val viewModel: ResetPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupObservers()
        setupTextWatchers()
    }

    private fun setupObservers() {
        viewModel.isSuccessful.observe(viewLifecycleOwner) {
            binding.message.text = "New password has been successfully set"
            binding.message.setTextColor(requireActivity().getColor(R.color.primary_dark))
            binding.message.show()
        }
    }

    private fun setupListeners() {
        binding.resetButton.setOnClickListener {
            val code = binding.resetLayout.editText?.text?.toString()
            val newPassword = binding.passwordLayout.editText?.text?.toString()
            val newPasswordConfirm = binding.passwordAgainLayout.editText?.text?.toString()
            if (validate(code, newPassword, newPasswordConfirm)) {
                viewModel.resetPassword(code.orEmpty(), newPassword.orEmpty())
            }
        }
        binding.signIn.setOnClickListener {
            findNavController().navigate(
                ResetPasswordFragmentDirections.actionResetPasswordFragmentToLoginFragment()
            )
        }
    }

    private fun setupTextWatchers() {
        binding.resetLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.resetLayout.isErrorEnabled = false
            binding.resetLayout.error = null
        }

        binding.passwordAgainLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.passwordAgainLayout.isErrorEnabled = false
            binding.passwordAgainLayout.error = null
        }

        binding.passwordLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.passwordLayout.isErrorEnabled = false
            binding.passwordLayout.error = null
        }
    }

    private fun validate(
        code: String?,
        newPassword: String?,
        newPasswordConfirm: String?
    ): Boolean {
        var result = true

        if (code.isNullOrBlank()) {
            binding.resetLayout.isErrorEnabled = true
            binding.resetLayout.error = "Invalid reset code"
            result = false
        }

        if (newPassword.isNullOrBlank()) {
            binding.passwordLayout.isErrorEnabled = true
            binding.passwordLayout.error = "Password length can not be less than 8"
            result = false
        }

        if (newPasswordConfirm.isNullOrBlank()) {
            binding.passwordAgainLayout.isErrorEnabled = true
            binding.passwordAgainLayout.error = "Password length can not be less than 8"
            result = false
        }

        if (newPassword != newPasswordConfirm) {
            binding.passwordLayout.isErrorEnabled = true
            binding.passwordLayout.error = "Passwords do not match"

            binding.passwordAgainLayout.isErrorEnabled = true
            binding.passwordAgainLayout.error = "Passwords do not match"
            result = false
        }

        return result
    }
}