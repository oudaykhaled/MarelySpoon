package com.ouday.marelyspoon.main.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ouday.core.presentation.ViewModelFactory
import com.ouday.marelyspoon.core.di.modules.ViewModelKey
import com.ouday.marelyspoon.main.presentation.viewmodel.RecipesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RecipePresentationModule {

    @Binds
    abstract fun bindsViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(RecipesViewModel::class)
    abstract fun bindsViewModel(viewModel: RecipesViewModel): ViewModel

}