package com.dexciuq.yummy_express.presentation.screen.on_boarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dexciuq.yummy_express.domain.model.OnBoarding

class OnBoardingViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val onBoardingList: List<OnBoarding>
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = onBoardingList.size

    override fun createFragment(position: Int): Fragment {
        val screen = onBoardingList[position]
        return OnBoardingItemFragment.newInstance(
            screen.image,
            screen.title,
            screen.description,
        )
    }
}