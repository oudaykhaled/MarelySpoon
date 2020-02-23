package com.ouday.marelyspoon.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ouday.marelyspoon.BuildConfig
import com.ouday.marelyspoon.main.data.local.dao.RecipeDao
import com.ouday.marelyspoon.main.data.local.dao.RoomTagsListConverter
import com.ouday.marelyspoon.main.data.model.Recipe

@Database(entities = [Recipe::class], version = BuildConfig.databaseVersion)
@TypeConverters(RoomTagsListConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}