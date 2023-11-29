package com.dexciuq.yummy_express.presentation.screen.on_boarding

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.Resource
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentOnBoardingBinding
import com.dexciuq.yummy_express.domain.model.OnBoarding
import com.dexciuq.yummy_express.presentation.MainActivity
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private val binding by lazy { FragmentOnBoardingBinding.inflate(layoutInflater) }
    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lifecycleScope.launch {
            viewModel.onBoardingItems.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {}

                    is Resource.Success -> {
                        setupOnBoardingItems(resource.data)
                    }

                    is Resource.Error -> {
                        toast(resource.throwable.message)
                    }
                }
            }
        }
        return binding.root
    }

    private fun setupOnBoardingItems(onBoardingItems: List<OnBoarding>) {
        val onBoardingAdapter =
            OnBoardingViewPagerAdapter(requireActivity(), onBoardingItems)

        with(binding.onBoardingViewPager) {
            adapter = onBoardingAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    binding.nextButton.text =
                        if (position == onBoardingItems.size - 1) getString(R.string.get_started)
                        else getString(R.string.next)
                }
            })
        }

        TabLayoutMediator(
            binding.tabLayout,
            binding.onBoardingViewPager
        ) { _, _ -> }.attach()

        binding.nextButton.setOnClickListener {
            val nextItem = binding.onBoardingViewPager.currentItem + 1
            if (nextItem < onBoardingAdapter.itemCount) {
                binding.onBoardingViewPager.currentItem = nextItem
            } else {
                viewModel.setOnBoardingComplete()
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
    }
}