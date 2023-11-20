package com.dexciuq.yummy_express.presentation.screen.home.banner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.dexciuq.yummy_express.databinding.FragmentBannerBinding
import com.dexciuq.yummy_express.domain.model.Banner

private const val ARG_ID = "id"
private const val ARG_IMAGE = "image"

class BannerFragment : Fragment() {

    private val binding by lazy { FragmentBannerBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let { args ->
            binding.image.setImageResource(args.getInt(ARG_IMAGE))
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(banner: Banner) = BannerFragment().apply {
            arguments = bundleOf(
                ARG_ID to banner.id,
                ARG_IMAGE to banner.image
            )
        }
    }
}