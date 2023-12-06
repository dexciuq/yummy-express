package com.dexciuq.yummy_express.presentation.activity.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dexciuq.yummy_express.databinding.ActivityAuthBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAuthBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}