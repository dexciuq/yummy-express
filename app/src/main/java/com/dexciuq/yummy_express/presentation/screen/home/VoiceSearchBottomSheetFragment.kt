package com.dexciuq.yummy_express.presentation.screen.home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.databinding.FragmentVoiceSearchBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class VoiceSearchBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentVoiceSearchBottomSheetBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            searchVoiceBtn.setOnClickListener {
                searchImage.imageTintList =
                    ContextCompat.getColorStateList(requireActivity(), R.color.primary_dark)
                Handler(Looper.myLooper()!!).postDelayed({
                    dismiss()
                }, 2000)
            }
        }
    }

    companion object {
        fun newInstance(): VoiceSearchBottomSheetFragment {
            return VoiceSearchBottomSheetFragment()
        }
    }
}