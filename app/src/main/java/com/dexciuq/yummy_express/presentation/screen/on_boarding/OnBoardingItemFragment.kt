package com.dexciuq.yummy_express.presentation.screen.on_boarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.dexciuq.yummy_express.databinding.FragmentOnBoardingItemBinding

private const val ARG_PARAM_IMAGE = "image"
private const val ARG_PARAM_TITLE = "title"
private const val ARG_PARAM_DESCRIPTION = "description"

class OnBoardingItemFragment : Fragment() {

    private val binding by lazy { FragmentOnBoardingItemBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            binding.image.setImageResource(it.getInt(ARG_PARAM_IMAGE))
            binding.title.text = getString(it.getInt(ARG_PARAM_TITLE))
            binding.description.text = getString(it.getInt(ARG_PARAM_DESCRIPTION))
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(image: Int, title: Int, description: Int) =
            OnBoardingItemFragment().apply {
                arguments = bundleOf(
                    ARG_PARAM_IMAGE to image,
                    ARG_PARAM_TITLE to title,
                    ARG_PARAM_DESCRIPTION to description
                )
            }
    }
}