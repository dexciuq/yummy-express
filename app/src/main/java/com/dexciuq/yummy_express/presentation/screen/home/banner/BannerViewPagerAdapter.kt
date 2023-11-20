package com.dexciuq.yummy_express.presentation.screen.home.banner

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dexciuq.yummy_express.domain.model.Banner

class BannerViewPagerAdapter(
    fragmentActivity: FragmentActivity,
    private val bannerList: List<Banner>,
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = bannerList.size

    override fun createFragment(position: Int): Fragment {
        val banner = bannerList[position]
        return BannerFragment.newInstance(banner)
    }
}