package com.dexciuq.yummy_express.utils

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.show(navHostFragmentContainer: View) {
    if (visibility == View.VISIBLE) return
    visibility = View.VISIBLE
    val params = navHostFragmentContainer.layoutParams as ConstraintLayout.LayoutParams
    params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, params.bottomMargin)
    navHostFragmentContainer.layoutParams = params
}

fun BottomNavigationView.hide(navHostFragmentContainer: View) {
    if (visibility == View.GONE) return
    visibility = View.GONE
    val params = navHostFragmentContainer.layoutParams as ConstraintLayout.LayoutParams
    params.setMargins(params.leftMargin, params.topMargin, params.rightMargin, 0)
    navHostFragmentContainer.layoutParams = params
}

fun View.hide() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun String?.orPlaceholder(): String = this ?: ""
