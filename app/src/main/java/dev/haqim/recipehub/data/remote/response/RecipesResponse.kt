package dev.haqim.recipehub.data.remote.response

import com.google.gson.annotations.SerializedName
import dev.haqim.recipehub.domain.model.CategoryItem
import dev.haqim.recipehub.domain.model.RecipeItem

data class RecipesResponse(

	@field:SerializedName("meals")
	val recipesItem: List<RecipesItemResponse>
) {
	fun toModel(): List<RecipeItem> {
		return this.recipesItem.map { 
			RecipeItem(
				id = it.id,
				name = it.name,
				image = it.mealThumb
			)
		}
	}
}

data class RecipesItemResponse(

	@field:SerializedName("strMealThumb")
	val mealThumb: String,

	@field:SerializedName("idMeal")
	val id: String,

	@field:SerializedName("strMeal")
	val name: String
)
