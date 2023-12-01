package com.dexciuq.yummy_express.presentation.screen.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentProfileBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        collectData()
        return binding.root
    }

    private fun collectData() {
        lifecycleScope.launch {
            viewModel.darkMode.collect { response ->
                when (response) {
                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                        toast(response.throwable.message)
                    }

                    is Resource.Success -> {
                        binding.darkTheme.itemBinding.actionSwitch.isChecked = response.data
                        if (response.data) {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        } else {
                            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        }
                    }
                }
            }
        }
    }
}