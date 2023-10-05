package dev.haqim.recipehub.domain.model

data class Recipe(
    val id: String,
    val name: String,
    val instructions: String,
    val image: String,
    val youtubeKey: String,
    val category: String,
    val ingredients: List<String>
)
