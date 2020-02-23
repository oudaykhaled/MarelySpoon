package com.ouday.marelyspoon.main.di

import dagger.Binds
import dagger.Module
import com.ouday.marelyspoon.main.data.repository.RecipeRepository
import com.ouday.marelyspoon.main.data.repository.RecipeRepositoryImpl
import com.ouday.marelyspoon.main.domain.RecipeUseCase
import com.ouday.marelyspoon.main.domain.RecipeUseCaseImpl

@Module
abstract class RecipeDomainModule {

    @Binds
    abstract fun bindPokemonUseCase(
        useCaseImpl: RecipeUseCaseImpl
    ): RecipeUseCase

    @Binds
    abstract fun bindRepo(
        repoImpl: RecipeRepositoryImpl
    ): RecipeRepository
}