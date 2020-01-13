package com.example.parking_app.util

import android.graphics.Rect
import android.view.View
import androidx.annotation.Px
import androidx.recyclerview.widget.RecyclerView

class GridSpacingDecoration(@Px val itemSpacing: Int): RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val viewPosition = parent.getChildAdapterPosition(view)
        outRect.left = (viewPosition % 2) * itemSpacing / 2
        outRect.right = itemSpacing - ((viewPosition % 2) + 1) * itemSpacing / 2
        if (viewPosition >= 2) {
            outRect.top = itemSpacing
        }
    }
}