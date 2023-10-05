package dev.haqim.recipehub.domain.repository

import androidx.paging.PagingData
import dev.haqim.recipehub.data.mechanism.Resource
import dev.haqim.recipehub.domain.model.CategoryItem
import dev.haqim.recipehub.domain.model.Recipe
import dev.haqim.recipehub.domain.model.RecipeItem
import kotlinx.coroutines.flow.Flow

interface IRecipesRepository {
    fun getCategories(): Flow<PagingData<CategoryItem>>
    fun getRecipes(category: String): Flow<PagingData<RecipeItem>>
    fun getRecipe(id: String): Flow<Resource<Recipe>>
}