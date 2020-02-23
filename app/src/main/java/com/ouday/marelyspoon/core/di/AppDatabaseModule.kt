package com.ouday.marelyspoon.core.di

import android.content.Context
import androidx.room.Room
import com.ouday.marelyspoon.core.database.AppDatabase
import com.ouday.marelyspoon.core.di.modules.RoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppDatabaseModule {


    @Singleton
    @Provides
    @RoomDatabase
    fun bindDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java, "recipeDatabase"
        ).build()
    }

}