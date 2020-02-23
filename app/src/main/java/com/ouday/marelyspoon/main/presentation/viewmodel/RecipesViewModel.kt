package com.ouday.marelyspoon.main.presentation.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ouday.marelyspoon.main.data.model.Recipe
import com.ouday.marelyspoon.main.domain.RecipeUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.ouday.marelyspoon.core.Result

class RecipesViewModel @Inject constructor(private val useCase: RecipeUseCase) : ViewModel() {

    private val recipes = MediatorLiveData<Result<List<Recipe>>>()
    var selectedRecipe : Recipe? = null

    fun requestRecipes(): MediatorLiveData<Result<List<Recipe>>> {
        viewModelScope.launch {
            recipes.addSource(useCase.getAllRecipes()){
                recipes.postValue(it)
            }
        }
        return recipes
    }

}