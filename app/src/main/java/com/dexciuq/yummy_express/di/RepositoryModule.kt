package com.dexciuq.yummy_express.di

import com.dexciuq.yummy_express.data.repository.AuthRepositoryImpl
import com.dexciuq.yummy_express.data.repository.CategoryRepositoryImpl
import com.dexciuq.yummy_express.data.repository.ProductRepositoryImpl
import com.dexciuq.yummy_express.data.repository.UIRepositoryImpl
import com.dexciuq.yummy_express.domain.repository.AuthRepository
import com.dexciuq.yummy_express.domain.repository.CategoryRepository
import com.dexciuq.yummy_express.domain.repository.ProductRepository
import com.dexciuq.yummy_express.domain.repository.UIRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @Binds
    fun provideCategoryRepository(categoryRepositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun provideUIRepository(uiRepositoryImpl: UIRepositoryImpl): UIRepository
}