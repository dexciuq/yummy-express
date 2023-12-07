package com.dexciuq.yummy_express.presentation.screen.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.databinding.FragmentLoginBinding
import com.dexciuq.yummy_express.presentation.activity.main.MainActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val binding by lazy { FragmentLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupListeners()
        setupTextWatchers()
    }

    private fun setupObservers() {
        viewModel.login.observe(viewLifecycleOwner) {
            if (it) {
                navigateToMainActivity()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.invalid_credentials_please_try_again),
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun setupListeners() {
        binding.signUp.setOnClickListener {
            findNavController().navigate(
                LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            )
        }

        binding.skip.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailLayout.editText?.text?.toString()
            val password = binding.passwordLayout.editText?.text?.toString()

            if (validate(email, password)) {
                viewModel.login(email.orEmpty(), password.orEmpty())
            }
        }
    }

    private fun validate(email: String?, password: String?): Boolean {
        var result = true

        if (email.isNullOrBlank()) {
            binding.emailLayout.isErrorEnabled = true
            binding.emailLayout.error = "Invalid email address"
            result = false
        }

        if (password.isNullOrBlank()) {
            binding.passwordLayout.isErrorEnabled = true
            binding.passwordLayout.error = "Password length can not be less than 8"
            result = false
        }

        return result
    }

    private fun setupTextWatchers() {
        binding.emailLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.emailLayout.isErrorEnabled = false
            binding.emailLayout.error = null
        }

        binding.passwordLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.passwordLayout.isErrorEnabled = false
            binding.passwordLayout.error = null
        }
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}