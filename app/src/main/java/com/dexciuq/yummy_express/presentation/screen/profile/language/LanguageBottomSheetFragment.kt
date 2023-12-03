package com.dexciuq.yummy_express.presentation.screen.profile.language

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.setIconPaddingLeft
import com.dexciuq.yummy_express.common.toast
import com.dexciuq.yummy_express.databinding.FragmentLanguageBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LanguageBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentLanguageBottomSheetBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        with(binding) {
            listOf(english, russian).forEach {
                it.setIconPaddingLeft(32)
            }
            languageGroup.setOnCheckedChangeListener { _, checkedId ->
                val language = when (checkedId) {
                    R.id.russian -> getString(R.string.russian)
                    R.id.english -> getString(R.string.english)
                    else -> error("unknown option")
                }
                toast("$language is chosen")
                Handler(Looper.getMainLooper()).postDelayed({
                    dismiss()
                }, 500L)
            }
        }
        return binding.root
    }
}