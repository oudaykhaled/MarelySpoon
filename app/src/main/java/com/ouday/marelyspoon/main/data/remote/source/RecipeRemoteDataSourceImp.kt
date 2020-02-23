package com.ouday.marelyspoon.main.data.remote.source

import com.contentful.java.cda.CDAClient
import com.contentful.java.cda.CDAEntry
import com.ouday.marelyspoon.core.di.qualifier.CoroutinesIO
import com.ouday.marelyspoon.main.data.CONTENT_TYPE_RECIPE
import com.ouday.marelyspoon.main.data.fromCDAResource
import com.ouday.marelyspoon.main.data.model.Recipe
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class RecipeRemoteDataSourceImp  @Inject constructor(
    private val cdaClient: CDAClient,
    @CoroutinesIO private val context: CoroutineContext
) : RecipeRemoteDataSource {

    override suspend fun requestAllRecipes() = withContext(context) {
        cdaClient
            .fetch<CDAEntry>(CDAEntry::class.java)
            ?.withContentType(CONTENT_TYPE_RECIPE)
            ?.orderBy("sys.createdAt")
            ?.include(2)
            ?.all()
            ?.items()
            ?.map { Recipe().fromCDAResource(it) }
            ?.toList() as List<Recipe>
    }

}