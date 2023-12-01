package com.dexciuq.yummy_express.presentation.screen.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dexciuq.yummy_express.databinding.FragmentAddressBottomSheetBinding
import com.dexciuq.yummy_express.domain.model.Address
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddressBottomSheetFragment(
    private val onFieldIsBlankClick: () -> Unit = {},
    private val onReadyButtonClick: (Address) -> Unit = {}
) : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentAddressBottomSheetBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.readyButton.setOnClickListener {
            val address = binding.address.editText?.text.toString()
            val apartment = binding.apartment.editText?.text.toString()
            val entrance = binding.entrance.editText?.text.toString()
            val floor = binding.floor.editText?.text.toString()

            if (address.isBlank() || entrance.isBlank() || apartment.isBlank() || floor.isBlank()) {
                onFieldIsBlankClick()
                return@setOnClickListener
            }

            val result = Address(
                street = address,
                apartment = apartment.toInt(),
                entrance = entrance.toInt(),
                floor = floor.toInt()
            )
            onReadyButtonClick(result)
            dismiss()
        }
        return binding.root
    }
}