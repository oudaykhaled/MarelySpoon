package com.ouday.marelyspoon.main.data

import com.contentful.java.cda.CDAAsset
import com.contentful.java.cda.CDAEntry
import com.contentful.java.cda.CDAResource
import com.contentful.java.cda.LocalizedResource
import com.contentful.java.cda.image.ImageOption
import com.ouday.marelyspoon.main.data.model.Recipe

const val CONTENT_TYPE_RECIPE = "recipe"

fun Recipe.fromCDAResource(
    cdaResource: CDAResource
): Recipe {
    val thumbnailImageOptionsList = ArrayList<ImageOption>()
    thumbnailImageOptionsList.add(ImageOption.https())
    thumbnailImageOptionsList.add(ImageOption.widthOf(64))
    cdaResource.id()?.let { this.id = it }
    this.title = (cdaResource as LocalizedResource).getField<String>("title")
    this.calories = cdaResource.getField<Double>("calories")
    this.description = cdaResource.getField<String>("description")
    this.chef = cdaResource.getField<CDAEntry>("chef")?.getField<String>("name")
    this.tags = cdaResource.getField<ArrayList<CDAEntry>>("tags")
        ?.map { tag -> tag.getField<String>("name") }
    this.thumbnailUrl = cdaResource.getField<CDAAsset>("photo")
        ?.urlForImageWith(*thumbnailImageOptionsList.toTypedArray())
    this.imageUrl = cdaResource.getField<CDAAsset>("photo")?.urlForImageWith(ImageOption.https())
    return this
}

fun Recipe.isContentIdenticalTo(recipe: Recipe): Boolean {
    return id == recipe.id &&
    title == recipe.title &&
    calories == recipe.calories &&
    description == recipe.description &&
    thumbnailUrl == recipe.thumbnailUrl &&
    imageUrl == recipe.imageUrl &&
    chef == recipe.chef &&
    tags == recipe.tags
}