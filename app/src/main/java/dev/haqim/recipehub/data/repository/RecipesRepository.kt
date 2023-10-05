package dev.haqim.recipehub.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.haqim.recipehub.data.mechanism.NetworkBoundResource
import dev.haqim.recipehub.data.mechanism.Resource
import dev.haqim.recipehub.data.pagingsource.CATEGORIES_DEFAULT_PAGE_SIZE
import dev.haqim.recipehub.data.pagingsource.CategoriesPagingSource
import dev.haqim.recipehub.data.pagingsource.RECIPES_DEFAULT_PAGE_SIZE
import dev.haqim.recipehub.data.pagingsource.RecipesPagingSource
import dev.haqim.recipehub.data.remote.RemoteDataSource
import dev.haqim.recipehub.data.remote.response.RecipeResponse
import dev.haqim.recipehub.data.remote.response.toModel
import dev.haqim.recipehub.domain.model.CategoryItem
import dev.haqim.recipehub.domain.model.Recipe
import dev.haqim.recipehub.domain.model.RecipeItem
import dev.haqim.recipehub.domain.repository.IRecipesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class RecipesRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource
): IRecipesRepository {
    override fun getCategories(): Flow<PagingData<CategoryItem>> {
        return Pager(
                config = PagingConfig(
                    pageSize = CATEGORIES_DEFAULT_PAGE_SIZE,
                    enablePlaceholders = false,
                    maxSize = 3 * CATEGORIES_DEFAULT_PAGE_SIZE
                ),
                pagingSourceFactory = {
                    CategoriesPagingSource{remoteDataSource.getCategories()}
                }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getRecipes(category: String): Flow<PagingData<RecipeItem>> {
        return Pager(
            config = PagingConfig(
                pageSize = RECIPES_DEFAULT_PAGE_SIZE,
                enablePlaceholders = false,
                maxSize = 3 * RECIPES_DEFAULT_PAGE_SIZE
            ),
            pagingSourceFactory = {
                RecipesPagingSource{remoteDataSource.getRecipes(category)}
            }
        ).flow.flowOn(Dispatchers.IO)
    }

    override fun getRecipe(id: String): Flow<Resource<Recipe>> {
        return object: NetworkBoundResource<Recipe, RecipeResponse>(){
            override suspend fun requestFromRemote(): Result<RecipeResponse> {
                return remoteDataSource.getRecipe(id)
            }

            override fun loadResult(data: RecipeResponse): Flow<Recipe> {
                return flowOf(data.toModel())
            }
        }.asFlow()
    }
}