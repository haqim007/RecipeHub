package dev.haqim.recipehub.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.haqim.recipehub.data.remote.response.CategoriesResponse
import dev.haqim.recipehub.domain.model.CategoryItem
import retrofit2.HttpException
import java.io.IOException

const val CATEGORIES_STARTING_PAGE_INDEX = 0
const val CATEGORIES_DEFAULT_PAGE_SIZE = 30
class CategoriesPagingSource(
    private val getCategories: suspend () -> Result<CategoriesResponse>,
): PagingSource<Int, CategoryItem>(){

    override fun getRefreshKey(state: PagingState<Int, CategoryItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CategoryItem> {
        val position = params.key ?: CATEGORIES_STARTING_PAGE_INDEX
        return try {
            val response = getCategories()
            val categories = response.getOrThrow().toModel()
            val nextKey = if(categories.isEmpty()){
                null
            }else{
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / CATEGORIES_DEFAULT_PAGE_SIZE)
            }
            LoadResult.Page(
                data = categories,
                prevKey = if (position == CATEGORIES_STARTING_PAGE_INDEX) null else position - 1,
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