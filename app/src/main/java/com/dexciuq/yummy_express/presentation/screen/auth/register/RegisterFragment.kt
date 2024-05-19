package com.dexciuq.yummy_express.presentation.screen.auth.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val binding by lazy { FragmentRegisterBinding.inflate(layoutInflater) }
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListeners()
        setupTextWatchers()
    }

    private fun setupListeners() {
        viewModel.registered.observe(viewLifecycleOwner) {
            if (it) {
                Snackbar.make(
                    binding.root,
                    "We send you message to your email, activate your account to authenticate",
                    Snackbar.LENGTH_LONG
                ).show()
                findNavController().navigateUp()
            } else {
                Snackbar.make(
                    binding.root,
                    "Account with this email is exists, please sign in",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.signIn.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.registerButton.setOnClickListener {
            val name = binding.nameLayout.editText?.text?.toString()
            val surname = binding.surnameLayout.editText?.text?.toString()
            val email = binding.emailLayout.editText?.text?.toString()
            val phone = binding.phoneLayout.editText?.text?.toString()
            val password = binding.passwordLayout.editText?.text?.toString()

            if (validate(name, surname, email, phone, password)) {
                viewModel.register(
                    name.orEmpty(),
                    surname.orEmpty(),
                    email.orEmpty(),
                    phone.orEmpty(),
                    password.orEmpty()
                )
            }
        }
    }

    private fun validate(
        name: String?,
        surname: String?,
        email: String?,
        phone: String?,
        password: String?
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

        if (email.isNullOrBlank()) {
            binding.emailLayout.isErrorEnabled = true
            binding.emailLayout.error = "Invalid email address"
            result = false
        }

        if (phone.isNullOrBlank()) {
            binding.phoneLayout.isErrorEnabled = true
            binding.phoneLayout.error = "Invalid phone"
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
        binding.nameLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.nameLayout.isErrorEnabled = false
            binding.nameLayout.error = null
        }

        binding.surnameLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.surnameLayout.isErrorEnabled = false
            binding.surnameLayout.error = null
        }

        binding.phoneLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.phoneLayout.isErrorEnabled = false
            binding.phoneLayout.error = null
        }

        binding.emailLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.emailLayout.isErrorEnabled = false
            binding.emailLayout.error = null
        }

        binding.passwordLayout.editText?.doOnTextChanged { _, _, _, _ ->
            binding.passwordLayout.isErrorEnabled = false
            binding.passwordLayout.error = null
        }
    }
}