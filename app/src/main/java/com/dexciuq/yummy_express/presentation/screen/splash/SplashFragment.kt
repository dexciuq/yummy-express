package com.dexciuq.yummy_express.presentation.screen.splash

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.databinding.FragmentSplashBinding
import com.dexciuq.yummy_express.presentation.activity.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private val binding by lazy { FragmentSplashBinding.inflate(layoutInflater) }
    private val viewModel: SplashViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lifecycleScope.launch {
            delay(1000)
            viewModel.isCompleted.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        if (resource.data) {
                            navigateToMainActivity()
                        } else {
                            navigateToOnBoardingFragment()
                        }
                    }
                    is Resource.Error -> {
                        Toast.makeText(
                            requireContext(),
                            resource.throwable.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
        return binding.root
    }

    private fun navigateToMainActivity() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun navigateToOnBoardingFragment() {
        val action = SplashFragmentDirections.actionSplashFragmentToOnBoardingFragment()
        findNavController().navigate(action)
    }
}