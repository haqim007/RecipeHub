package dev.haqim.recipehub.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.haqim.recipehub.data.repository.RecipesRepository
import dev.haqim.recipehub.domain.repository.IRecipesRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideRepository(repository: RecipesRepository): IRecipesRepository
}