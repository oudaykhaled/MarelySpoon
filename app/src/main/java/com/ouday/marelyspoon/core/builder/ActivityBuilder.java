package com.ouday.marelyspoon.core.builder;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import com.ouday.marelyspoon.main.presentation.ui.activity.MainActivity;

@Module
public interface ActivityBuilder {

    @ContributesAndroidInjector
    MainActivity getMainActivity();

}
