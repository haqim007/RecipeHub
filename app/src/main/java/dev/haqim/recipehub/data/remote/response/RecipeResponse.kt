package dev.haqim.recipehub.data.remote.response

import com.google.gson.annotations.SerializedName
import dev.haqim.recipehub.domain.model.Recipe

data class RecipeResponse(

	@field:SerializedName("meals")
	val recipeData: List<RecipeDataResponse>
)

fun RecipeResponse.toModel(): Recipe{
	val data = this.recipeData[0]
	val ingredients = listOf(
		"${data.ingredient1} ${data.measure1}",
		"${data.ingredient2} ${data.measure2}",
		"${data.ingredient3} ${data.measure3}",
		"${data.ingredient4} ${data.measure4}",
		"${data.ingredient5} ${data.measure5}",
		"${data.ingredient6} ${data.measure6}",
		"${data.ingredient7} ${data.measure7}",
		"${data.ingredient8} ${data.measure8}",
		"${data.ingredient9} ${data.measure9}",
		"${data.ingredient10} ${data.measure10}",
		"${data.ingredient11} ${data.measure11}",
		"${data.ingredient12} ${data.measure12}",
		"${data.ingredient13} ${data.measure13}",
		"${data.ingredient14} ${data.measure14}",
		"${data.ingredient15} ${data.measure15}",
		"${data.ingredient16} ${data.measure16}",
		"${data.ingredient17} ${data.measure17}",
		"${data.ingredient18} ${data.measure18}",
		"${data.ingredient19} ${data.measure19}",
		"${data.ingredient20} ${data.measure20}"
	)


	return Recipe(
		name = data.strMeal,
		id = data.idMeal,
		instructions = data.instructions,
		image = data.image,
		category = data.category,
		youtubeKey = data.youtube.substringAfter("v="),
		ingredients = ingredients.filter { it.trim().isNotEmpty() }
	)
}

data class RecipeDataResponse(

	@field:SerializedName("strIngredient10")
	val ingredient10: String,

	@field:SerializedName("strIngredient12")
	val ingredient12: String,

	@field:SerializedName("strIngredient11")
	val ingredient11: String,

	@field:SerializedName("strIngredient14")
	val ingredient14: String,

	@field:SerializedName("strCategory")
	val category: String,

	@field:SerializedName("strIngredient13")
	val ingredient13: String,

	@field:SerializedName("strIngredient16")
	val ingredient16: String,

	@field:SerializedName("strIngredient15")
	val ingredient15: String,

	@field:SerializedName("strIngredient18")
	val ingredient18: String,

	@field:SerializedName("strIngredient17")
	val ingredient17: String,

	@field:SerializedName("strArea")
	val strArea: String,

	@field:SerializedName("strCreativeCommonsConfirmed")
	val strCreativeCommonsConfirmed: Any,

	@field:SerializedName("strIngredient19")
	val ingredient19: String,

	@field:SerializedName("strTags")
	val strTags: String,

	@field:SerializedName("idMeal")
	val idMeal: String,

	@field:SerializedName("strInstructions")
	val instructions: String,

	@field:SerializedName("strIngredient1")
	val ingredient1: String,

	@field:SerializedName("strIngredient3")
	val ingredient3: String,

	@field:SerializedName("strIngredient2")
	val ingredient2: String,

	@field:SerializedName("strIngredient20")
	val ingredient20: String,

	@field:SerializedName("strIngredient5")
	val ingredient5: String,

	@field:SerializedName("strIngredient4")
	val ingredient4: String,

	@field:SerializedName("strIngredient7")
	val ingredient7: String,

	@field:SerializedName("strIngredient6")
	val ingredient6: String,

	@field:SerializedName("strIngredient9")
	val ingredient9: String,

	@field:SerializedName("strIngredient8")
	val ingredient8: String,

	@field:SerializedName("strMealThumb")
	val image: String,

	@field:SerializedName("strMeasure20")
	val measure20: String,

	@field:SerializedName("strYoutube")
	val youtube: String,

	@field:SerializedName("strMeal")
	val strMeal: String,

	@field:SerializedName("strMeasure12")
	val measure12: String,

	@field:SerializedName("strMeasure13")
	val measure13: String,

	@field:SerializedName("strMeasure10")
	val measure10: String,

	@field:SerializedName("strMeasure11")
	val measure11: String,

	@field:SerializedName("dateModified")
	val dateModified: Any,

	@field:SerializedName("strDrinkAlternate")
	val strDrinkAlternate: Any,

	@field:SerializedName("strSource")
	val strSource: String,

	@field:SerializedName("strMeasure9")
	val measure9: String,

	@field:SerializedName("strMeasure7")
	val measure7: String,

	@field:SerializedName("strMeasure8")
	val measure8: String,

	@field:SerializedName("strMeasure5")
	val measure5: String,

	@field:SerializedName("strMeasure6")
	val measure6: String,

	@field:SerializedName("strMeasure3")
	val measure3: String,

	@field:SerializedName("strMeasure4")
	val measure4: String,

	@field:SerializedName("strMeasure1")
	val measure1: String,

	@field:SerializedName("strMeasure18")
	val measure18: String,

	@field:SerializedName("strMeasure2")
	val measure2: String,

	@field:SerializedName("strMeasure19")
	val measure19: String,

	@field:SerializedName("strMeasure16")
	val measure16: String,

	@field:SerializedName("strMeasure17")
	val measure17: String,

	@field:SerializedName("strMeasure14")
	val measure14: String,

	@field:SerializedName("strMeasure15")
	val measure15: String
)
