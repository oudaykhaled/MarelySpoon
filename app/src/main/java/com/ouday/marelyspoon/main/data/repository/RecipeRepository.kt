package com.ouday.marelyspoon.main.data.repository

import androidx.lifecycle.LiveData
import com.ouday.marelyspoon.main.data.model.Recipe
import com.ouday.marelyspoon.core.Result

interface RecipeRepository {

    suspend fun requestAllRecipes(): LiveData<Result<List<Recipe>>>

}