package com.ouday.marelyspoon.main.data.local

import com.ouday.marelyspoon.main.data.model.Recipe

interface RecipeLocalDataSource {

    suspend fun getAllRecipes(): List<Recipe>

    suspend fun insertAllRecipes(recipes: List<Recipe>)

    suspend fun deleteAllRecipes()

}