package com.dexciuq.yummy_express.presentation.screen.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dexciuq.yummy_express.common.toMoney
import com.dexciuq.yummy_express.databinding.ItemCartProductBinding
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.presentation.diff_util.ProductDiffUtil
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader

class CartProductListAdapter(
    private val imageLoader: ImageLoader,
    private val onDeleteClick: (Product) -> Unit,
    private val onUpdateAmountClick: (Product) -> Unit,
) : ListAdapter<Product, CartProductListAdapter.ViewHolder>(ProductDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCartProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: ItemCartProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                name.text = product.name
                price.text = "${product.calculatePrice.toMoney()} / ${product.unit}"
                quantity.text = product.amount.toString()
                imageLoader.load(product.imageURL, image)

                plus.setOnClickListener {
                    val next = quantity.text.toString().toDouble() + product.priceUnit
                    if (next <= product.quantity) {
                        quantity.text = next.toString()

                        product.amount = next
                        onUpdateAmountClick(product)
                    }
                }

                minus.setOnClickListener {
                    val prev = quantity.text.toString().toDouble() - product.priceUnit
                    if (prev == 0.0) {
                        onDeleteClick(product)
                    } else {
                        quantity.text = prev.toString()

                        product.amount = prev
                        onUpdateAmountClick(product)
                    }
                }
            }
        }
    }
}