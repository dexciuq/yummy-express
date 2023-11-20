package com.dexciuq.yummy_express.presentation.image_loader

import android.widget.ImageView

interface ImageLoader {
    fun load(url: String, imageView: ImageView)
}