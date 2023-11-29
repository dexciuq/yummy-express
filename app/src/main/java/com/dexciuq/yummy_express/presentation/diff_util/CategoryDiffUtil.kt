package com.dexciuq.yummy_express.presentation.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.dexciuq.yummy_express.domain.model.Category

object CategoryDiffUtil : DiffUtil.ItemCallback<Category>() {
    override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
        return oldItem == newItem
    }
}