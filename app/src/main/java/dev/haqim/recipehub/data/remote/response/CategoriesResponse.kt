package dev.haqim.recipehub.data.remote.response

import com.google.gson.annotations.SerializedName
import dev.haqim.recipehub.domain.model.CategoryItem

data class CategoriesResponse(

	@field:SerializedName("categories")
	val categories: List<CategoriesItemResponse>
) {
	fun toModel(): List<CategoryItem> {
		return this.categories.map { 
			CategoryItem(name = it.name, image = it.image)
		}
	}
}

data class CategoriesItemResponse(

	@field:SerializedName("strCategory")
	val name: String,

	@field:SerializedName("strCategoryDescription")
	val description: String,

	@field:SerializedName("idCategory")
	val id: String,

	@field:SerializedName("strCategoryThumb")
	val image: String
)
