package com.ouday.marelyspoon.main.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import org.jetbrains.annotations.NotNull
import org.jetbrains.annotations.Nullable

@Entity
@Parcelize
data class Recipe(
    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: String = "0",
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "calories")
    var calories: Double? = null,
    @ColumnInfo(name = "description")
    var description: String? = null,
    @ColumnInfo(name = "thumbnailUrl")
    var thumbnailUrl: String? = null,
    @ColumnInfo(name = "imageUrl")
    var imageUrl: String? = null,
    @ColumnInfo(name = "chef")
    @Nullable
    var chef: String? = null,
    @ColumnInfo(name = "tags")
    @Nullable
    var tags: List<String>? = null
) : Parcelable
