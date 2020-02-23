package com.ouday.marelyspoon.main.domain

import androidx.lifecycle.LiveData
import com.ouday.marelyspoon.core.Result
import com.ouday.marelyspoon.main.data.model.Recipe

interface RecipeUseCase {
    suspend fun getAllRecipes(): LiveData<Result<List<Recipe>>>

}