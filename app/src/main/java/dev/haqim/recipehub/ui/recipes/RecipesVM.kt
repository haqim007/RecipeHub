package dev.haqim.recipehub.ui.recipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.haqim.recipehub.domain.model.RecipeItem
import dev.haqim.recipehub.domain.repository.IRecipesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesVM @Inject constructor(
    private val repository: IRecipesRepository
): ViewModel() {
    private val _state = MutableStateFlow(RecipesUiState())
    val state get() = _state.asStateFlow()
    private var _pagingFlow: Flow<PagingData<RecipeItem>>? = null
    val pagingFlow get() = _pagingFlow
    
    fun setCategory(category: String){
        _state.update { state -> state.copy(categoryName = category) }
        _pagingFlow = getRecipes().cachedIn(viewModelScope)
    }
    
    
    private fun getRecipes(): Flow<PagingData<RecipeItem>> {
        return repository.getRecipes(state.value.categoryName)    
    }
}

data class RecipesUiState(
    val categoryName: String = "",
    val searchQuery: String? = null
)