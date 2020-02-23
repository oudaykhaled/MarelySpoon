package com.ouday.marelyspoon.main.data.repository

import com.ouday.marelyspoon.core.Result
import androidx.lifecycle.liveData
import com.ouday.marelyspoon.main.data.local.RecipeLocalDataSource
import com.ouday.marelyspoon.main.data.remote.source.RecipeRemoteDataSource
import javax.inject.Inject

class RecipeRepositoryImpl @Inject constructor(
    private val remoteDataSource: RecipeRemoteDataSource,
    private val localDataSource: RecipeLocalDataSource
    ) : RecipeRepository {

    override suspend fun requestAllRecipes() = liveData {
        emit(Result.loading())
        try {

            //emit local cache first until fetching data from server
            val localRecipes = localDataSource.getAllRecipes()
            if (localRecipes.isNotEmpty()){
                emit(Result.success(localRecipes))
            }

            val remoteRecipes = remoteDataSource.requestAllRecipes()

            //emit updated data from remote source
            emit(Result.success(remoteRecipes))

            //Update local cache
            localDataSource.deleteAllRecipes()
            localDataSource.insertAllRecipes(remoteRecipes)

        } catch (exception: Exception) {
            emit(Result.error(exception.message ?: ""))
        }
    }

}