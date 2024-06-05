package com.dexciuq.yummy_express.presentation.screen.auth.forgot_password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private val binding by lazy { FragmentForgotPasswordBinding.inflate(layoutInflater) }
    private val viewModel: ForgotPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEmailLayout()
        setupListeners()
        setupTextWatchers()
    }

    private fun setupEmailLayout() {
        viewModel.isSend.observe(viewLifecycleOwner) { isSend ->
            if (isSend) {
                binding.resetPassword.performClick()
            } else {
                binding.emailLayout.isErrorEnabled = true
                binding.emailLayout.error = "Invalid email address"
            }
        }
    }


    private fun setupListeners() {
        binding.sendButton.setOnClickListener {
            val email = binding.emailLayout.editText?.text?.toString()
            if (validate(email)) {
                viewModel.sendCode(email.orEmpty())
            }
        }
        binding.resetPassword.setOnClickListener {
            findNavController().navigate(
                ForgotPasswordFragmentDirections.actionForgotPasswordFragmentToResetPasswordFragment()
            )
        }
    }

    private fun setupTextWatchers() {
        binding.emailLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.emailLayout.isErrorEnabled = false
            binding.emailLayout.error = null
        }
    }

    private fun validate(email: String?): Boolean {
        var result = true

        if (email.isNullOrBlank()) {
            binding.emailLayout.isErrorEnabled = true
            binding.emailLayout.error = "Invalid email address"
            result = false
        }

        return result
    }
}