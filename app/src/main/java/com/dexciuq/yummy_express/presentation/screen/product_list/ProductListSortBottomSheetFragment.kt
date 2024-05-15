package com.dexciuq.yummy_express.presentation.screen.product_list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.setIconPaddingLeft
import com.dexciuq.yummy_express.databinding.FragmentProductListSortBottomSheetBinding
import com.dexciuq.yummy_express.domain.model.Filter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ProductListSortBottomSheetFragment : BottomSheetDialogFragment() {

    private val binding by lazy { FragmentProductListSortBottomSheetBinding.inflate(layoutInflater) }
    private var chosenSort: String? = null
    private var onChosen: (String?) -> Unit = {}

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
            listOf(byDefault, priceInAscendingOrder, priceInDescendingOrder).forEach {
                it.setIconPaddingLeft(32)
            }
        }

        val id = when (chosenSort) {
            null -> R.id.by_default
            "price" -> R.id.price_in_ascending_order
            "-price" -> R.id.price_in_descending_order
            else -> error("unknown option")
        }
        binding.sortGroup.check(id)

        binding.sortGroup.setOnCheckedChangeListener { _, checkedId ->
            val sort = when (checkedId) {
                R.id.by_default -> null
                R.id.price_in_ascending_order -> "price"
                R.id.price_in_descending_order -> "-price"
                else -> error("unknown option")
            }
            onChosen(sort)

            Handler(Looper.getMainLooper()).postDelayed({
                dismiss()
            }, 300L)
        }
    }

    companion object {
        fun newInstance(filter: Filter, onChosen: (String?) -> Unit = {}): ProductListSortBottomSheetFragment {
            return ProductListSortBottomSheetFragment().apply {
                this.chosenSort = filter.sort
                this.onChosen = onChosen
            }
        }
    }
}