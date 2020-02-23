package com.ouday.marelyspoon.main.data.remote.source

import com.ouday.marelyspoon.main.data.model.Recipe

interface RecipeRemoteDataSource {
    suspend fun requestAllRecipes(): List<Recipe>
}