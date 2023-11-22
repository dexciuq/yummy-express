package com.dexciuq.yummy_express.presentation.screen.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dexciuq.yummy_express.databinding.ItemCategoryBinding
import com.dexciuq.yummy_express.databinding.ItemHomeCategoryBinding
import com.dexciuq.yummy_express.databinding.ItemProductBinding
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import javax.inject.Inject

class HomeFeaturedProductsAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: (Long) -> Unit = {}
) : ListAdapter<Product, HomeFeaturedProductsAdapter.ViewHolder>(
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.name.text = product.name
            binding.price.text = "${product.price / 100f} ₸ / ${product.unit}"
            imageLoader.load(product.imageURL, binding.image)
            binding.root.setOnClickListener { onItemClick(product.id) }
        }
    }
}