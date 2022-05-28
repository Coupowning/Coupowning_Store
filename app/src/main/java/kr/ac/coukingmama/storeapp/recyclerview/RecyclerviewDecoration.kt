package kr.ac.coukingmama.storeapp.recyclerview

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView


class RecyclerviewDecoration(private val divWidth : Int = 30) : RecyclerView.ItemDecoration() { // 리사이클러뷰 간격 설정

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State,
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.getChildAdapterPosition(view) != parent.adapter!!.itemCount - 1)
            outRect.right = divWidth
    }
}