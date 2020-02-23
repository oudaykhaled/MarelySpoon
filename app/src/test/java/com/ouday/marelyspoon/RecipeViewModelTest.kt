package com.ouday.marelyspoon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.ouday.marelyspoon.main.data.model.Recipe
import com.ouday.marelyspoon.main.domain.RecipeUseCase
import com.ouday.marelyspoon.main.presentation.viewmodel.RecipesViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.ouday.marelyspoon.core.Result
import com.ouday.marelyspoon.core.Status

class RecipeViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    lateinit var recipeViewModel: RecipesViewModel

    lateinit var useCase: RecipeUseCase

    val response = ArrayList<Recipe>()

    @Before
    fun init() {
        useCase = mock ()
        response.add(Recipe("1"))
        response.add(Recipe("2"))
        response.add(Recipe("3"))
    }

    @Test
    fun testRequestLoginWhenStatusIsLoading() = mainCoroutineRule.runBlockingTest {

        useCase = mock {
            onBlocking { getAllRecipes()} doReturn object : LiveData<Result<List<Recipe>>>() {
                init {
                    value = Result.loading()
                }
            }
        }
        recipeViewModel = RecipesViewModel(useCase)
        recipeViewModel.requestRecipes()

        val result = recipeViewModel.requestRecipes()
        result.observeForever {}
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)

    }


    @Test
    fun testRequestLoginWhenStatusIsSuccess() = mainCoroutineRule.runBlockingTest {

        useCase = mock {
            onBlocking { getAllRecipes()} doReturn object : LiveData<Result<List<Recipe>>>() {
                init {
                    value = Result.success(response)
                }
            }
        }
        recipeViewModel = RecipesViewModel(useCase)
        recipeViewModel.requestRecipes()
        val result = recipeViewModel.requestRecipes()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(
            LiveDataTestUtil.getValue(result).status == Status.SUCCESS &&
                    LiveDataTestUtil.getValue(result).data == response)

    }

    @Test
    fun testRequestLoginWhenStatusIsFailed() = mainCoroutineRule.runBlockingTest {

        useCase = mock {
            onBlocking { getAllRecipes()} doReturn object : LiveData<Result<List<Recipe>>>() {
                init {
                    value = Result.error("error")
                }
            }
        }
        recipeViewModel = RecipesViewModel(useCase)
        recipeViewModel.requestRecipes()
        val result = recipeViewModel.requestRecipes()
        result.observeForever {}
        kotlinx.coroutines.delay(2000)
        assert(
            LiveDataTestUtil.getValue(result).status == Status.ERROR &&
                    LiveDataTestUtil.getValue(result).message == "error")

    }


}