package com.dexciuq.yummy_express.common

import android.util.TypedValue
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
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

fun SearchView.setTextSize(size: Float) {
    val searchText = this.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
    searchText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
}

