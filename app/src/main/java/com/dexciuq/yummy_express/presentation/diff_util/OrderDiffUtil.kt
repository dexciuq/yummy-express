package com.dexciuq.yummy_express.presentation.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.dexciuq.yummy_express.domain.model.Order

object OrderDiffUtil : DiffUtil.ItemCallback<Order>() {
    override fun areItemsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Order, newItem: Order): Boolean {
        return oldItem == newItem
    }
}