package com.ouday.marelyspoon.main.data.local

import com.ouday.marelyspoon.core.database.AppDatabase
import com.ouday.marelyspoon.core.di.modules.RoomDatabase
import com.ouday.marelyspoon.core.di.qualifier.CoroutinesIO
import com.ouday.marelyspoon.main.data.model.Recipe
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RecipeLocalDataSourceImp @Inject constructor(
    @RoomDatabase private val appDatabase: AppDatabase,
    @CoroutinesIO private val context: CoroutineContext
): RecipeLocalDataSource {

    override suspend fun getAllRecipes(): List<Recipe> = withContext(context) {
        appDatabase.recipeDao().getAll()
    }

    override suspend fun insertAllRecipes(recipes: List<Recipe>) = withContext(context) {
        appDatabase.recipeDao().insert(recipes)
    }

    override suspend fun deleteAllRecipes() = withContext(context) {
        appDatabase.recipeDao().deleteAll()
    }


}