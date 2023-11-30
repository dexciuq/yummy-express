package com.dexciuq.yummy_express.presentation.screen.checkout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.dexciuq.yummy_express.databinding.FragmentCheckoutBinding

class CheckoutFragment : Fragment() {

    private val binding by lazy { FragmentCheckoutBinding.inflate(layoutInflater) }
    private val args: CheckoutFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.address.text = args.address
        return binding.root
    }
}