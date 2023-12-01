package com.dexciuq.yummy_express.presentation.activity

import androidx.annotation.IdRes

interface OnNavigationItemChanger {
    fun navigate(@IdRes menu: Int)
}