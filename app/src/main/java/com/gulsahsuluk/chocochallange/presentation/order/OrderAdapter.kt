package com.gulsahsuluk.chocochallange.presentation.order

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gulsahsuluk.chocochallange.R
import com.gulsahsuluk.chocochallange.common.applyThousandsSeparator
import com.gulsahsuluk.chocochallange.common.gone
import com.gulsahsuluk.chocochallange.common.inflate
import com.gulsahsuluk.chocochallange.data.order.model.Order
import kotlinx.android.synthetic.main.item_product.view.*
import java.util.*

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.ViewHolder>() {

    private var items: MutableList<Order> = mutableListOf()

    internal var clickListener: (Order) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_product))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position], clickListener)

    fun setItems(orders: List<Order>) {
        items.clear()
        items.addAll(orders)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Order, clickListener: (Order) -> Unit) {
            itemView.coverImage.gone()
            itemView.name.text = item.name
            itemView.desc.text = item.description
            itemView.price.text =
                item.price.applyThousandsSeparator(',').plus(Currency.getInstance("EUR").symbol)

            itemView.setOnClickListener { clickListener(item) }
        }
    }
}