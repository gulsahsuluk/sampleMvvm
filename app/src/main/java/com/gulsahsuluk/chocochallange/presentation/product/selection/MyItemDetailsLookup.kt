package com.gulsahsuluk.chocochallange.presentation.product.selection

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.gulsahsuluk.chocochallange.presentation.product.ProductAdapter

class MyItemDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {
    override fun getItemDetails(event: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(event.x, event.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as ProductAdapter.ViewHolder)
                .getItemDetails()
        }
        return null
    }
}
