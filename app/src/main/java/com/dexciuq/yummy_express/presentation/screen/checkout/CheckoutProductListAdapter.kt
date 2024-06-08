package com.dexciuq.yummy_express.presentation.screen.checkout

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dexciuq.yummy_express.common.toMoney
import com.dexciuq.yummy_express.databinding.ItemCheckoutProductBinding
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.presentation.diff_util.ProductDiffUtil
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader

class CheckoutProductListAdapter(
    private val imageLoader: ImageLoader,
) : ListAdapter<Product, CheckoutProductListAdapter.ViewHolder>(ProductDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCheckoutProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: ItemCheckoutProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                name.text = product.name
                price.text = "${product.calculatePrice.toMoney()} / ${product.unit}"
                quantity.text = "${product.amount.toString()} ${product.unit}"
                imageLoader.load(product.imageURL, image)
            }
        }
    }
}