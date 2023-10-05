package dev.haqim.recipehub.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.haqim.recipehub.data.mechanism.Resource
import dev.haqim.recipehub.domain.model.Recipe
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
class RecipeVM @Inject constructor(
    private val repository: IRecipesRepository
): ViewModel() {
    private val _state = MutableStateFlow(RecipesUiState())
    val state get() = _state.asStateFlow()
    
    fun getDetail(id: String){
        viewModelScope.launch { 
            repository.getRecipe(id).collectLatest { 
                _state.update { state ->
                    state.copy(
                        recipe = it
                    )
                }
            }
        }
    }
    
}

data class RecipesUiState(
    val recipe: Resource<Recipe> = Resource.Idle()
)