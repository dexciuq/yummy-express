package com.dexciuq.yummy_express.presentation.screen.product_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dexciuq.yummy_express.common.Constants
import com.dexciuq.yummy_express.common.hide
import com.dexciuq.yummy_express.common.show
import com.dexciuq.yummy_express.databinding.ItemProductBinding
import com.dexciuq.yummy_express.domain.model.Product
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader

class ProductListAdapter(
    private val imageLoader: ImageLoader,
    private val onItemClick: (Product, FragmentNavigator.Extras) -> Unit,
    private val onAddToCart: (Product) -> Unit,
    private val onDeleteFromCart: (Product) -> Unit,
    private val onUpdateAmountClick: (Product) -> Unit,
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

                if (product.amount != null) {
                    addToCardContainer.hide()
                    quantityContainer.show()
                    quantity.text = product.amount.toString()
                } else {
                    addToCardContainer.show()
                    quantityContainer.hide()
                    quantity.text = 0.0.toString()
                }

                addToCardContainer.setOnClickListener {
                    addToCardContainer.hide()
                    quantityContainer.show()
                    quantity.text = product.priceUnit.toString()

                    product.amount = product.priceUnit
                    onAddToCart(product)
                }

                plus.setOnClickListener {
                    val next = quantity.text.toString().toDouble() + product.priceUnit
                    if (next <= product.quantity) {
                        quantity.text = next.toString()

                        product.amount = next
                        onUpdateAmountClick(product)
                    }
                }

                minus.setOnClickListener {
                    val prev = quantity.text.toString().toDouble() - product.priceUnit
                    if (prev == 0.0) {
                        addToCardContainer.show()
                        quantityContainer.hide()
                        quantity.text = 0.0.toString()

                        onDeleteFromCart(product)
                    } else {
                        quantity.text = prev.toString()

                        product.amount = prev
                        onUpdateAmountClick(product)
                    }
                }

                name.transitionName = Constants.TransitionName.NAME + product.id.toString()
                image.transitionName = Constants.TransitionName.IMAGE + product.id.toString()
                price.transitionName = Constants.TransitionName.PRICE + product.id.toString()

                val extras = FragmentNavigatorExtras(
                    name to Constants.TransitionName.NAME,
                    image to Constants.TransitionName.IMAGE,
                    price to Constants.TransitionName.PRICE,
                )

                productContainer.setOnClickListener {
                    onItemClick(product, extras)
                }
            }
        }
    }
}