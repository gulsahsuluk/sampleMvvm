package com.gulsahsuluk.chocochallange.presentation.product

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.gulsahsuluk.chocochallange.R
import com.gulsahsuluk.chocochallange.common.applyThousandsSeparator
import com.gulsahsuluk.chocochallange.common.inflate
import com.gulsahsuluk.chocochallange.data.product.model.Product
import kotlinx.android.synthetic.main.item_product.view.*
import java.util.*


class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ViewHolder>() {

    internal var items: MutableList<Product> = mutableListOf()

    internal var clickListener: (Product) -> Unit = { _ -> }

    var tracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(parent.inflate(R.layout.item_product))

    override fun getItemCount() = items.size

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        tracker?.let { holder.bind(items[position], it.isSelected(position.toLong()), clickListener) }
    }

    fun setItems(products: List<Product>) {
        items.clear()
        items.addAll(products)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Product, isActivated: Boolean = false, clickListener: (Product) -> Unit) {
            itemView.name.text = item.name
            itemView.desc.text = item.description
            itemView.price.text = item.price.applyThousandsSeparator(',').plus(Currency.getInstance("EUR").symbol)
            itemView.coverImage.load(url = item.picture)
            itemView.isActivated = isActivated

            itemView.setOnClickListener { clickListener(item) }
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }
    }
}