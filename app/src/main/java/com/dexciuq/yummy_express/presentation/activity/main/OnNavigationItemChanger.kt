package com.dexciuq.yummy_express.presentation.activity.main

import androidx.annotation.IdRes

interface OnNavigationItemChanger {
    fun navigate(@IdRes menu: Int)
}