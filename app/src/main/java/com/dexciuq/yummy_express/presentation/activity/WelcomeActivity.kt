package com.dexciuq.yummy_express.presentation.activity

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.ActivityWelcomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class WelcomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityWelcomeBinding.inflate(layoutInflater) }
    private val viewModel: WelcomeActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        collectDarkTheme()
        collectLanguageCode()
    }

    private fun collectDarkTheme() {
        lifecycleScope.launch {
            viewModel.darkMode.collectLatest { response ->
                when (response) {
                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                        toast(response.throwable.message)
                    }

                    is Resource.Success -> {
                        try {
//                            if (response.data) {
//                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                            } else {
//                                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                            }
                        } catch (_: Exception) {
                        }
                    }
                }
            }
        }
    }

    private fun collectLanguageCode() {
        lifecycleScope.launch {
            viewModel.currentLanguageCode.collectLatest { response ->
                when (response) {
                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                        toast(response.throwable.message)
                    }

                    is Resource.Success -> {
                        val locale = Locale(response.data)
                        val config = Configuration()
                        config.setLocale(locale)
                        val resources = this@WelcomeActivity.resources
                        resources?.updateConfiguration(config, resources.displayMetrics)
                    }
                }
            }
        }
    }
}