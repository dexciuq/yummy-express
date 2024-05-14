package com.dexciuq.yummy_express.presentation.screen.profile.order_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dexciuq.yummy_express.common.toMoney
import com.dexciuq.yummy_express.databinding.ItemOrderDetailProductBinding
import com.dexciuq.yummy_express.domain.model.OrderItem
import com.dexciuq.yummy_express.presentation.diff_util.OrderItemDiffUtil
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader

class OrderDetailAdapter(
    private val imageLoader: ImageLoader,
) : ListAdapter<OrderItem, OrderDetailAdapter.ViewHolder>(OrderItemDiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemOrderDetailProductBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(
        private val binding: ItemOrderDetailProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(orderItem: OrderItem) {
            with(binding) {
                name.text = orderItem.name
                subtotal.text = "${orderItem.subtotal.toMoney()}"
                quantity.text = "${orderItem.amount} ${orderItem.unit}"
                imageLoader.load(orderItem.image, image)
            }
        }
    }
}