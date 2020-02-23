package com.ouday.marelyspoon.main.di

import com.ouday.marelyspoon.main.data.remote.source.RecipeRemoteDataSource
import com.ouday.marelyspoon.main.data.remote.source.RecipeRemoteDataSourceImp
import dagger.Binds
import dagger.Module

@Module(includes = [RecipeRemoteModule.Binders::class])
class RecipeRemoteModule {

    @Module
    interface Binders {
        @Binds
        fun bindsRemoteSource(
            remoteDataSourceImpl: RecipeRemoteDataSourceImp
        ): RecipeRemoteDataSource
    }

}
