package com.dexciuq.yummy_express.presentation.screen.address

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dexciuq.yummy_express.databinding.FragmentAddressBottomSheetBinding
import com.dexciuq.yummy_express.domain.model.Address
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddressBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentAddressBottomSheetBinding.inflate(layoutInflater) }

    private var address: String = ""
    private var onFieldIsBlankClick: () -> Unit = {}
    private var onReadyButtonClick: (Address) -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding.address.editText?.setText(address)

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

    companion object {
        fun newInstance(
            address: String = "",
            onFieldIsBlankClick: () -> Unit = {},
            onReadyButtonClick: (Address) -> Unit = {}
        ): AddressBottomSheetFragment {
            return AddressBottomSheetFragment().apply {
                this.address = address
                this.onFieldIsBlankClick = onFieldIsBlankClick
                this.onReadyButtonClick = onReadyButtonClick
            }
        }
    }
}