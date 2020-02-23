package com.ouday.marelyspoon

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ouday.marelyspoon.core.Status
import com.ouday.marelyspoon.main.data.local.RecipeLocalDataSource
import com.ouday.marelyspoon.main.data.model.Recipe
import com.ouday.marelyspoon.main.data.remote.source.RecipeRemoteDataSource
import com.ouday.marelyspoon.main.data.repository.RecipeRepository
import com.ouday.marelyspoon.main.data.repository.RecipeRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class RecipeRepositoryTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    lateinit var repository: RecipeRepository

    @Mock
    lateinit var remoteDataSource: RecipeRemoteDataSource

    @Mock
    lateinit var localDataSource: RecipeLocalDataSource

    val response = ArrayList<Recipe>()

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        repository = RecipeRepositoryImpl(remoteDataSource, localDataSource)
        response.add(Recipe("1"))
        response.add(Recipe("2"))
        response.add(Recipe("3"))
    }

    @Test
    fun testRequestAllRecipesWhenNoCache() = mainCoroutineRule.runBlockingTest {
        Mockito.`when`(remoteDataSource.requestAllRecipes()).thenReturn(response)
        Mockito.`when`(localDataSource.getAllRecipes()).thenReturn(ArrayList())
        val result = repository.requestAllRecipes()
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
        assert(LiveDataTestUtil.getValue(result).data == response)
    }

    @Test
    fun testRequestAllRecipesWhenCacheAvailable() = mainCoroutineRule.runBlockingTest {
        Mockito.`when`(remoteDataSource.requestAllRecipes()).thenReturn(response)
        Mockito.`when`(localDataSource.getAllRecipes()).thenReturn(response)
        val result = repository.requestAllRecipes()
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
        assert(LiveDataTestUtil.getValue(result).data == response)
    }

    @Test
    fun testRequestAllRecipesWhenCacheAvailableAndNotInternet() = mainCoroutineRule.runBlockingTest {
        Mockito.`when`(remoteDataSource.requestAllRecipes()).thenReturn(ArrayList())
        Mockito.`when`(localDataSource.getAllRecipes()).thenReturn(response)
        val result = repository.requestAllRecipes()
        assert(LiveDataTestUtil.getValue(result).status == Status.LOADING)
        assert(LiveDataTestUtil.getValue(result).status == Status.SUCCESS)
        assert(LiveDataTestUtil.getValue(result).data == ArrayList<Recipe>())
    }

}