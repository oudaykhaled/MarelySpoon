package com.ouday.marelyspoon.main.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ouday.marelyspoon.main.data.model.Recipe

@Dao
interface RecipeDao {

    @Query("SELECT * FROM Recipe")
    fun getAll(): List<Recipe>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recipes: List<Recipe>)

    @Query("DELETE FROM Recipe")
    fun deleteAll()
}
