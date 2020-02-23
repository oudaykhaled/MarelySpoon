package com.ouday.marelyspoon.main.presentation.ui.fragment


import android.content.Context
import android.graphics.Bitmap
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.assist.FailReason
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener
import com.ouday.core.presentation.ViewModelFactory
import com.ouday.marelyspoon.R
import com.ouday.marelyspoon.core.utils.applyTextFormatter
import com.ouday.marelyspoon.main.presentation.ui.bindTags
import com.ouday.marelyspoon.main.presentation.viewmodel.RecipesViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_recipe_details.*
import kotlinx.android.synthetic.main.fragment_recipe_details.view.*
import javax.inject.Inject


class RecipeDetailsFragment  : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RecipesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_recipe_details, container, false)

        setTransitions(view)
        view.viewTreeObserver
            .addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    view.viewTreeObserver.removeOnPreDrawListener(this)
                    activity?.startPostponedEnterTransition()
                    return true
                }
            })

        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    private fun setTransitions(view: View) {
        view.cpTags.transitionName = "cpTags"
        view.tvRecipeName.transitionName = "tvRecipeName"
        view.ivRecipe.transitionName = "recipeImage"

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(RecipesViewModel::class.java)

        ImageLoader.getInstance().displayImage(viewModel.selectedRecipe?.thumbnailUrl, ivRecipe, object : ImageLoadingListener{
            override fun onLoadingStarted(imageUri: String?, view: View?) {

            }

            override fun onLoadingCancelled(imageUri: String?, view: View?) {

            }

            override fun onLoadingFailed(imageUri: String?, view: View?, failReason: FailReason?) {

            }

            override fun onLoadingComplete(imageUri: String?, view: View?, loadedImage: Bitmap?) {
                ImageLoader.getInstance().displayImage(viewModel.selectedRecipe?.imageUrl, ivRecipe)
            }
        })

        tvRecipeName.text = viewModel.selectedRecipe?.title

        tvRecipeName.transitionName = "tvRecipeName"
        cpTags.transitionName = "cpTags"
        ivRecipe.transitionName = "recipeImage"

        if (TextUtils.isEmpty(viewModel.selectedRecipe?.chef)) {
            tvChefName.visibility = View.GONE
        } else {
            tvChefName.text = viewModel.selectedRecipe?.chef
        }

        tvDescription.text = viewModel.selectedRecipe?.description

        tvDescription.applyTextFormatter()
        viewModel.selectedRecipe?.let { bindTags(constraintLayoutRoot, cpTags, it, showAll = true) }

    }

}
