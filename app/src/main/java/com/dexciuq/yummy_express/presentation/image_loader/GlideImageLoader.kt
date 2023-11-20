package com.dexciuq.yummy_express.presentation.image_loader

import android.widget.ImageView
import com.bumptech.glide.Glide
import javax.inject.Inject

class GlideImageLoader @Inject constructor(
    private val glide: Glide
) : ImageLoader {
    override fun load(url: String, imageView: ImageView) {
        glide.requestManagerRetriever
            .get(imageView)
            .load(url)
            .into(imageView)
    }
}