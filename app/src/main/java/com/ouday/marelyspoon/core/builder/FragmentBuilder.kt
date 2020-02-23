package com.ouday.marelyspoon.core.builder

import com.ouday.marelyspoon.main.presentation.ui.fragment.RecipeDetailsFragment
import com.ouday.marelyspoon.main.presentation.ui.fragment.RecipeListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilder {

    @ContributesAndroidInjector
    fun getRecipeListFragment(): RecipeListFragment

    @ContributesAndroidInjector
    fun getRecipeDetailsFragment(): RecipeDetailsFragment

}