package com.dexciuq.yummy_express.presentation.screen.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dexciuq.yummy_express.databinding.ItemCategoryBinding
import com.dexciuq.yummy_express.domain.model.Category
import com.dexciuq.yummy_express.presentation.diff_util.CategoryDiffUtil
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import javax.inject.Inject

class CategoriesAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: (Category) -> Unit = {}
) : ListAdapter<Category, CategoriesAdapter.ViewHolder>(CategoryDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(category: Category) {
            binding.name.text = category.name
            imageLoader.load(category.imageURL, binding.image)
            binding.root.setOnClickListener { onItemClick(category) }
        }
    }
}