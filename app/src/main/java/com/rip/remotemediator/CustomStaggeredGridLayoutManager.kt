package com.rip.remotemediator

import androidx.recyclerview.widget.StaggeredGridLayoutManager

class CustomStaggeredGridLayoutManager: StaggeredGridLayoutManager {

    constructor(spanCount: Int, orientation: Int): super(spanCount, orientation)

    override fun canScrollVertically(): Boolean {
        return false
    }

    override fun canScrollHorizontally(): Boolean {
        return false
    }


}