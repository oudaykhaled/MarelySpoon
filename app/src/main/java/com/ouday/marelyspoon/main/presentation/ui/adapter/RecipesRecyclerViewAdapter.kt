package com.ouday.marelyspoon.main.presentation.ui.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.ouday.marelyspoon.R
import com.ouday.marelyspoon.main.data.isContentIdenticalTo
import com.ouday.marelyspoon.main.data.model.Recipe
import com.ouday.marelyspoon.main.presentation.ui.bindTags
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.row_recipe.view.*


const val MAX_TAGS_TO_SHOW = 5

class RecipesRecyclerViewAdapter :
    ListAdapter<Recipe, RecipesRecyclerViewAdapter.RecipeViewHolder>(DIFF_CALLBACK) {

    private var onRecipeClick: ((Recipe, FragmentNavigator.Extras) -> Unit)? = null

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Recipe>() {
            override fun areItemsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Recipe, newItem: Recipe): Boolean =
               oldItem.isContentIdenticalTo(newItem)
        }
    }

    fun setOnRecipeClickListener(onRecipeClickListener: ((Recipe, FragmentNavigator.Extras) -> Unit)): RecipesRecyclerViewAdapter {
        this.onRecipeClick = onRecipeClickListener
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder =
        RecipeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.row_recipe, parent, false
            )
        )

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int){
        holder.bind(getItem(position))
    }

    inner class RecipeViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: Recipe) {

            itemView.tvRecipeName.text = item.title

            ImageLoader.getInstance().displayImage(item.thumbnailUrl, itemView.ivThumbnail, object :
                ImageLoadingListener {
                override fun onLoadingStarted(imageUri: String?, view: View?) {
                }

                override fun onLoadingCancelled(imageUri: String?, view: View?) {
                }

                override fun onLoadingFailed(
                    imageUri: String?,
                    view: View?,
                    failReason: FailReason?
                ) {
                }

                override fun onLoadingComplete(
                    imageUri: String?,
                    view: View?,
                    loadedImage: Bitmap?
                ) {
                    ImageLoader.getInstance().displayImage(item.imageUrl, itemView.ivThumbnail)
                }
            })



            val navTransition = FragmentNavigatorExtras(
                itemView.ivThumbnail to "recipeImage",
                itemView.tvRecipeName to "tvRecipeName",
                itemView.cpTags to "cpTags"

            )

            itemView.setOnClickListener {
                onRecipeClick?.invoke(item, navTransition)
            }

            bindTags(itemView.constraintLayoutRoot, itemView.cpTags, item)



        }

    }


}
