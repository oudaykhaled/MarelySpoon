package com.ouday.marelyspoon.main.di

import com.ouday.marelyspoon.main.data.local.RecipeLocalDataSource
import com.ouday.marelyspoon.main.data.local.RecipeLocalDataSourceImp
import dagger.Binds
import dagger.Module

@Module(includes = [RecipeLocalModule.Binders::class])
class RecipeLocalModule {

    @Module
    interface Binders {
        @Binds
        fun bindsLocalSource(
            localDataSourceImpl: RecipeLocalDataSourceImp
        ): RecipeLocalDataSource
    }

}