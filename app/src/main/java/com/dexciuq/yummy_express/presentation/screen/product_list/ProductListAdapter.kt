package com.dexciuq.yummy_express.presentation.screen.product_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.databinding.ItemProductBinding
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader

class ProductListAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: (Long) -> Unit = {}
) : ListAdapter<Product, ProductListAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            with(binding) {
                name.text = product.name
                price.text = "${product.price / 100f} ₸ / ${product.unit}"
                imageLoader.load(product.imageURL, image)
                productContainer.setOnClickListener { onItemClick(product.id) }
                addToCardContainer.setOnClickListener {
                    addToCardContainer.hide()
                    quantityContainer.show()
                    quantity.text = product.priceUnit.toString()
                }
                plus.setOnClickListener {
                    val next = quantity.text.toString().toDouble() + product.priceUnit
                    if (next <= product.quantity) {
                        quantity.text =
                            (quantity.text.toString().toDouble() + product.priceUnit).toString()
                    }
                }
                minus.setOnClickListener {
                    if (quantity.text == product.priceUnit.toString()) {
                        addToCardContainer.show()
                        quantityContainer.hide()
                        quantity.text = 0.0.toString()
                    } else {
                        quantity.text =
                            (quantity.text.toString().toDouble() - product.priceUnit).toString()
                    }
                }
            }
        }
    }
}