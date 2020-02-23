package com.ouday.marelyspoon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.ouday.marelyspoon.main.data.model.Recipe
import com.ouday.marelyspoon.main.data.repository.RecipeRepository
import com.ouday.marelyspoon.main.domain.RecipeUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.ouday.marelyspoon.core.Result
import com.ouday.marelyspoon.core.Status
import com.ouday.marelyspoon.main.domain.RecipeUseCaseImpl

class RecipeUseCaseTest {
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    lateinit var useCase: RecipeUseCase

    lateinit var repository: RecipeRepository

    val response = ArrayList<Recipe>()

    @Before
    fun init() {
        response.add(Recipe("1"))
        response.add(Recipe("2"))
        response.add(Recipe("3"))
    }

    @Test
    fun testRequestAllRecipesWhenStatusIsLoading() = mainCoroutineRule.runBlockingTest{
        repository = mock {
            onBlocking { requestAllRecipes() } doReturn object : LiveData<Result<List<Recipe>>>() {
                init {
                    value = Result.loading()
                }
            }
        }
        useCase = RecipeUseCaseImpl(repository)
        val result = useCase.getAllRecipes()
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
    }

    @Test
    fun testRequestAllRecipesWhenStatusIsSuccess() = mainCoroutineRule.runBlockingTest{
        repository = mock {
            onBlocking { requestAllRecipes() } doReturn object : LiveData<Result<List<Recipe>>>() {
                init {
                    value = Result.success(response)
                }
            }
        }
        useCase = RecipeUseCaseImpl(repository)
        val result = useCase.getAllRecipes()
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS && LiveDataTestUtil.getValue(result).data == response)

    }

    @Test
    fun testRequestAllRecipesWhenStatusIsFailed() = mainCoroutineRule.runBlockingTest{
        repository = mock {
            onBlocking { requestAllRecipes() } doReturn object : LiveData<Result<List<Recipe>>>() {
                init {
                    value = Result.error("error")
                }
            }
        }
        useCase = RecipeUseCaseImpl(repository)
        val result = useCase.getAllRecipes()
        result.observeForever {  }
        assert(LiveDataTestUtil.getValue(result).status == Status.ERROR && LiveDataTestUtil.getValue(result).message == "error")

    }


}