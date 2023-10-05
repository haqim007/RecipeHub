package dev.haqim.recipehub.data.remote.network

import dev.haqim.recipehub.data.remote.response.CategoriesResponse
import dev.haqim.recipehub.data.remote.response.RecipeResponse
import dev.haqim.recipehub.data.remote.response.RecipesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeService {
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getRecipes(
        @Query("c")
        category: String
    ): RecipesResponse

    @GET("lookup.php")
    suspend fun getRecipe(
        @Query("i")
        id: String
    ): RecipeResponse
    
    
}