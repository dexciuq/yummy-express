package com.dexciuq.yummy_express.di

import android.content.Context
import com.bumptech.glide.Glide
import com.dexciuq.yummy_express.presentation.image_loader.GlideImageLoader
import com.dexciuq.yummy_express.presentation.image_loader.ImageLoader
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface PresentationModule {

    @Binds
    fun provideImageLoader(glideImageLoader: GlideImageLoader): ImageLoader

    companion object {
        @Provides
        fun provideGlide(@ApplicationContext context: Context): Glide {
            return Glide.get(context)
        }
    }
}