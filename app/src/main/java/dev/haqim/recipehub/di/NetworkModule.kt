package dev.haqim.recipehub.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.haqim.recipehub.data.remote.network.ApiConfig
import dev.haqim.recipehub.data.remote.network.RecipeService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideService(
        config: ApiConfig
    ): RecipeService{
        return config.createService(RecipeService::class.java)
    }
}