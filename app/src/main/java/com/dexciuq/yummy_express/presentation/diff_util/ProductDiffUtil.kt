package com.dexciuq.yummy_express.presentation.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.dexciuq.yummy_express.domain.model.Product

object ProductDiffUtil : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        return oldItem == newItem
    }
}