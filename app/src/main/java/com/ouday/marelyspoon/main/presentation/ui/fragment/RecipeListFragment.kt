package com.ouday.marelyspoon.main.presentation.ui.fragment


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.ouday.core.presentation.ViewModelFactory
import com.ouday.marelyspoon.R
import com.ouday.marelyspoon.core.Status
import com.ouday.marelyspoon.main.data.model.Recipe
import com.ouday.marelyspoon.main.presentation.ui.adapter.RecipesRecyclerViewAdapter
import com.ouday.marelyspoon.main.presentation.viewmodel.RecipesViewModel
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import javax.inject.Inject

class RecipeListFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: RecipesViewModel

    private lateinit var adapter: RecipesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(RecipesViewModel::class.java)
        rvRecipes.layoutManager = LinearLayoutManager(context)
        adapter = RecipesRecyclerViewAdapter()
            .setOnRecipeClickListener {recipe, activityOptions ->  goToDetails(recipe, activityOptions) }
        rvRecipes.adapter = adapter
        viewModel.requestRecipes().observe(viewLifecycleOwner, Observer {
            when( it.status ){
                Status.LOADING -> showLoading()
                Status.ERROR -> showErrorRetrievingRecipes()
                Status.SUCCESS -> fetchData(it.data)
            }
        })
    }

    private fun goToDetails(
        recipe: Recipe,
        activityOptions: FragmentNavigator.Extras
    ) {
        viewModel.selectedRecipe = recipe
        activity?.postponeEnterTransition()
        view?.let { Navigation.findNavController(it)
            .navigate(R.id.action_recipeListFragment_to_recipeDetailsFragment, null, null, activityOptions) }

    }

    private fun fetchData(data: List<Recipe>?) {
        dismissLoading()
        adapter.submitList(data)
    }

    private fun dismissLoading() {
        progressBarLoadingRecipes.hide()
    }

    private fun showErrorRetrievingRecipes() {
        val snackBar = Snackbar.make(
            constraintLayoutRoot,
            getString(R.string.failed_to_load_recipes),
            Snackbar.LENGTH_INDEFINITE
        )
            snackBar.setAction(R.string.retry) {
                viewModel.requestRecipes()
        }
        snackBar.show()
    }

    private fun showLoading() {
        progressBarLoadingRecipes.show()
    }

}
