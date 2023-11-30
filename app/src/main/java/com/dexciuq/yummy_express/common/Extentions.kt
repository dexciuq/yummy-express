package com.dexciuq.yummy_express.common

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.app.Activity
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

fun BottomNavigationView.showWithAnimation(fragmentContainerView: View) {
    if (this.visibility == View.VISIBLE) return
    this.show()
    this.animateTranslationY(0f, 70f, 500)
    fragmentContainerView.animateMarginBottom(0f, 500)
}

fun BottomNavigationView.hideWithAnimation(fragmentContainerView: View) {
    if (this.visibility == View.GONE) return
    this.animateTranslationY(70f, 0f, 500)
    fragmentContainerView.animateMarginBottom(0f, 500)
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

fun SearchView.setTextSize(size: Float) {
    val searchText = this.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
    searchText.setTextSize(TypedValue.COMPLEX_UNIT_SP, size)
}

fun Fragment.toast(message: String?) {
    Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
}

fun Activity.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}


fun View.animateTranslationY(animateFrom: Float, animateTo: Float, duration: Long) {
    val animator =
        ObjectAnimator.ofFloat(
            this, "translationY", TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                animateTo,
                resources.displayMetrics
            ), TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                animateFrom,
                resources.displayMetrics
            )
        )
    animator.duration = duration
    if (animateTo == 0f) {
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                this@animateTranslationY.hide()
            }
        })
    }
    animator.start()

}

fun View.animateMarginBottom(size: Float, duration: Long) {
    val dpToPx = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        size,
        resources.displayMetrics
    )

    val params =
        this.layoutParams as ConstraintLayout.LayoutParams
    val animator = ValueAnimator.ofInt(params.bottomMargin, dpToPx.toInt())
    animator.addUpdateListener {
        val value = it.animatedValue as Int
        params.setMargins(
            params.leftMargin,
            params.topMargin,
            params.rightMargin,
            value
        )
        this.layoutParams = params
    }
    animator.duration = duration
    animator.start()
}

fun Long.toMoney() = (this.toDouble() / 100).toString() + " ₸"

