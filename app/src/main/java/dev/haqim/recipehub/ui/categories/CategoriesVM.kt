package dev.haqim.recipehub.ui.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.haqim.recipehub.domain.model.CategoryItem
import dev.haqim.recipehub.domain.repository.IRecipesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CategoriesVM @Inject constructor(
    private val repository: IRecipesRepository
): ViewModel() {
    private var _pagingFlow: Flow<PagingData<CategoryItem>>
    val pagingFlow get() = _pagingFlow
    
    init {
        _pagingFlow = getCategories().cachedIn(viewModelScope)
    }
    
    
    private fun getCategories(): Flow<PagingData<CategoryItem>> {
        return repository.getCategories()    
    }
}