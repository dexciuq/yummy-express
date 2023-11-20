package com.dexciuq.yummy_express.di

import com.dexciuq.yummy_express.data.repository.UIRepositoryImpl
import com.dexciuq.yummy_express.data.repository.ProductRepositoryImpl
import com.dexciuq.yummy_express.domain.repository.UIRepository
import com.dexciuq.yummy_express.domain.repository.ProductRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    fun provideBannerRepository(uiRepositoryImpl: UIRepositoryImpl): UIRepository
}