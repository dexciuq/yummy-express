package com.dexciuq.yummy_express.presentation.screen.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dexciuq.yummy_express.databinding.FragmentCategoriesBinding

class CategoriesFragment : Fragment() {

    private val binding by lazy { FragmentCategoriesBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}