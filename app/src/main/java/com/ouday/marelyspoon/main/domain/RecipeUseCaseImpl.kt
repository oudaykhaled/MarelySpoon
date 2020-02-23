package com.ouday.marelyspoon.main.domain

import com.ouday.marelyspoon.main.data.repository.RecipeRepository
import javax.inject.Inject

class RecipeUseCaseImpl @Inject constructor(private val repository: RecipeRepository) :
    RecipeUseCase {

    override suspend fun getAllRecipes() = repository.requestAllRecipes()

}
