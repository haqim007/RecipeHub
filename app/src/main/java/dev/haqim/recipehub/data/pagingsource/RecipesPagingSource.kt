package dev.haqim.recipehub.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.haqim.recipehub.data.remote.response.RecipesResponse
import dev.haqim.recipehub.domain.model.RecipeItem
import retrofit2.HttpException
import java.io.IOException

const val RECIPES_STARTING_PAGE_INDEX = 0
const val RECIPES_DEFAULT_PAGE_SIZE = 30
class RecipesPagingSource(
    private val getRecipes: suspend () -> Result<RecipesResponse>,
): PagingSource<Int, RecipeItem>(){

    override fun getRefreshKey(state: PagingState<Int, RecipeItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, RecipeItem> {
        val position = params.key ?: RECIPES_STARTING_PAGE_INDEX
        return try {
            val response = getRecipes()
            val recipes = response.getOrThrow().toModel()
            val nextKey = if(recipes.isEmpty()){
                null
            }else{
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / RECIPES_DEFAULT_PAGE_SIZE)
            }
            LoadResult.Page(
                data = recipes,
                prevKey = if (position == RECIPES_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        }catch (exception: IOException){
            LoadResult.Error(exception)
        }catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
        catch (e: Throwable){
            return LoadResult.Error(e)
        }
    }
}