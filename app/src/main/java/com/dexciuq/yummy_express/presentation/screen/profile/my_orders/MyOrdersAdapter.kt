package com.dexciuq.yummy_express.presentation.screen.profile.my_orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dexciuq.yummy_express.R
import com.dexciuq.yummy_express.common.toMoney
import com.dexciuq.yummy_express.databinding.ItemOrderBinding
import com.dexciuq.yummy_express.domain.model.Order
import com.dexciuq.yummy_express.presentation.diff_util.OrderDiffUtil

class MyOrdersAdapter(
    private val onItemClick: (Order) -> Unit = {}
) : ListAdapter<Order, MyOrdersAdapter.ViewHolder>(OrderDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: ItemOrderBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(order: Order) {
            binding.orderName.text = itemView.context.getString(R.string.order) + " #${order.id}"
            binding.total.text = itemView.context.getString(
                R.string.order_total,
                order.total.toMoney()
            )
            binding.address.text = "Created At: ${order.createdAt.take(10)}"
            binding.root.setOnClickListener { onItemClick(order) }
        }
    }
}