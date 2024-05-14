package com.dexciuq.yummy_express.presentation.diff_util

import androidx.recyclerview.widget.DiffUtil
import com.dexciuq.yummy_express.domain.model.OrderItem
import com.dexciuq.yummy_express.domain.model.Product

object OrderItemDiffUtil : DiffUtil.ItemCallback<OrderItem>() {
    override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem == newItem
    }
}