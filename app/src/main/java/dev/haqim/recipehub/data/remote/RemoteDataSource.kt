package dev.haqim.recipehub.data.remote

import dev.haqim.recipehub.data.mechanism.getResult
import dev.haqim.recipehub.data.remote.network.RecipeService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(
    private val service: RecipeService
) {
    suspend fun getCategories() = getResult { 
        service.getCategories()
    }

    suspend fun getRecipes(
        category: String
    ) = getResult { 
        service.getRecipes(category)
    }

    suspend fun getRecipe(
        id: String
    ) = getResult { 
        service.getRecipe(id)
    }
}