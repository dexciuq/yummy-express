package com.dexciuq.yummy_express

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dexciuq.yummy_express.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}