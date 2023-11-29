package com.dexciuq.yummy_express.presentation.screen.home.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dexciuq.yummy_express.databinding.ItemHomeCategoryBinding
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader

class HomeCategoriesAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: (Category) -> Unit = {}
) : ListAdapter<Category, HomeCategoriesAdapter.ViewHolder>(
    object : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.id == newItem.id
        }
        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemHomeCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: ItemHomeCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.name.text = category.name
            imageLoader.load(category.imageURL, binding.image)
            binding.root.setOnClickListener { onItemClick(category) }
        }
    }
}